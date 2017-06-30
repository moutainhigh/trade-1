package com.pzj.trade.withdraw.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.payment.model.RefundApplyRequestModel;
import com.pzj.core.payment.model.WithdrawApplyRequestModel;
import com.pzj.core.payment.service.RefundService;
import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.ThirdPayWithdraw;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.CashPostalEntity;
import com.pzj.trade.payment.entity.TakenOrderEntity;
import com.pzj.trade.payment.entity.ThirdTradingRecordEntiry;
import com.pzj.trade.payment.entity.WithdrawOrderFlow;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.withdraw.common.TakenStateEnum;
import com.pzj.trade.withdraw.common.WithdrawTypeEnum;
import com.pzj.trade.withdraw.common.constant.WithdrawConstant;
import com.pzj.trade.withdraw.event.PossibleWithdrawEvent;
import com.pzj.trade.withdraw.exception.WithdrawException;
import com.pzj.trade.withdraw.model.CashPostalModel;

/**
 * 退款提现引擎.
 * 
 * @author YRJ
 *
 */
@Component(value = "refundWithdrawEngine")
@Transactional
public class RefundWithdrawEngine {

	private final static Logger logger = LoggerFactory.getLogger(RefundWithdrawEngine.class);

	@Resource(name = "orderWriteMapper")
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "cashPostalWriteMapper")
	private CashPostalWriteMapper cashPostalWriteMapper;

	@Resource(name = "possibleWithdrawEvent")
	private PossibleWithdrawEvent possibleWithdrawEvent;

	@Autowired
	private NoughtRefundWithdrawEngine noughtRefundWithdrawEngine;

	@Autowired
	private AccountBussinessService accountBussinessService;

	@Autowired
	private RefundService paymentRefundService;

	/*@Autowired
	private PayFlowWriteMapper payFlowWriteMapper;*/

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void doWithdraw(final CashPostalModel cashPostalModel) {
		// moneys<订单ID,金额>
		final Map<String, Double> moneys = possibleWithdrawEvent.getPossibleWithdrawRecord(cashPostalModel.getAccountId(),
				cashPostalModel.getCashPostalMoney());
		logger.info("withdraw refundOrder resultCache:" + JSONConverter.toJson(moneys));
		// 如果发现当前用户的可提现金额不够当前提现,走线下提现流程
		if (moneys.isEmpty()) {
			noughtRefundWithdrawEngine.doWithdraw(cashPostalModel);
			return;
		}
		final String settlmentSignId = UUID.randomUUID().toString().replace("-", "");
		// 插入提现
		final CashPostalEntity postalEntity = addCashPostal(cashPostalModel.getAccountId(),
				cashPostalModel.getCashPostalMoney(), settlmentSignId);
		// 封装提现冻结金额信息
		final List<ThirdPayWithdraw> frozenlist = new ArrayList<ThirdPayWithdraw>();
		// 订单与提现关系信息
		final List<WithdrawOrderFlow> flowlist = new ArrayList<WithdrawOrderFlow>();
		for (final Entry<String, Double> entry : moneys.entrySet()) {
			final String signId = UUID.randomUUID().toString().replace("-", "");
			final double withdrawMoney = entry.getValue();
			final String orderId = entry.getKey();
			// 修改可提现金额(t_taken_order)
			cashPostalWriteMapper.updateTakenStatus(orderId, withdrawMoney, PayFlowStateEnum.Paying.getKey());
			// 构建冻结金额信息
			convertFrozenlist(orderId, withdrawMoney, signId, postalEntity.getPostal_id(), frozenlist, flowlist);

		}
		// 冻结提现金额
		logger.info("call withdrawFrozen accountBussinessService:" + JSONConverter.toJson(frozenlist));
		final Result<Boolean> result = accountBussinessService.refundThirdPayWithdraw(frozenlist);
		logger.info("call withdrawFrozen result:" + JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new WithdrawException(10501, "提现申请处理失败");
		}
		// 调用第三方退款接口,发起提现申请
		callCallThirdRefund(postalEntity.getPostal_id(), cashPostalModel.getAccountId(), frozenlist, flowlist);
	}

	/**
	 * 插入提现申请洗洗脑
	 * 
	 * @param accountId
	 * @param takenMoney
	 */
	private CashPostalEntity addCashPostal(final long accountId, final double takenMoney, final String signId) {
		final CashPostalEntity postalEntity = new CashPostalEntity();
		postalEntity.setTarget_postal_money(MoneyUtils.getMoenyNumber(takenMoney));
		postalEntity.setAccount_id(accountId);
		postalEntity.setSettlement_sign_id(signId);
		postalEntity.setPostal_status(PayFlowStateEnum.Paying.getKey());
		postalEntity.setWithdraw_type(WithdrawTypeEnum.onlineWithdraw.getKey());
		cashPostalWriteMapper.insertCashPostalEntity(postalEntity);
		return postalEntity;
	}

	/**
	 * 生成提现冻结信息
	 * 
	 * @param orderId
	 * @param changeMoney
	 * @param takenId
	 * @param frozenlist
	 */
	private void convertFrozenlist(final String orderId, final double changeMoney, final String singId, final long withdrawId,
			final List<ThirdPayWithdraw> frozenlist, final List<WithdrawOrderFlow> flowlist) {
		final OrderEntity order = orderWriteMapper.getOrderEntityNotLock(orderId);
		final ThirdPayWithdraw withdraw = new ThirdPayWithdraw();
		withdraw.setSignId(singId);
		withdraw.setOrderId(orderId);
		withdraw.setPayMoney(MoneyUtils.getMoenyNumber(changeMoney));
		withdraw.setCounterFee(MoneyUtils.getMoenyNumber(changeMoney * WithdrawConstant.counterFeeWeight));
		withdraw.setThridPayType(order.getThird_pay_type());
		withdraw.setUserId(order.getReseller_id());
		withdraw.setAudit(true);
		frozenlist.add(withdraw);

		// 拼接单个订单对应的提现退款
		final WithdrawOrderFlow withflow = new WithdrawOrderFlow();
		withflow.setOrder_id(orderId);
		withflow.setWithdraw_id(withdrawId);
		withflow.setRefund_money(MoneyUtils.getMoenyNumber(changeMoney));
		withflow.setFlow_status(PayFlowStateEnum.Paying.getKey());
		withflow.setRefund_id(singId);
		flowlist.add(withflow);
	}

	/**
	 * 提现调用第三方支付退款
	 * 
	 * @param takenId
	 * @param postalId
	 * @param accountId
	 * @param frozenlist
	 */
	private void callCallThirdRefund(final long postalId, final long accountId, final List<ThirdPayWithdraw> frozenlist,
			final List<WithdrawOrderFlow> flowlist) {
		final WithdrawApplyRequestModel model = new WithdrawApplyRequestModel();
		model.setRefundGroupId(postalId);
		final List<RefundApplyRequestModel> withdrawrefundlist = new ArrayList<RefundApplyRequestModel>();
		for (final ThirdPayWithdraw bean : frozenlist) {
			final RefundApplyRequestModel applaybean = new RefundApplyRequestModel();
			applaybean.setOrderId(bean.getOrderId());
			applaybean.setRefundId(bean.getSignId());
			applaybean.setRefundMoney(MoneyUtils.getMoenyNumber(bean.getPayMoney() - bean.getCounterFee()));
			applaybean.setRefundType(1);
			withdrawrefundlist.add(applaybean);
		}
		model.setRefundApplyRequestModels(withdrawrefundlist);
		// 插入提现订单流水记录
		cashPostalWriteMapper.insertWithdrawOrderFlow(flowlist);
		logger.info("call paymentRefundService.withdrawApply sendParam:{}", JSONConverter.toJson(model));
		final Result<Boolean> result = paymentRefundService.withdrawApply(model);
		logger.info("call paymentRefundService.withdrawApply rspResult:{}", JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new WithdrawException(10503, "调用第三方支付的提现接口异常");
		}
	}

	/**
	 * 提现成功后的回调方法
	 * 
	 * @param cashPostalEntity
	 */
	public void withdrawFinishCallback(final CashPostalEntity cashPostalEntity) {
		updateCashPostalInfo(cashPostalEntity);
		final List<WithdrawOrderFlow> flowlist = cashPostalWriteMapper.getWithdrawOrderFlowList(
				cashPostalEntity.getPostal_id(), PayFlowStateEnum.Paying.getKey());
		updateTakenOrder(flowlist, cashPostalEntity.getAccount_id());
		callunWithdrawUnfrozen(flowlist, cashPostalEntity.getPostal_id(), cashPostalEntity.getAccount_id());
	}

	/**
	 * @param cashPostal
	 */
	private void updateCashPostalInfo(final CashPostalEntity cashPostal) {
		cashPostalWriteMapper.updateCashPostalMoney(cashPostal.getPostal_id(), cashPostal.getTarget_postal_money(),
				PayFlowStateEnum.PaySuccess.getKey());
	}

	/**
	 * @param flow
	 */
	private void updateTakenOrder(final List<WithdrawOrderFlow> flowlist, final long accountId) {
		final List<String> takenOrderIds = new ArrayList<String>();
		for (final WithdrawOrderFlow flow : flowlist) {
			final TakenOrderEntity takenOrder = cashPostalWriteMapper.getTakenOrderEntityNotlock(flow.getOrder_id(), accountId);
			int takenStatus = TakenStateEnum.takened.getKey();
			if (takenOrder.getCan_postal_money() > takenOrder.getPostal_money()) {
				takenStatus = TakenStateEnum.canTaken.getKey();
			}
			flow.setFlow_status(PayFlowStateEnum.PaySuccess.getKey());
			takenOrderIds.add(flow.getOrder_id());
			cashPostalWriteMapper.updateTakenStatusByOrderId(flow.getOrder_id(), takenStatus);
			cashPostalWriteMapper.updateWithDrawOrderFlow(flow);
			//final OrderEntity order = orderWriteMapper.getOrderEntityNotLock(flow.getOrder_id());
			//addPayFlow(order, accountId, flow.getRefund_money());
		}
	}

	/**
	 * 插入提现的相关支付流水信息
	 * 
	 * @param takenOrder
	 * @param takenMoney
	 */
	//	private void addPayFlow(final OrderEntity order, final long accountId, final double takenMoney) {
	//		final ThirdTradingRecordEntiry thirdRecode = cashPostalWriteMapper.getThirdRecordOfDrawing(order.getOrder_id(),
	//				order.getPayer_id());
	//		final PayFlowEntity payflow = new PayFlowEntity();
	//		final String orderid = order.getOrder_id();
	//		payflow.setOrder_id(orderid);
	//		payflow.setPayer_id(accountId);
	//		payflow.setReceive_type(ReceiveTypeEnum.Withdraw.getValue());
	//		payflow.setPay_type(order.getThird_pay_type());
	//		payflow.setPay_amount(takenMoney);
	//		payflow.setFlow_state(PayFlowStateEnum.PaySuccess.getKey());
	//		payflow.setDeal_id(order.getThird_code());
	//		payflow.setBank_account(thirdRecode.getSeller_email());
	//		payFlowWriteMapper.insertPayFlow(payflow);
	//	}

	/**
	 * 订单对应的冻结金额解冻
	 * 
	 * @param unfroenId
	 * @param takenMoney
	 * @param order
	 * @param takenOrder
	 * @return
	 */
	private void callunWithdrawUnfrozen(final List<WithdrawOrderFlow> flowlist, final long withdrawId, final long accountId) {
		final List<ThirdPayWithdraw> thirdPayWithdraws = new ArrayList<ThirdPayWithdraw>();
		for (final WithdrawOrderFlow flow : flowlist) {
			final ThirdTradingRecordEntiry thirdRecode = cashPostalWriteMapper.getThirdRecordOfDrawing(flow.getOrder_id(),
					accountId);
			final ThirdPayWithdraw thirdPayWithdraw = new ThirdPayWithdraw();
			thirdPayWithdraw.setOrderId(flow.getOrder_id());
			thirdPayWithdraw.setSignId(flow.getRefund_id());
			thirdPayWithdraw.setPayMoney(flow.getRefund_money());
			thirdPayWithdraw.setThridPayType(flow.getThird_pay_type());
			thirdPayWithdraw.setUserId(accountId);
			thirdPayWithdraw.setBatchTransaction(thirdRecode.getDeal_id());
			thirdPayWithdraw.setReceiptBankAccount(thirdRecode.getSeller_email());
			thirdPayWithdraw.setCounterFee(thirdRecode.getPoundage());
			thirdPayWithdraw.setAudit(true);
			thirdPayWithdraws.add(thirdPayWithdraw);
		}
		logger.info("call unfrozenThirdPayWithdraw param:" + JSONConverter.toJson(thirdPayWithdraws) + ",withdrawId:"
				+ withdrawId);
		final Result<Boolean> result = accountBussinessService.unfrozenThirdPayWithdraw(thirdPayWithdraws);
		logger.info("call unfrozenThirdPayWithdraw result:" + JSONConverter.toJson(result) + ",withdrawId:" + withdrawId);
		if (!result.isOk()) {
			throw new WithdrawException(10504, "调用清结算提现通知异常");
		}
	}

}

package com.pzj.core.trade.ack.engine;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.ack.engine.exception.AckException;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.core.trade.voucher.write.VoucherDockInfoEntityWriteMapper;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.ack.model.AckModel;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderConfirmEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.AgentOrderEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.service.RefundApplyService;
import com.pzj.voucher.common.VoucherStateEnum;
import com.pzj.voucher.entity.VoucherDockInfoEntity;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 二次确认执行引擎.
 * @author YRJ
 *
 */
@Component(value = "orderAckEngine")
public class OrderAckEngine {

	private final static Logger logger = LoggerFactory.getLogger(OrderAckEngine.class);

	@Resource(name = "orderWriteMapper")
	protected OrderWriteMapper orderWriteMapper;

	@Resource(name = "merchWriteMapper")
	protected MerchWriteMapper merchWriteMapper;
	@Autowired
	private VoucherReadMapper voucherReadMapper;
	@Autowired
	private VoucherWriteMapper voucherWriteMapper;

	@Autowired
	private VoucherDockInfoEntityWriteMapper voucherDockInfoEntityWriteMapper;

	@Autowired
	private RefundApplyService refundApplyService;

	/**
	 * 确认.
	 * @param ackModel
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public Result<Boolean> ack(final AckModel ackModel) {
		//1. 先根据订单ID获取订单基本信息, 再根据订单信息校验是否可进行二次确认操作.
		final OrderEntity order = orderWriteMapper.getOrderEntityForUpdate(ackModel.getOrderId());
		if (order.getOrder_status() != OrderStatusEnum.AlreadyPaid.getValue()) {
			return new Result<Boolean>(10601, "当前状态的订单不可进行二次确认");
		}
		final List<MerchEntity> merches = merchWriteMapper.getMerchByOrderId(order.getP_order_id());
		if (checkOrderRefunding(merches)) {
			return new Result<Boolean>(10601, "当前订单存在退款中的商品,不可进行二次确认");
		}
		changeOrderAndMerchForConfirm(order.getP_order_id());
		if (!ackModel.getAcknowledge()) {
			logger.info("二次确认拒绝, 执行退款流程. orderId=" + order.getP_order_id());
			final RefundApplyReqModel reqModel = new RefundApplyReqModel();
			reqModel.setOrderId(order.getTransaction_id());
			if (ackModel.getApplyUserId() == 0) {
				reqModel.setInitiatorId(999L);
			} else {
				reqModel.setInitiatorId(ackModel.getApplyUserId());
			}
			reqModel.setInitParty(RefundInitPartyEnum.CONFIRM.getParty());
			reqModel.setRefundType(0);
			final Result<Boolean> result = refundApplyService.refundApply(reqModel, null);
			if (!result.isOk()) {
				logger.error("退款拒绝.result:" + JSONConverter.toJson(result));
				throw new AckException(10501, "退款拒绝");
			}
		} else {
			commit(order, ackModel.getThirdCode());
		}
		return new Result<Boolean>();
	}

	/**
	 * 二次确认, 提交事务.
	 * @param order
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	protected void commit(final OrderEntity order, final String thirdCode) {
		final AgentOrderEntity agent = queryAgentOrderByOrderId(order.getOrder_id());
		final List<VoucherEntity> vouchers = voucherReadMapper.queryVoucherByTransactionId(order.getTransaction_id());
		for (final VoucherEntity voucher : vouchers) {
			voucherWriteMapper.updateVouchConfirmStatusById(voucher.getVoucherId(), VoucherStateEnum.AVAILABLE.getValue(), (Date) null);
		}
		final VoucherDockInfoEntity vourDockEntity = generateAgentVoucher(order.getTransaction_id(), agent, thirdCode);
		if (!Check.NuNObject(vourDockEntity)) {
			voucherDockInfoEntityWriteMapper.insert(vourDockEntity);
		}
	}

	protected VoucherDockInfoEntity generateAgentVoucher(final String transactionId, final AgentOrderEntity agent, final String thirdCode) {
		return null;
	}

	protected AgentOrderEntity queryAgentOrderByOrderId(final String orderId) {
		return null;
	}

	/**
	 * 修改订单和订单商品的状态
	 * @param transactionId
	 */
	private void changeOrderAndMerchForConfirm(final String transactionId) {
		orderWriteMapper.updateOrderToConfirmedByTransactionId(transactionId, OrderConfirmEnum.CONFIRMED.getValue());
		merchWriteMapper.updateMerchStatusConsumeByTransactionId(transactionId, MerchStateEnum.CONSUMABLE.getState());
	}

	/**
	 * 验证是否有退款中的商品
	 * @param merches
	 * @return
	 */
	private boolean checkOrderRefunding(final List<MerchEntity> merches) {
		boolean flag = false;
		for (final MerchEntity merch : merches) {
			if (merch.getIs_refunding() == RefundingEnum.REFUNDING.getValue()) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}

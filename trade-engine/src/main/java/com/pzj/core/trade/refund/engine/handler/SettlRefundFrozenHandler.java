package com.pzj.core.trade.refund.engine.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.common.global.StrategyGlobal;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.exception.SettlementForceRefundValidatException;
import com.pzj.core.trade.refund.engine.exception.SettlementRefundFrozenException;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.PrePaymentVo;
import com.pzj.settlement.balance.response.PaymentResponse;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.settlement.forcedRefund.request.FRefSettPur;
import com.pzj.settlement.forcedRefund.request.FRefVerificationVo;
import com.pzj.settlement.forcedRefund.service.ForceRefund;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 清结算系统退款冻结处理
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "settlRefundFrozenHandler")
public class SettlRefundFrozenHandler {

	private static final Logger logger = LoggerFactory.getLogger(SettlRefundFrozenHandler.class);

	@Autowired
	private ForceRefund forceRefund;

	@Autowired
	private AccountBussinessService accountBussinessService;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	public void handler(final List<RefundFlowEntity> refundFlows, final String refundId, final RefundApplyReqModel reqModel,
			final OrderEntity sellOrder, final RefundMerchModel[] refundMerces) {

		if (sellOrder.getOnline_pay() == StrategyGlobal.IsNeedPaymentEnum.Not.getId() || sellOrder.getTotal_amount() == 0) {
			return;
		}

		final double refundTotalPrice = calculateRefundPrice(refundFlows);

		if (reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType()) {
			final FRefVerificationVo param = buildFrceRefundParam(sellOrder, refundId, refundMerces);
			forceRefund(param);
		}

		accontFrozen(sellOrder, refundId, refundTotalPrice);

	}

	/**
	 * 计算分销商退款价格
	 *
	 * @param refundFlows 退款流水
	 * @return 退款价格
	 */
	private double calculateRefundPrice(List<RefundFlowEntity> refundFlows) {

		refundFlows = RefundFlowEntity.filterFlows(refundFlows, RefundUserTypeEnum.resellerRefund.getKey());

		Double refundTotalPrice = 0D;
		for (final RefundFlowEntity refundFlow : refundFlows) {
			refundTotalPrice += refundFlow.getRefund_num() * refundFlow.getRefund_price();
		}
		final BigDecimal bd = new BigDecimal(refundTotalPrice);
		return bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 构建强制退款请求参数
	 *
	 * @param sellOrder 销售订单
	 * @param refundId 退款ID
	 * @param refundMerces 退款商品
	 * @return 验证参数
	 */
	private FRefVerificationVo buildFrceRefundParam(final OrderEntity sellOrder, final String refundId,
			final RefundMerchModel[] refundMerces) {
		final FRefVerificationVo verifyParam = new FRefVerificationVo();

		verifyParam.setDistributorId(sellOrder.getReseller_id());
		verifyParam.setOrderId(sellOrder.getOrder_id());
		if (SalePortEnum.isMicShop(sellOrder.getSale_port())) {
			verifyParam.setIsMShop(Boolean.TRUE);
		}
		verifyParam.setSignId(refundId);

		final List<String> sellMerchIds = new ArrayList<String>();
		final List<FRefSettPur> settPurS = new ArrayList<FRefSettPur>();
		for (final RefundMerchModel refundMerch : refundMerces) {
			sellMerchIds.add(refundMerch.getMerchId());
			final MerchEntity purcMerch = merchWriteMapper.getPurchaseMerchBySellMerchId(refundMerch.getMerchId());
			final FRefSettPur fRefSettPur = new FRefSettPur();
			fRefSettPur.setPurMerchId(purcMerch.getMerch_id());
			fRefSettPur.setPurOrderId(purcMerch.getOrder_id());
			settPurS.add(fRefSettPur);
		}
		verifyParam.setSellMerchIds(sellMerchIds);

		verifyParam.setSettPurS(settPurS);

		return verifyParam;
	}

	/**
	 * 强制退款参数校验
	 *
	 * @param verifyParam 验证参数
	 */
	private void forceRefund(final FRefVerificationVo verifyParam) {

		if (logger.isInfoEnabled()) {
			logger.info("call settlement force refund verify ,reqModel:{}.", JSONConverter.toJson(verifyParam));
		}
		Result<Boolean> result = null;
		try {
			result = forceRefund.forceRefundVerification(verifyParam);
		} catch (final Throwable t) {

			logger.error("call settlement force refund verify exception,reqModel:" + JSONConverter.toJson(verifyParam), t);

			throw new SettlementForceRefundValidatException(RefundErrorCode.SETTLEMENT_FORCE_REFUND_VALIDATE_ERROR_CODE,
					"清结算验证强制退款异常");
		}

		if (!result.isOk()) {
			logger.warn("call settlement force refund verify fail,reqModel:{},result:{}.", JSONConverter.toJson(verifyParam),
					result.getErrorCode() + "," + result.getErrorMsg());
			throw new SettlementForceRefundValidatException(RefundErrorCode.SETTLEMENT_FORCE_REFUND_VALIDATE_ERROR_CODE,
					result.getErrorMsg());
		}

		if (logger.isInfoEnabled()) {
			logger.info("settlement force refund verify result is ok.");
		}

	}

	/**
	 * 调用退款冻结获取,此订单应退金额,记入流水记录
	 *
	 * @param sellOrder 销售订单
	 * @param refundId 退款ID
	 * @param refundTotalPrice 退款总价格
	 */
	private void accontFrozen(final OrderEntity sellOrder, final String refundId, final double refundTotalPrice) {

		final PrePaymentVo refundFrozenParam = buildrefundFrozenParam(sellOrder, refundId, refundTotalPrice);

		if (logger.isInfoEnabled()) {
			logger.info("call account frozen, reqParam:{}", JSONConverter.toJson(refundFrozenParam));
		}
		double balancePayMoney = 0, thirdPayMoney = 0;
		if (refundTotalPrice > 0) {
			Result<PaymentResponse> frozenResult = null;
			try {
				frozenResult = accountBussinessService.refundFrozenBalance(refundFrozenParam);
			} catch (final Throwable t) {
				logger.error("call account frozen exception,reqParam:" + JSONConverter.toJson(refundFrozenParam), t);
				throw new SettlementRefundFrozenException(RefundErrorCode.SETTLEMENT_ACCOUNT_BUSSINESS_ERROR_CODE, "清结算退款冻结异常");
			}

			if (!frozenResult.isOk()) {
				logger.warn("call account frozen fail,reqParam:{},errorMsg:{}.", JSONConverter.toJson(refundFrozenParam),
						frozenResult.getErrorMsg());

				throw new SettlementRefundFrozenException(RefundErrorCode.SETTLEMENT_ACCOUNT_BUSSINESS_ERROR_CODE,
						frozenResult.getErrorMsg());
			}

			final PaymentResponse resultData = frozenResult.getData();
			if (null == resultData) {
				logger.warn("call account frozen fail,result is null,reqParam:{}.", JSONConverter.toJson(refundFrozenParam));

				throw new SettlementRefundFrozenException(RefundErrorCode.SETTLEMENT_ACCOUNT_BUSSINESS_ERROR_CODE,
						"清结算退款冻结结果为空");
			}
			if (logger.isInfoEnabled()) {
				logger.info("call account frozen result,reqParam:{},reqResult:{}.", JSONConverter.toJson(refundFrozenParam),
						JSONConverter.toJson(resultData));
			}
			balancePayMoney = resultData.getBalancePayMoney();
			thirdPayMoney = resultData.getThirdPayMoney();
		}
		writeFreezeFlowEntity(sellOrder.getOrder_id(), refundId, balancePayMoney, thirdPayMoney);

	}

	/**
	 * 构建退款冻结请求参数
	 *
	 * @param sellOrder 销售订单
	 * @param refundId 退款ID
	 * @param refundTotalPrice 退款总价
	 * @return 退款冻结请求参数
	 */
	private PrePaymentVo buildrefundFrozenParam(final OrderEntity sellOrder, final String refundId,
			final double refundTotalPrice) {
		// 退款冻结，并获取余额退款额和第三方退款额
		final PrePaymentVo refundFrozenParam = new PrePaymentVo();
		refundFrozenParam.setOrderId(sellOrder.getOrder_id());
		refundFrozenParam.setSignId(refundId);
		refundFrozenParam.setUserId(SalePortEnum.isMicShop(sellOrder.getSale_port()) ? sellOrder.getReseller_id() : sellOrder
				.getPayer_id());
		refundFrozenParam.setPayMoney(refundTotalPrice);
		refundFrozenParam.setIsMShop(SalePortEnum.isMicShop(sellOrder.getSale_port()));

		return refundFrozenParam;
	}

	/**
	 * 写人退款流水
	 * @param orderId 订单ID
	 * @param refundId 退款ID
	 * @param balance 退余额
	 * @param thirdPay 退三方
	 */
	private void writeFreezeFlowEntity(final String orderId, final String refundId, final Double balance, final Double thirdPay) {
		final FreezeFlowEntity freezeFlowEntity = new FreezeFlowEntity();
		freezeFlowEntity.setOrder_id(orderId);
		freezeFlowEntity.setSign_id(refundId);
		freezeFlowEntity.setBalance_amount(balance);
		freezeFlowEntity.setThird_amount(thirdPay);
		freezeFlowEntity.setFreeze_state(PayFlowStateEnum.Paying.getKey());
		freezeFlowEntity.setReceive_type(ReceiveTypeEnum.Refund.getValue());

		freezeFlowWriteMapper.insertFreezeFlow(freezeFlowEntity);
	}

}

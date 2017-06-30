package com.pzj.core.trade.refund.engine.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.SettlementForceRefundCallbackException;
import com.pzj.core.trade.refund.engine.exception.SettlementRefundCallbackException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.ConfirmPaymentVo;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.settlement.forcedRefund.request.FRefCreateVo;
import com.pzj.settlement.forcedRefund.service.ForceRefund;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.entity.FreezeFlowEntity;

/**
 * 清结算系统退款处理成功回调
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "settlRefundCleanHandler")
public class SettlRefundCleanHandler {

	private static final Logger logger = LoggerFactory.getLogger(SettlRefundCleanHandler.class);

	@Autowired
	private ForceRefund forceRefund;

	@Autowired
	private AccountBussinessService accountBussinessService;

	public void handler(final List<RefundFlowEntity> refundFlows, final String refundId, final OrderEntity sellOrder,
			final int refundType, final FreezeFlowEntity freezeFlow) {

		if (null == freezeFlow) {
			return;
		}

		if (refundType == RefundApplyTypeEnum.FORCE.getType()) {
			final FRefCreateVo reqParam = buildFRefCreateVo(refundId, sellOrder, refundFlows);
			callForceRefundUpAccount(reqParam);
		}

		callAccountService(freezeFlow, sellOrder, refundId);

	}

	/**
	 * 构建强制退款成功告知清结算系统参数
	 *
	 * @param refundId
	 *            退款ID
	 * @param rootOrder
	 *            销售订单
	 * @param refundflows
	 *            退款流水
	 * @return
	 */
	private FRefCreateVo buildFRefCreateVo(final String refundId, final OrderEntity sellOrder,
			final List<RefundFlowEntity> refundflows) {

		final FRefCreateVo vo = new FRefCreateVo();
		vo.setOrderId(sellOrder.getOrder_id());
		vo.setSignId(refundId);
		vo.setDistributorId(sellOrder.getReseller_id());
		final List<String> merchIds = new ArrayList<String>(refundflows.size());
		Double resellerRefundMoney = 0D;
		for (final RefundFlowEntity flow : refundflows) {
			merchIds.add(flow.getMerch_id());
			resellerRefundMoney += flow.getRefund_price() * flow.getRefund_num();
		}
		final BigDecimal bd = new BigDecimal(resellerRefundMoney);
		vo.setPayMoney(bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
		vo.setIsMShop(SalePortEnum.isMicShop(sellOrder.getSale_port()));
		vo.setMerchId(merchIds);
		return vo;

	}

	private void callForceRefundUpAccount(final FRefCreateVo reqParam) {
		if (logger.isInfoEnabled()) {
			logger.info("force refund update account,reqModel:{}.", JSONConverter.toJson(reqParam));
		}
		Result<Boolean> updateResult = null;
		try {
			updateResult = forceRefund.forceRefundUpAccount(reqParam);
		} catch (final Throwable t) {
			logger.error("force refund update account exception ,reqModel:" + JSONConverter.toJson(reqParam), t);
			throw new SettlementForceRefundCallbackException(RefundErrorCode.SETTL_FORCE_REFUND_CALLBACK_ERROR_CODE,
					t.getMessage(), t);
		}

		if (!updateResult.isOk()) {
			logger.warn("force refund update account fail ,reqModel:{}", JSONConverter.toJson(reqParam));
			throw new SettlementForceRefundCallbackException(RefundErrorCode.SETTL_FORCE_REFUND_CALLBACK_ERROR_CODE,
					updateResult.getErrorCode() + "," + updateResult.getErrorMsg());
		}

		if (logger.isInfoEnabled()) {
			logger.info("force refund update account success,reqModel:{}.", JSONConverter.toJson(reqParam));
		}
	}

	/**
	 * 普通退款成功回调清结算系统
	 *
	 * @param freezeFlowEntity
	 * @param rootOrder
	 * @param refundId
	 */
	private void callAccountService(final FreezeFlowEntity freezeFlowEntity, final OrderEntity sellOrder, final String refundId) {

		final ConfirmPaymentVo unfrozenParam = buildUnfrozenParam(freezeFlowEntity, sellOrder, refundId);
		if (unfrozenParam.getBalancePayMoney() + unfrozenParam.getThirdPayMoney() == 0) {
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info("account balance unfrozen,reqModel:{}." + JSONConverter.toJson(unfrozenParam));
		}
		Result<Boolean> accountResult = null;
		try {
			accountResult = accountBussinessService.refundUnfrozenBalance(unfrozenParam);
		} catch (final Throwable t) {
			logger.error("account balance unfrozen exception,reqModel:" + JSONConverter.toJson(unfrozenParam), t);
			throw new SettlementRefundCallbackException(RefundErrorCode.SETTL_REFUND_CALLBACK_ERROR_CODE, t.getMessage(), t);
		}

		if (!accountResult.isOk()) {
			logger.error("account balance unfrozen fail,reqModel:" + JSONConverter.toJson(unfrozenParam),
					accountResult.getErrorMsg());
			throw new SettlementRefundCallbackException(RefundErrorCode.SETTL_REFUND_CALLBACK_ERROR_CODE,
					accountResult.getErrorMsg());
		}

		if (logger.isInfoEnabled()) {
			logger.info("account balance unfrozen success.");
		}
	}

	private ConfirmPaymentVo buildUnfrozenParam(final FreezeFlowEntity freezeFlowEntity, final OrderEntity sellOrder,
			final String refundId) {
		final ConfirmPaymentVo unfrozenParam = new ConfirmPaymentVo();
		unfrozenParam.setBalancePayMoney(freezeFlowEntity.getBalance_amount());
		unfrozenParam.setThirdPayMoney(freezeFlowEntity.getThird_amount());
		unfrozenParam.setOrderId(freezeFlowEntity.getOrder_id());
		unfrozenParam.setSignId(refundId);
		unfrozenParam.setThridPayType(sellOrder.getThird_pay_type());
		unfrozenParam.setUserId(SalePortEnum.isMicShop(sellOrder.getSale_port()) ? sellOrder.getReseller_id() : sellOrder
				.getPayer_id());
		unfrozenParam.setIsMShop(SalePortEnum.isMicShop(sellOrder.getSale_port()));

		return unfrozenParam;
	}

}

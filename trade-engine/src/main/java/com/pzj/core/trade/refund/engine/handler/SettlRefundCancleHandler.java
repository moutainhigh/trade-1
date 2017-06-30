package com.pzj.core.trade.refund.engine.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.strategy.common.global.StrategyGlobal;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.SettlementForceRefundValidatException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.PrePaymentVo;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.settlement.forcedRefund.request.FRefOperateVo;
import com.pzj.settlement.forcedRefund.service.ForceRefund;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderEntity;

/**
 * 清结算系统冻结积累回滚
 *
 * @author DongChunfu
 * @date 2016年12月21日
 */
@Component(value = "settlRefundCancleHandler")
public class SettlRefundCancleHandler {

	private static final Logger logger = LoggerFactory.getLogger(SettlRefundCancleHandler.class);

	@Autowired
	private ForceRefund forceRefund;

	@Autowired
	private AccountBussinessService accountBussinessService;

	public void handler(final OrderEntity sellOrder, final int isForce, final String refundId) {

		if (sellOrder.getOnline_pay() == StrategyGlobal.IsNeedPaymentEnum.Not.getId() || sellOrder.getTotal_amount() == 0) {
			return;
		}
		if (RefundApplyTypeEnum.FORCE.getType() == isForce) {
			cancleForceRefund(sellOrder, refundId);
		}

		cancelRefundFrozenBalance(sellOrder, refundId);
	}

	private void cancleForceRefund(final OrderEntity sellOrder, final String refundId) {

		final FRefOperateVo fRefVo = new FRefOperateVo();
		fRefVo.setDistributorId(sellOrder.getReseller_id());
		fRefVo.setOrderId(sellOrder.getOrder_id());
		fRefVo.setSignId(refundId);
		if (logger.isInfoEnabled()) {
			logger.info("清结算验证强制退款,reqModel:{}.", JSONConverter.toJson(fRefVo));
		}
		Result<Boolean> result = null;
		try {
			result = forceRefund.forceRefundOperate(fRefVo);
		} catch (final Throwable t) {
			logger.error("清结算验证强制退款异常,reqModel:" + JSONConverter.toJson(fRefVo), t);
			throw new SettlementForceRefundValidatException(RefundErrorCode.SETTLEMENT_FORCE_REFUND_VALIDATE_ERROR_CODE,
					"清结算验证强制退款异常");
		}
		if (logger.isInfoEnabled()) {
			logger.info("清结算验证强制退款,reqModel:{},result:{}.", JSONConverter.toJson(fRefVo), result);
		}

		if (!result.isOk()) {
			throw new SettlementForceRefundValidatException(RefundErrorCode.SETTLEMENT_FORCE_REFUND_VALIDATE_ERROR_CODE,
					result.getErrorMsg());
		}
	}

	private void cancelRefundFrozenBalance(final OrderEntity sellOrder, final String refundId) {

		final PrePaymentVo prePaymentVo = new PrePaymentVo();
		prePaymentVo.setOrderId(sellOrder.getOrder_id());
		prePaymentVo.setSignId(refundId);
		prePaymentVo.setUserId(SalePortEnum.isMicShop(sellOrder.getSale_port()) ? sellOrder.getReseller_id() : sellOrder
				.getPayer_id());
		prePaymentVo.setIsMShop(SalePortEnum.isMicShop(sellOrder.getSale_port()));

		if (logger.isInfoEnabled()) {
			logger.info("call accountBussinessService.cancelPayOrder sendParam:" + JSONConverter.toJson(prePaymentVo));
		}

		final Result<Boolean> result = accountBussinessService.cancelRefund(prePaymentVo);

		if (logger.isInfoEnabled()) {
			logger.info("call accountBussinessService.cancelPayOrder reqResult:" + JSONConverter.toJson(result));
		}

	}

}

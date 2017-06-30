package com.pzj.core.trade.refund.engine.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.payment.model.RefundApplyRequestModel;
import com.pzj.core.payment.service.RefundService;
import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.CallPaymentRefundException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;

@Component
public class PaymentRefundHandler {
	private static final Logger logger = LoggerFactory.getLogger(PaymentRefundHandler.class);
	@Resource(name = "paymentRefundService")
	private RefundService paymentRefundService;

	/**
	 * 调用支付系统退款申请服务
	 *
	 * @param reqParam
	 */
	public Result<Boolean> callPaymentRefundMoney(final String transactionId, final RefundApplyEntity refudnApply,
			final double thirdRefundAmount) {
		final RefundApplyRequestModel reqParam = buildRefundApplyReqParam(transactionId, refudnApply, thirdRefundAmount);
		logger.info("支付系统退款申请,reqModel:{}", JSONConverter.toJson(reqParam));
		Result<Boolean> result = null;
		try {
			result = paymentRefundService.refundApply(reqParam);
		} catch (final Throwable t) {
			logger.error("支付系统退款申请失败,reqModel:{}.", JSONConverter.toJson(reqParam));
			throw new CallPaymentRefundException(RefundErrorCode.PAYMENT_REFUND_ERROR_CODE, "支付系统退款申请失败");
		}
		logger.info("支付系统退款申请结果, reqModel:{},reqResult:{}.", JSONConverter.toJson(reqParam), JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new CallPaymentRefundException(RefundErrorCode.PAYMENT_REFUND_ERROR_CODE, "支付系统退款申请失败");
		}
		return result;
	}

	private RefundApplyRequestModel buildRefundApplyReqParam(final String transactionId, final RefundApplyEntity refudnApply,
			final double thirdRefundAmount) {
		final RefundApplyRequestModel reqParam = new RefundApplyRequestModel();
		reqParam.setOrderId(transactionId);
		reqParam.setRefundId(refudnApply.getRefundId());
		reqParam.setRefundMoney(MoneyUtils.getMoenyNumber(thirdRefundAmount));
		if (refudnApply.getIsForce() == RefundApplyTypeEnum.FORCE.getType()) {
			reqParam.setRefundType(2);
		} else {
			reqParam.setRefundType(0);
		}
		return reqParam;
	}
}

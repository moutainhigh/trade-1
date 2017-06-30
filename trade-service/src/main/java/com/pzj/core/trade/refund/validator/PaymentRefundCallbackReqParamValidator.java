package com.pzj.core.trade.refund.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.refund.model.ThirdPayRefundRspModel;

/**
 * 支付系统退款回调请求参数验证器
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "paymentRefundCallbackReqParamValidator")
public class PaymentRefundCallbackReqParamValidator
		implements ObjectConverter<ThirdPayRefundRspModel, Void, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final ThirdPayRefundRspModel reqModel, final Void x) {

		if (Check.NuNObject(reqModel)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请参数为空.");
		}

		if (Check.NuNString(reqModel.getOrderId())) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款回调订单ID为空");
		}

		if (Check.NuNString(reqModel.getRefundId())) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款回调退款ID为空");
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

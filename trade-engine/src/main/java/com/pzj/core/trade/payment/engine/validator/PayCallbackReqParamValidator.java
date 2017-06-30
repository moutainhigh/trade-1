package com.pzj.core.trade.payment.engine.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.payment.engine.exception.PayErrorCode;
import com.pzj.core.trade.payment.engine.exception.PayReqParamErrorException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.payment.vo.PayCallbackVO;

@Component(value = "payCallbackReqParamValidator")
public class PayCallbackReqParamValidator implements ObjectConverter<PayCallbackVO, ServiceContext, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final PayCallbackVO reqModel, final ServiceContext context) {

		if (Check.NuNObject(reqModel)) {
			throw new PayReqParamErrorException(PayErrorCode.PAY_CALLBACK_REQ_PARAM_ERROR_CODE, "支付回调请求参数为空");
		}

		if (Check.NuNStrStrict(reqModel.getOrderId())) {
			throw new PayReqParamErrorException(PayErrorCode.PAY_CALLBACK_REQ_PARAM_ERROR_CODE, "支付回调请求参数订单ID为空");
		}

		final Integer payType = reqModel.getPayType();
		if (payType != 1 && payType != 2) {
			throw new PayReqParamErrorException(PayErrorCode.PAY_CALLBACK_REQ_PARAM_ERROR_CODE, "支付回调请求参数支付方式错误[" + payType + "]");
		}

		final double money = reqModel.getMoney();
		if (money <= 0D) {
			throw new PayReqParamErrorException(PayErrorCode.PAY_CALLBACK_REQ_PARAM_ERROR_CODE, "支付回调请求参数支付金额错误[" + money + "]");
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

package com.pzj.core.trade.payment.engine.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.payment.engine.exception.PayErrorCode;
import com.pzj.core.trade.payment.engine.exception.PayReqParamErrorException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.payment.vo.ChildOrderPaymentModel;

@Component(value = "payRequestParamValidator")
public class PayRequestParamValidator implements ObjectConverter<ChildOrderPaymentModel, ServiceContext, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final ChildOrderPaymentModel reqModel, final ServiceContext context) {

		if (Check.NuNObject(reqModel)) {
			throw new PayReqParamErrorException(PayErrorCode.REQ_PARAM_ERROR_CODE, "支付请求参数为空");
		}

		if (Check.NuNStrStrict(reqModel.getOrderId())) {
			throw new PayReqParamErrorException(PayErrorCode.REQ_PARAM_ERROR_CODE, "支付请求参数订单ID为空");
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

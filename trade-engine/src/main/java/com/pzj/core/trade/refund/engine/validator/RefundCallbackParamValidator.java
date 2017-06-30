package com.pzj.core.trade.refund.engine.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.framework.toolkit.Check;

/**
 * 退款回调请求参数验证
 * 
 * @author DongChunfu
 * @date 2016年12月18日
 */
@Component(value = "refundCallbackParamValidator")
public class RefundCallbackParamValidator {

	public Boolean convert(final String orderId, final String refundId, final int refundType) {

		if (Check.NuNString(orderId)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款回调参数orderId:[" + orderId + "]不得为空.");
		}
		if (Check.NuNString(refundId)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE,
					"退款回调参数refundId:[" + refundId + "]不得为空.");
		}

		RefundApplyTypeEnum.getRefundApplyType(refundType);

		return Boolean.TRUE;
	}

}
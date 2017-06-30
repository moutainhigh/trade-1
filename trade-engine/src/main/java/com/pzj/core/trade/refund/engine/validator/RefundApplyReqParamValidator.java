package com.pzj.core.trade.refund.engine.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.refund.model.RefundApplyReqModel;

/**
 * 退款申请请求参数验证器
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "refundApplyReqParamValidator")
public class RefundApplyReqParamValidator implements ObjectConverter<RefundApplyReqModel, Void, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final RefundApplyReqModel reqModel, final Void x) {

		if (Check.NuNObject(reqModel)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请参数为空.");
		}

		if (Check.NuNString(reqModel.getOrderId())) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请销售订单ID为空");
		}

		RefundInitPartyEnum.getRefundInitPartyEnum(reqModel.getInitParty());

		final Long initiatorId = reqModel.getInitiatorId();
		if (Check.NuNObject(initiatorId) || initiatorId <= 0) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请发起人ID[" + initiatorId + "]错误.");
		}

		RefundApplyTypeEnum.getRefundApplyType(reqModel.getRefundType());
		return new Result<Boolean>(Boolean.TRUE);
	}

}

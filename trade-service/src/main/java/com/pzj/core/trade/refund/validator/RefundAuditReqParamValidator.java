/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundAuditPartyEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditResultEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.refund.model.RefundAuditReqModel;

/**
 * 退款审核请求参数验证器
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
@Component(value = "refundAuditReqParamValidator")
public class RefundAuditReqParamValidator implements ObjectConverter<RefundAuditReqModel, Void, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final RefundAuditReqModel reqModel, final Void x) {

		if (Check.NuNObject(reqModel)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核参数为空.");
		}

		final String refundId = reqModel.getRefundId();
		if (Check.NuNString(refundId)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核参数[refundId]为空");
		}

		if (Check.NuNObject(reqModel.getAuditorId())) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核参数[auditorId]为空");
		}

		if(Check.NuNString(reqModel.getSaleOrderId())){
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核参数[saleOrderId]为空");
		}
		RefundAuditPartyEnum.getRefundAuditPartyEnum(reqModel.getAuditorParty());
		RefundAuditResultEnum.getRefundAuditResultEnum(reqModel.getAuditResult());

		//		if (reqModel.getAuditResult() == RefundAuditResultEnum.REFUSE.getResult()) {
		//			if (Check.NuNObject(reqModel.getRefusedMsg())) {
		//				throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核参数[refusedMsg]为空");
		//			}
		//		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

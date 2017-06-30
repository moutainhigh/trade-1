/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.refund.model.QueryRefundApplyReqModel;

/**
 * 退款申请分页查询请求参数验证器
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
@Component(value = "queryRefundApplyPageReqParamValidator")
public class QueryRefundApplyPageReqParamValidator
		implements ObjectConverter<QueryRefundApplyReqModel, Void, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final QueryRefundApplyReqModel reqModel, final Void x) {

		if (Check.NuNObject(reqModel)) {
			throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请查询请求参数为空.");
		}

		final Integer auditState = reqModel.getAuditState();
		if (auditState != null) {
			if (0 > auditState || auditState > 3) {
				throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE,
						"退款申请查询请求参数[" + auditState + "]不符合要求.");
			}
		}

		final Integer thirdAuditState = reqModel.getThirdAuditState();
		if (thirdAuditState != null) {
			if (1 > thirdAuditState || thirdAuditState > 3) {
				throw new RefundArgsException(RefundErrorCode.REQ_PARAM_ERROR_CODE,
						"退款申请查询请求参数[" + thirdAuditState + "]不符合要求.");
			}
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

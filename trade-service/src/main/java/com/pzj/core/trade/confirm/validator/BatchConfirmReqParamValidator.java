/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.exception.ConfirmReqParamException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.model.BatchConfirmReqModel;

/**
 * 批量核销请求参数验证器
 *
 * @author DongChunfu
 * @version $Id: BatchConfirmReqParamValidator.java, v 0.1 2017年2月27日 上午10:22:45 DongChunfu Exp $
 */
@Component(value = "batchConfirmReqParamValidator")
public class BatchConfirmReqParamValidator implements ObjectConverter<BatchConfirmReqModel, ServiceContext, Result<Boolean>> {

	/**
	 * @see com.pzj.framework.converter.ObjectConverter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Result<Boolean> convert(final BatchConfirmReqModel reqModel, final ServiceContext serviceContext) {

		if (Check.NuNObject(reqModel)) {
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "请求参数不得为空");
		}

		final List<String> sellOrderIds = reqModel.getSellOrderIds();
		if (Check.NuNCollections(sellOrderIds)) {
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE,
					"请求参数sellOrderIds[" + sellOrderIds + "]不符合要求");
		}

		return new Result<Boolean>();
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.common.ConfirmTypeEnum;
import com.pzj.core.trade.confirm.exception.ConfirmReqParamException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.model.ConfirmModel;

/**
 * 核销请求参数校验器
 *
 * @author DongChunfu
 * @version $Id: ConfirmReqParamValidator.java, v 0.1 2017年3月3日 上午11:54:42 Administrator Exp $
 */
@Component(value = "confirmReqParamValidator")
public class ConfirmReqParamValidator implements ObjectConverter<ConfirmModel, ServiceContext, Result<Boolean>> {
	private static final Logger logger = LoggerFactory.getLogger(ConfirmReqParamValidator.class);

	/**
	 * @see com.pzj.framework.converter.ObjectConverter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Result<Boolean> convert(final ConfirmModel reqModel, final ServiceContext serviceContext) {

		if (Check.NuNObject(reqModel)) {
			logger.warn("confirm request model can not bu null.");
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "请求参数不得为空");
		}

		final long voucherId = reqModel.getVoucherId();
		if (voucherId <= 0) {
			logger.error("核销参数错误, voucherId:{},is illegal.", voucherId);
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "请求参数voucherId[" + voucherId + "]不符合要求");
		}

		ConfirmTypeEnum.getConfirmTypeEnum(reqModel.getType());
		return new Result<Boolean>();
	}
}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.financeCenter.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.exception.ConfirmReqParamException;
import com.pzj.core.trade.finance.common.SaasRoleEnum;
import com.pzj.core.trade.finance.common.SettleStateEnum;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.financeCenter.request.SettleGatherReqModel;

/**
 * 结算汇总请求参数校验器
 *
 * @author DongChunfu
 * @version $Id: SettleGatherReqParamValidator.java, v 0.1 2017年5月15日 上午11:04:56 DongChunfu Exp $
 */
@Component(value = "settleGatherReqParamValidator")
public class SettleGatherReqParamValidator implements ObjectConverter<SettleGatherReqModel, ServiceContext, Result<Boolean>> {
	private static final Logger logger = LoggerFactory.getLogger(SettleGatherReqParamValidator.class);

	@Override
	public Result<Boolean> convert(final SettleGatherReqModel reqModel, final ServiceContext serviceContext) {

		if (Check.NuNObject(reqModel)) {
			logger.error("settleGather request model can not bu null.");
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "请求参数不得为空");
		}
		final long userId = reqModel.getUserId();
		if (userId < 0) {
			logger.error("settle detail request model,illegal userId:[{}].", userId);
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "结算明细查询请求参数,用户ID错误:" + userId);
		}

		final int settleState = reqModel.getSettleState();
		try {
			SettleStateEnum.getSettleStateEnumByState(settleState);
		} catch (final Throwable t) {
			logger.error("settle detail request model,illegal settleState:[{}].", settleState);
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "结算明细查询请求参数,结算状态错误:" + userId);
		}

		final int userRole = reqModel.getUserRole();
		try {
			SaasRoleEnum.getSaasRoleEnumByRole(userRole);
		} catch (final Throwable t) {
			logger.error("settle detail request model,illegal userRole:[{}].", userRole);
			throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "结算明细查询请求参数,用户角色错误:" + userId);
		}

		return new Result<Boolean>();
	}
}

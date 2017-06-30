package com.pzj.core.trade.confirm.service.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.confirm.common.ConfirmErrorCode;
import com.pzj.core.trade.confirm.engine.ConfirmCodeEngine;
import com.pzj.core.trade.confirm.engine.GetConfirmCodeEngine;
import com.pzj.core.trade.confirm.exception.ConfirmCodeException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.model.ConfirmCodeModel;
import com.pzj.trade.confirm.model.MfCodeModel;
import com.pzj.trade.confirm.response.MfcodeResult;
import com.pzj.trade.confirm.service.ConfirmCodeService;

/**
 * 核销码服务实现.
 * @author YRJ
 *
 */
@Service("confirmCodeService")
public class ConfirmCodeServiceImpl implements ConfirmCodeService {

	private final static Logger logger = LoggerFactory.getLogger(ConfirmCodeServiceImpl.class);

	@Autowired
	private ConfirmCodeEngine confirmCodeEngine;

	@Autowired
	private GetConfirmCodeEngine getConfirmCodeEngine;

	@Override
	public Result<String> verify(final ConfirmCodeModel codeModel, final ServiceContext serviceContext) {
		logger.info("verify code. codeModel: {}, serviceContext: {}.", codeModel, serviceContext);

		if (Check.NuNObject(codeModel) || Check.NuNObject(codeModel.getSupplierId(), codeModel.getCode())) {
			return new Result<String>(41001, "验证失败, 请指定合法参数");
		}

		try {
			final Result<String> result = confirmCodeEngine.verifyMFCode(codeModel);

			logger.info("verify code. codeModel: {}, serviceContext: {}, result: {}.", codeModel, serviceContext,
					ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
			return result;
		} catch (final Throwable e) {
			logger.error("verify code fail. codeModel: " + codeModel + ", serviceContext:" + serviceContext, e);
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ConfirmCodeException(ConfirmErrorCode.CONFIRM_CODE_POINT_ERROR_CODE, "魔方码验证失败, 请稍后重试.", e);
		}
	}

	@Override
	public Result<MfcodeResult> getMfcode(final MfCodeModel codeModel, final ServiceContext serviceContext) {
		logger.info("get code. codeModel: {}, serviceContext: {}.", codeModel, serviceContext);
		if (Check.NuNObject(codeModel) || codeModel.getCodeId() <= 0) {
			return new Result<MfcodeResult>(41001, "获取魔方码失败.");
		}

		MfcodeResult mfcode;
		try {
			mfcode = getConfirmCodeEngine.getMfcode(codeModel.getCodeId());
			logger.info("get code. codeModel: {}, serviceContext: {}, result: {}.", codeModel, serviceContext,
					ToStringBuilder.reflectionToString(mfcode, ToStringStyle.SHORT_PREFIX_STYLE));

		} catch (final Throwable e) {
			logger.error("get code fail. codeModel: " + codeModel + ", serviceContext:" + serviceContext, e);
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ConfirmCodeException(ConfirmErrorCode.CONFIRM_CODE_POINT_ERROR_CODE, "魔方码获取失败, 请稍后重试.", e);
		}

		return new Result<MfcodeResult>(mfcode);
	}
}

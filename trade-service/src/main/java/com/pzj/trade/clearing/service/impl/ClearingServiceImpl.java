package com.pzj.trade.clearing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.clean.engine.CleaningEngine;
import com.pzj.core.trade.clean.engine.exception.CleaningException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.clearing.model.CleaningModel;
import com.pzj.trade.clearing.service.ClearingService;

/**
 * 清算服务接口实现.
 * @author YRJ
 *
 */
@Service(value = "clearingService")
public class ClearingServiceImpl implements ClearingService {

	private final static Logger logger = LoggerFactory.getLogger(ClearingServiceImpl.class);

	@Autowired
	private CleaningEngine cleaningEngine;

	@Override
	@Deprecated
	public Result<Boolean> clean(final CleaningModel cleaningModel, final ServiceContext serviceContext) {

		logger.info("清算. cleaningModel: {}, serviceContext: {}.", cleaningModel, serviceContext);

		if (Check.NuNObject(cleaningModel) || Check.NuNObject(cleaningModel.getOrderId(), cleaningModel.getMerchId())) {
			return new Result<Boolean>(false);
		}

		try {
			cleaningEngine.cleaning(cleaningModel);
		} catch (final Throwable e) {
			logger.error("清算失败.cleaningModel: " + cleaningModel + ",serviceContext:" + serviceContext, e);
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new CleaningException("清算失败", e);
		}

		return new Result<Boolean>(true);
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.filled.engine.FilledEngine;
import com.pzj.core.trade.order.engine.TouristEngine;
import com.pzj.core.trade.order.validator.TouristInModelValidator;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.OrderFillModel;
import com.pzj.trade.order.model.TouristEditInModel;
import com.pzj.trade.order.service.FilledService;
import com.pzj.voucher.engine.exception.VoucherException;

/**
 * 
 * @author Administrator
 * @version $Id: FilledServiceImpl.java, v 0.1 2017年2月20日 上午10:33:54 Administrator Exp $
 */
@Service(value = "filledService")
public class FilledServiceImpl implements FilledService {
	private static final Logger logger = LoggerFactory.getLogger(FilledServiceImpl.class);

	@Autowired
	private FilledEngine filledEngine;

	@Resource(name = "touristInModelValidator")
	private TouristInModelValidator touristInModelValidator;

	@Resource(name = "touristEngine")
	private TouristEngine touristEngine;

	@Override
	public Result<Boolean> updateFilledModel(final OrderFillModel order, final ServiceContext context) {
		logger.info("修改填单项参数:" + JSONConverter.toJson(order));
		if (Check.NuNObject(order) || Check.NuNObject(order.getOrder_id()) || Check.NuNCollections(order.getFilleds())) {
			return new Result<Boolean>(14001, "修改填单项参数不能为空.");
		}

		try {
			filledEngine.updateFilled(order);
		} catch (final Throwable e) {
			logger.error("修改填单项失败：" + JSONConverter.toJson(order), e);
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ServiceException("修改填单项失败.", e);
		}
		logger.info("修改填单项成功");
		return new Result<Boolean>(Boolean.TRUE);
	}

	@Override
	public Result<Boolean> updateTourist(final TouristEditInModel inModel) {
		logger.info("更新游客信息: " + inModel);
		try {
			touristInModelValidator.validate(inModel);
			touristEngine.updateTourist(inModel);
		} catch (final Throwable e) {
			logger.error("更新游客信息失败, reqModel: " + (inModel), e);

			if (e instanceof VoucherException) {
				final TradeException ex = (TradeException) e;
				return new Result<Boolean>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<Boolean>(10500, "更新游客信息失败.");
		}

		logger.info("更新游客信息成功: " + inModel);
		return new Result<Boolean>(Boolean.TRUE);
	}
}

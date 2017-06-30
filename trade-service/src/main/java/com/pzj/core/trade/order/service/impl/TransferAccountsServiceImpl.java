/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.TransferAccountsEngine;
import com.pzj.core.trade.order.engine.common.OrderErrorCode;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.model.TransferAccountsReqModel;
import com.pzj.trade.order.service.TransferAccountsService;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 * 分账服务实现
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsServiceImpl.java, v 0.1 2017年3月25日 下午12:47:33 DongChunfu Exp $
 */
@Component(value = "transferAccountsService")
public class TransferAccountsServiceImpl implements TransferAccountsService {
	private static final Logger logger = LoggerFactory.getLogger(TransferAccountsServiceImpl.class);

	@Resource(name = "transferAccountsQueryRepParamValidator")
	private ObjectConverter<TransferAccountsReqModel, ServiceContext, Result<Boolean>> transferAccountsQueryRepParamValidator;

	@Resource(name = "transferAccountsEngine")
	private TransferAccountsEngine transferAccountsEngine;

	/**
	 * @see com.pzj.trade.order.service.TransferAccountsService#queryTransferAccountsDetail(com.pzj.trade.order.model.TransferAccountsReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<QueryResult<OrderTransferAccountsVO>> queryTransferAccountsDetail(final TransferAccountsReqModel reqModel,
			final ServiceContext context) {
		try {
			transferAccountsQueryRepParamValidator.convert(reqModel, context);
			logger.info("transfer accounts query param,reqModel:{}.", reqModel);

			final QueryResult<OrderTransferAccountsVO> qureyResult = transferAccountsEngine.qureyTransferAccounts(reqModel);
			return new Result<QueryResult<OrderTransferAccountsVO>>(qureyResult);
		} catch (final Throwable t) {
			logger.error("transfer accounts query error,reqModel:" + reqModel, t);
			if (t instanceof OrderException) {
				final OrderException oe = (OrderException) t;
				return new Result<QueryResult<OrderTransferAccountsVO>>(oe.getErrCode(), oe.getMessage());
			}
			return new Result<QueryResult<OrderTransferAccountsVO>>(OrderErrorCode.ORDER_ERROR_CODE, "分账查询系统错误");
		}
	}
}

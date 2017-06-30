/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.export.engine.exception.OrderExportException;
import com.pzj.core.trade.export.entity.QueryOrderExportLogParam;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.export.model.OrderExportQueryReqModel;
import com.pzj.trade.export.model.OrderExportRepModel;
import com.pzj.trade.export.model.OrderExportVerifyRepModel;
import com.pzj.trade.export.model.OrderExportVerifyReqModel;
import com.pzj.trade.export.service.OrderExportQueryService;

/**
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
@Service(value = "orderExportQueryService")
public class OrderExportQueryServiceImpl implements OrderExportQueryService {

	private final static Logger logger = LoggerFactory.getLogger(OrderExportQueryServiceImpl.class);

	@Resource(name = "orderExportQueryParamValidator")
	private ObjectConverter<OrderExportQueryReqModel, Void, Result<Boolean>> orderExportQueryParamValidator;

	@Resource(name = "orderExportVerifyParamValidator")
	private ObjectConverter<OrderExportVerifyReqModel, Void, Result<Boolean>> orderExportVerifyParamValidator;

	@Resource(name = "orderExportQueryEngine")
	private OrderExportQueryEngine orderExportQueryEngine;

	@Override
	public Result<QueryResult<OrderExportRepModel>> queryExports(final OrderExportQueryReqModel reqModel) {

		try {

			orderExportQueryParamValidator.convert(reqModel, null);
			logger.info("order export log reqModel:{}.", reqModel);

			final QueryOrderExportLogParam param = queryExportsReqParamConvert(reqModel);
			final QueryResult<OrderExportRepModel> queryResult = orderExportQueryEngine.queryPage(param);

			return new Result<QueryResult<OrderExportRepModel>>(queryResult);

		} catch (final Throwable t) {
			logger.error("order export log error,reqModel:", reqModel, t);

			if (t instanceof OrderExportException) {
				throw (OrderExportException) t;
			}
			throw new OrderExportException(10500, t.getMessage(), t);
		}
	}

	private QueryOrderExportLogParam queryExportsReqParamConvert(final OrderExportQueryReqModel reqModel) {
		final QueryOrderExportLogParam convertParam = new QueryOrderExportLogParam();

		convertParam.setCreateBy(reqModel.getCreateBy());
		convertParam.setEndTime(reqModel.getEndTime());
		convertParam.setStartTime(reqModel.getStartTime());

		convertParam.setPageAble(reqModel.getPageAble());
		convertParam.setPageIndex(reqModel.getPageIndex());
		convertParam.setPageSize(reqModel.getPageSize());

		return convertParam;
	}

	/**
	 * @see com.pzj.trade.export.service.OrderExportQueryService#queryExportLogById(com.pzj.trade.export.model.OrderExportVerifyReqModel)
	 */
	@Override
	public Result<OrderExportVerifyRepModel> verifyExportLog(final OrderExportVerifyReqModel reqModel) {

		try {
			orderExportVerifyParamValidator.convert(reqModel, null);
			logger.info("order export verify reqModel:{}.", reqModel);

			final OrderExportVerifyRepModel queryResult = orderExportQueryEngine.queryExportVerifyById(reqModel);

			return new Result<OrderExportVerifyRepModel>(queryResult);

		} catch (final Throwable t) {
			logger.error("order export verify error,reqModel:", reqModel, t);

			if (t instanceof OrderExportException) {
				throw (OrderExportException) t;
			}
			throw new OrderExportException(10500, t.getMessage(), t);
		}
	}

}

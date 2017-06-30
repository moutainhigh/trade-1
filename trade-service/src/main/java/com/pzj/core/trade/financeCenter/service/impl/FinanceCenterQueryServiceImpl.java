package com.pzj.core.trade.financeCenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.confirm.exception.ConfirmException;
import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.finance.engine.SettleDetailEngine;
import com.pzj.core.trade.finance.engine.SettleGatherEngine;
import com.pzj.core.trade.finance.exception.FinanceErrorCode;
import com.pzj.core.trade.order.engine.AccountOrderQueryEngine;
import com.pzj.core.trade.order.engine.OrderStatisticsEngine;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.financeCenter.request.AccountOrdersReqModel;
import com.pzj.trade.financeCenter.request.OrderStatisticsReqModel;
import com.pzj.trade.financeCenter.request.SettleDetailReqModel;
import com.pzj.trade.financeCenter.request.SettleGatherReqModel;
import com.pzj.trade.financeCenter.response.AccountOrdersRespModel;
import com.pzj.trade.financeCenter.response.OrderStatisticsRespModel;
import com.pzj.trade.financeCenter.response.SettleDetailRepModel;
import com.pzj.trade.financeCenter.response.SettleGatherRepModel;
import com.pzj.trade.financeCenter.service.FinanceCenterQueryService;

/**
 * 订单查询服务实现.
 * <ul>
 * 功能如下:
 * <li>订单列表查询.</li>
 * <li>订单详情查询.</li>
 * </ul>
 *
 * @author YRJ
 *
 */
@Service(value = "financeCenterQueryService")
public class FinanceCenterQueryServiceImpl implements FinanceCenterQueryService {
	private final static Logger logger = LoggerFactory.getLogger(FinanceCenterQueryServiceImpl.class);

	@Resource(name = "orderStatisticsEngine")
	private OrderStatisticsEngine orderStatisticsEngine;

	@Resource(name = "settleGatherReqParamValidator")
	private ObjectConverter<SettleGatherReqModel, ServiceContext, Result<Boolean>> settleGatherReqParamValidator;

	@Resource(name = "settleDetailReqParamValidator")
	private ObjectConverter<SettleDetailReqModel, ServiceContext, Result<Boolean>> settleDetailReqParamValidator;

	@Resource(name = "settleDetailEngine")
	private SettleDetailEngine settleDetailEngine;

	@Resource(name = "settleGatherEngine")
	private SettleGatherEngine settleGatherEngine;

	@Override
	public Result<OrderStatisticsRespModel> queryOrderStatistics(final OrderStatisticsReqModel reqModel, final ServiceContext context) {

		if (reqModel == null || (reqModel.getResellerId() == 0)) {
			logger.warn("查询 分销商补差金额参数为, reqModel: " + reqModel);
			return new Result<OrderStatisticsRespModel>(10601, "查询 分销商补差金额失败, 请指定正确的参数[" + reqModel == null ? null : reqModel.getResellerId() + "].");
		}
		OrderStatisticsRespModel respModel = new OrderStatisticsRespModel();
		try {
			respModel = orderStatisticsEngine.queryOrderStatistics(reqModel);
		} catch (final Throwable e) {
			logger.error("查询 分销商补差金额, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<OrderStatisticsRespModel>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<OrderStatisticsRespModel>(10500, "查询 分销商补差金额失败.");
		}
		return new Result<OrderStatisticsRespModel>(respModel);

	}

	@Resource(name = "accountOrderQueryEngine")
	private AccountOrderQueryEngine accountOrderQueryEngine;

	@Override
	public Result<QueryResult<AccountOrdersRespModel>> queryOrdersForAccount(final AccountOrdersReqModel reqModel, final ServiceContext context) {
		if (reqModel == null) {
			return new Result<QueryResult<AccountOrdersRespModel>>();
		}

		//		if (logger.isInfoEnabled()) {
		//			logger.info("账户余额订单列表, 入參. reqModel: " + (reqModel));
		//		}

		final QueryResult<AccountOrdersRespModel> qr = new QueryResult<AccountOrdersRespModel>(0, 0);

		try {
			final List<AccountOrdersRespModel> orders = accountOrderQueryEngine.queryOrders(reqModel);
			qr.setRecords(orders);
		} catch (final Throwable e) {
			logger.error("账户余额订单列表, 失败. reqModel: " + JSONConverter.toJson(reqModel), e);

			if (e instanceof TradeException) {
				final TradeException ex = (TradeException) e;
				return new Result<QueryResult<AccountOrdersRespModel>>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<QueryResult<AccountOrdersRespModel>>(10500, "账户余额订单列表查询失败.");
		}
		//
		//		if (logger.isInfoEnabled()) {
		//			logger.info("账户余额订单列表, 出參. respModel: " + (qr));
		//		}
		return new Result<QueryResult<AccountOrdersRespModel>>(qr);

	}

	/**
	 * @see com.pzj.trade.financeCenter.service.FinanceCenterQueryService#settleGather(com.pzj.trade.financeCenter.request.SettleGatherReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<SettleGatherRepModel> settleGather(final SettleGatherReqModel reqModel, final ServiceContext context) {
		try {
			logger.info("settle gather query request,reqModel:{}.", reqModel);
			settleGatherReqParamValidator.convert(reqModel, context);

			final Result<SettleGatherRepModel> queryResult = settleGatherEngine.query(reqModel);

			if (!queryResult.isOk()) {
				logger.info("settle gather query fail,reqModel:{},msg:{}.", reqModel, queryResult.getErrorCode() + ":" + queryResult.getErrorMsg());
				return new Result<SettleGatherRepModel>(queryResult.getErrorCode(), queryResult.getErrorMsg());
			}
			logger.info("settle gather query request,resp:{}.", JSONConverter.toJson(queryResult));
			return queryResult;
		} catch (final Throwable t) {
			logger.error("settle gather error, reqModel:" + reqModel, t);

			if (t instanceof ConfirmException) {
				final ConfirmException ce = (ConfirmException) t;
				return new Result<SettleGatherRepModel>(ce.getErrCode(), ce.getMessage());
			}
			return new Result<SettleGatherRepModel>(FinanceErrorCode.FINANCE_GATHER_ERROR, "结算汇总失败");
		}
	}

	/**
	 * @see com.pzj.trade.financeCenter.service.FinanceCenterQueryService#settleDetail(com.pzj.trade.financeCenter.request.SettleDetailReqModel, com.pzj.framework.context.ServiceContext)
	 */
	@Override
	public Result<SettleDetailRepModel> settleDetail(final SettleDetailReqModel reqModel, final ServiceContext context) {
		try {
			logger.info("settle detail query request,reqModel:{}.", reqModel);
			settleDetailReqParamValidator.convert(reqModel, context);

			final Result<SettleDetailRepModel> queryResult = settleDetailEngine.query(reqModel);
			logger.info("method return data queryResult{}", JSONConverter.toJson(queryResult));
			if (!queryResult.isOk()) {
				logger.info("settle detail query fail,reqModel:{},msg:{}.", reqModel, queryResult.getErrorCode() + ":" + queryResult.getErrorMsg());
				return new Result<SettleDetailRepModel>(queryResult.getErrorCode(), queryResult.getErrorMsg());
			}
			return queryResult;
		} catch (final Throwable t) {
			logger.error("settle detail error, reqModel:" + reqModel, t);

			if (t instanceof ConfirmException) {
				final ConfirmException ce = (ConfirmException) t;
				return new Result<SettleDetailRepModel>(ce.getErrCode(), ce.getMessage());
			}
			return new Result<SettleDetailRepModel>(FinanceErrorCode.FINANCE_DETAIL_ERROR, "查询结算明细失败");
		}
	}
}

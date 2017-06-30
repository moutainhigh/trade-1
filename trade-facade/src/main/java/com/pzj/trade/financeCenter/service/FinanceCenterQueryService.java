package com.pzj.trade.financeCenter.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.financeCenter.request.AccountOrdersReqModel;
import com.pzj.trade.financeCenter.request.OrderStatisticsReqModel;
import com.pzj.trade.financeCenter.request.SettleDetailReqModel;
import com.pzj.trade.financeCenter.request.SettleGatherReqModel;
import com.pzj.trade.financeCenter.response.AccountOrdersRespModel;
import com.pzj.trade.financeCenter.response.OrderStatisticsRespModel;
import com.pzj.trade.financeCenter.response.SettleDetailRepModel;
import com.pzj.trade.financeCenter.response.SettleGatherRepModel;

/**
 * 订单查询服务.
 * @author YRJ
 *
 */
public interface FinanceCenterQueryService {

	/**SaaS供应订单列表查询*/
	Result<OrderStatisticsRespModel> queryOrderStatistics(OrderStatisticsReqModel orderVO, ServiceContext context);

	/**
	 * 根据订单id批量查询订单
	 * */

	Result<QueryResult<AccountOrdersRespModel>> queryOrdersForAccount(AccountOrdersReqModel reqModel, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.financeCenter.service.FinanceCenterQueryService#settleGather SAAS用户结算汇总信息查询服务
	 * @apiGroup 财务中心
	 * @apiName SAAS用户结算汇总信息查询服务
	 * @apiDescription SAAS用户结算汇总信息查询服务，适用[已结算（线上线下），未结算（线上线下）]
	 * @apiVersion 1.1.0
	 */
	Result<SettleGatherRepModel> settleGather(SettleGatherReqModel reqModel, ServiceContext context);

	/**
	 * @api {dubbo} com.pzj.trade.financeCenter.service.FinanceCenterQueryService#settleDetail SAAS用户结算详细信息查询服务
	 * @apiGroup 财务中心
	 * @apiName SAAS用户结算详细信息查询服务
	 * @apiDescription SAAS用户结算详细信息查询服务，适用[已结算（线上线下），未结算（线上线下）]
	 * @apiVersion 1.1.0
	 */
	Result<SettleDetailRepModel> settleDetail(SettleDetailReqModel reqModel, ServiceContext context);
}

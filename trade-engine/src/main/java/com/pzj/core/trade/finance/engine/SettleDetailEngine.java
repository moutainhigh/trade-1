/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.engine;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.finance.event.SettleDetailAmountCalculateEvent;
import com.pzj.core.trade.finance.event.SettleDetailMerchInfoEvent;
import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.financeCenter.request.SettleDetailReqModel;
import com.pzj.trade.financeCenter.response.SettleDetailAmountRepModel;
import com.pzj.trade.financeCenter.response.SettleDetailRepModel;
import com.pzj.trade.financeCenter.response.SettleMerchRepModel;
import com.pzj.trade.order.model.SettleDetailQueryEntity;

/**
 * 结算明细查询引擎
 *
 * @author DongChunfu
 * @version $Id: SettleDetailEngine.java, v 0.1 2017年5月19日 上午11:31:48 DongChunfu Exp $
 */
@Component(value = "settleDetailEngine")
public class SettleDetailEngine {
	private static final Logger logger = LoggerFactory.getLogger(SettleDetailEngine.class);

	@Resource(name = "settleDetailAmountCalculateEvent")
	private SettleDetailAmountCalculateEvent settleDetailAmountCalculateEvent;

	@Resource(name = "settleDetailMerchInfoEvent")
	private SettleDetailMerchInfoEvent settleDetailMerchInfoEvent;

	public Result<SettleDetailRepModel> query(final SettleDetailReqModel reqModel) {
		final SettleDetailRepModel queryResult = new SettleDetailRepModel();
		final QueryResult<SettleMerchRepModel> settleMerchResult = new QueryResult<SettleMerchRepModel>(
				reqModel.getCurrentPage(), reqModel.getPageSize());

		// 1：查询符合要求的交易ID集合
		final SettleDetailQueryEntity queryParam = buildQuerySettleDetailTradeIdsParam(reqModel);
		logger.debug("settle detail inner query param:{}.", queryParam);

		// 计算总额
		final SettleDetailAmountRepModel settleAmount = settleDetailAmountCalculateEvent.calculate(reqModel, queryParam);
		queryResult.setSettleAmount(settleAmount);

		// 查商品
		settleDetailMerchInfoEvent.query(settleMerchResult, queryParam);
		queryResult.setSettleMerches(settleMerchResult);

		return new Result<SettleDetailRepModel>(queryResult);
	}

	/**
	 * 构建查询参数
	 *
	 * @param reqModel
	 * @return
	 */
	private SettleDetailQueryEntity buildQuerySettleDetailTradeIdsParam(final SettleDetailReqModel reqModel) {
		final SettleDetailQueryEntity queryParam = new SettleDetailQueryEntity();
		// 共用参数
		queryParam.setBeginDate(reqModel.getBeginDate());
		queryParam.setEndDate(reqModel.getEndDate());

		// 汇总查询参数
		queryParam.setUserId(reqModel.getUserId());
		queryParam.setSettleState(reqModel.getSettleState());
		queryParam.setUserRole(reqModel.getUserRole());
		queryParam.setOnline(reqModel.getOnline());

		// 明细查询参数
		queryParam.setTransactionId(reqModel.getTransactionId());
		queryParam.setProductName(reqModel.getProductName());
		queryParam.setTradeState(reqModel.getTradeState());
		queryParam.setRelativeUserId(reqModel.getRelativeUserId());
		queryParam.setPageable(Boolean.TRUE);
		queryParam.setLimit(reqModel.getPageSize());
		queryParam.setOffset((reqModel.getCurrentPage() - 1) * reqModel.getPageSize());

		logger.debug("settle detail query param convert to :{}", queryParam);
		return queryParam;
	}
}
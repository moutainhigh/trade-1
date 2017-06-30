/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.event.QueryTransferAccountsDetailEvent;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;
import com.pzj.trade.order.entity.TransferAccountsDetailParamEntity;
import com.pzj.trade.order.model.TransferAccountsReqModel;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 *
 * 分账引擎
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsEngine.java, v 0.1 2017年3月25日 下午1:11:34 DongChunfu Exp $
 */
@Component(value = "transferAccountsEngine")
public class TransferAccountsEngine {
	private static final Logger logger = LoggerFactory.getLogger(TransferAccountsEngine.class);

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Resource(name = "queryTransferAccountsDetailEvent")
	private QueryTransferAccountsDetailEvent queryTransferAccountsDetailEvent;

	/**
	 * 查询分账明细
	 *
	 * @param queryParam
	 * @return
	 */
	public QueryResult<OrderTransferAccountsVO> qureyTransferAccounts(final TransferAccountsReqModel reqModel) {

		final TransferAccountsDetailParamEntity queryParam = convertQueryParam(reqModel);
		final QueryResult<OrderTransferAccountsVO> queryResult = new QueryResult<OrderTransferAccountsVO>(
				reqModel.getCurrentPage(), reqModel.getPageSize());

		//1:统计符合条件的交易总体条数
		final int totalNum = orderReadMapper.queryTransferAccountsDetailByPageParamCount(queryParam);
		logger.info("transfer accounts detail total num is {}.", totalNum);

		queryResult.setTotal(totalNum);
		if (totalNum == 0) {
			return queryResult;
		}

		//2:根据分页条件查询满足条件的交易ID集合
		final List<TransferAccountsBaseDataEntity> baseDatas = orderReadMapper.queryTransferAccountsBaseData(queryParam);

		//4:对每级订单进行分账操作
		final List<OrderTransferAccountsVO> transferAccountsResult = queryTransferAccountsDetailEvent
				.transferAccounts(baseDatas);
		logger.debug("transfer accounts result,reqModel:{},result:{}.", queryParam, transferAccountsResult);

		queryResult.setRecords(transferAccountsResult);
		return queryResult;
	}

	private TransferAccountsDetailParamEntity convertQueryParam(final TransferAccountsReqModel reqModel) {
		final TransferAccountsDetailParamEntity queryParam = new TransferAccountsDetailParamEntity();

		queryParam.setTransactionId(reqModel.getTransactionId());
		queryParam.setPayStartTime(reqModel.getPayStartTime());
		queryParam.setPayEndTime(reqModel.getPayEndTime());
		queryParam.setConfirmStartTime(reqModel.getConfirmStartTime());
		queryParam.setConfirmEndTime(reqModel.getConfirmEndTime());

		queryParam.setLimit(reqModel.getPageSize());

		queryParam.setOffSet((reqModel.getCurrentPage() - 1) * reqModel.getPageSize());

		return queryParam;
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.finance.common.SettleDetailTool;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.financeCenter.response.SettleMerchRepModel;
import com.pzj.trade.order.model.SettleDetailQueryEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 * 结算明细商品详细信息处理
 *
 * @author DongChunfu
 * @version $Id: Sub.java, v 0.1 2017年5月15日 下午5:53:23 DongChunfu Exp $
 */
@Component(value = "settleDetailMerchInfoEvent")
public class SettleDetailMerchInfoEvent {
	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Resource(name = "settleDetailTrigeTransferAccountEvent")
	private SettleDetailTrigeTransferAccountEvent settleDetailTrigeTransferAccountEvent;

	public void query(final QueryResult<SettleMerchRepModel> queryResult, final SettleDetailQueryEntity queryParam) {

		final int totalNum = orderReadMapper.countSettleDetailTradeByParam(queryParam);
		queryResult.setTotal(totalNum);
		if (totalNum == 0) {
			return;
		}
		
		final List<String> orderIds = orderReadMapper.querySettleDetailTradeIdsByParam(queryParam);

		final List<SettleMerchRepModel> settleMerches = merchReadMapper.querySettleMerchesByOrderIds(orderIds);

		final List<OrderTransferAccountsVO> transferAccouts = settleDetailTrigeTransferAccountEvent
				.transferAccoutStatistics(filterMerchTradeIds(settleMerches));

		wrapSettleMerchRepModel(settleMerches, transferAccouts, queryParam);

		queryResult.setRecords(settleMerches);
	}

	private List<String> filterMerchTradeIds(final List<SettleMerchRepModel> settleMerches) {
		final Set<String> tradeIds = new HashSet<>();
		for (final SettleMerchRepModel settleMerch : settleMerches) {
			tradeIds.add(settleMerch.getTradeId());
		}
		return new ArrayList<String>(tradeIds);
	}

	private void wrapSettleMerchRepModel(final List<SettleMerchRepModel> settleMerches,
			final List<OrderTransferAccountsVO> transferAccouts, final SettleDetailQueryEntity queryParam) {

		final long userRole = queryParam.getUserRole();
		final int payWay = queryParam.getOnline();
		final long userId = queryParam.getUserId();

		SettleDetailTool tool=new SettleDetailTool();
		Map<String,List<OrderTransferAccountsVO>> classificationMap=tool.getClassification(transferAccouts);
		
		for (final SettleMerchRepModel settleMerch : settleMerches) {
			List<OrderTransferAccountsVO> tempTransferAccouts=classificationMap.get(settleMerch.getTradeId());
			SettleMerchRepModel tempMerch=tool.getTempSettleMerchRepModel(tempTransferAccouts, userRole, payWay,userId);
			settleMerch.setRelativeUserId(tempMerch.getRelativeUserId());
			settleMerch.setIncome(tempMerch.getIncome());
			settleMerch.setProfit(tempMerch.getProfit());
			settleMerch.setExpense(tempMerch.getExpense());
			settleMerch.setOrderPay(tempMerch.getOrderPay());
			settleMerch.setRefundAmount(tempMerch.getRefundAmount());
		}

	}
	
}

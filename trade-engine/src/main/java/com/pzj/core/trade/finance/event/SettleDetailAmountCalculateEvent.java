/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.event.QueryTransferAccountsDetailEvent;
import com.pzj.core.trade.finance.common.SaasRoleEnum;
import com.pzj.core.trade.finance.common.SettleDetailTool;
import com.pzj.core.trade.finance.common.SettleWayEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.finance.read.FinanceReadMapper;
import com.pzj.trade.financeCenter.request.SettleDetailReqModel;
import com.pzj.trade.financeCenter.response.SettleDetailAmountRepModel;
import com.pzj.trade.financeCenter.response.SettleMerchRepModel;
import com.pzj.trade.order.model.SettleDetailQueryEntity;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 * 计算结算明细总额
 *
 * @author DongChunfu
 * @version $Id: Sub.java, v 0.1 2017年5月15日 下午5:53:23 DongChunfu Exp $
 */
@Component(value = "settleDetailAmountCalculateEvent")
public class SettleDetailAmountCalculateEvent {

	@Autowired
	private OrderReadMapper orderReadMapper;
	@Autowired
	private FinanceReadMapper financeReadMapper;

	@Resource(name = "queryTransferAccountsDetailEvent")
	private QueryTransferAccountsDetailEvent queryTransferAccountsDetailEvent;

	@Resource(name = "settleDetailTrigeTransferAccountEvent")
	private SettleDetailTrigeTransferAccountEvent settleDetailTrigeTransferAccountEvent;

	public SettleDetailAmountRepModel calculate(final SettleDetailReqModel reqModel, final SettleDetailQueryEntity queryParam) {

		final List<String> statisticsTrades = orderReadMapper.querySettleDetailAmountTradeByParam(queryParam);
		final List<OrderTransferAccountsVO> TransferAccounts = settleDetailTrigeTransferAccountEvent
				.transferAccoutStatistics(statisticsTrades);

		/**返利金额*/
		double rebateAmount = 0D;
		/**货款支付金额*/
		double accountAmount = 0D;

		final long userId = reqModel.getUserId();
		final int userRole = reqModel.getUserRole();
		final int payWay = reqModel.getOnline();

		SettleDetailTool tool=new SettleDetailTool();
		Map<String,List<OrderTransferAccountsVO>> classificationMap=tool.getClassification(TransferAccounts);
		
		Iterator iter = classificationMap.keySet().iterator();
		
		while(iter.hasNext()){
			String key=(String)iter.next();
			
			List<OrderTransferAccountsVO> tempTransferAccouts=classificationMap.get(key);
			SettleMerchRepModel tempMerch=tool.getTempSettleMerchRepModel(tempTransferAccouts, userRole, payWay,userId);
			
			rebateAmount+=tempMerch.getIncome();
			accountAmount+=tempMerch.getExpense();
		}
		final SettleDetailAmountRepModel repModel = new SettleDetailAmountRepModel();
		repModel.setAccountAmount(accountAmount);
		repModel.setRebateAmount(rebateAmount);
		//修改相对用户查询
		reqModel.setRelativeUserId(null);
		final List<Long> relativeUserIds=financeReadMapper.queryCurrentPageSettlePartyIds(reqModel, 0, 0, false);
		repModel.setRelativeUserIds(relativeUserIds);
		
		return repModel;
	}
	
	
}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.event.QueryTransferAccountsDetailEvent;
import com.pzj.trade.order.entity.TransferAccountsBaseDataEntity;
import com.pzj.trade.order.entity.TransferAccountsDetailParamEntity;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 *
 * @author DongChunfu
 * @version $Id: SettleDetailTrigeTransferAccountEvent.java, v 0.1 2017年5月18日 上午10:26:53 DongChunfu Exp $
 */
@Component(value = "settleDetailTrigeTransferAccountEvent")
public class SettleDetailTrigeTransferAccountEvent {
	private static final Logger logger = LoggerFactory.getLogger(SettleDetailTrigeTransferAccountEvent.class);

	private final ExecutorService executor = Executors.newFixedThreadPool(8)/*Runtime.getRuntime().availableProcessors() - 1)*/;

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Resource(name = "queryTransferAccountsDetailEvent")
	private QueryTransferAccountsDetailEvent queryTransferAccountsDetailEvent;

	public List<OrderTransferAccountsVO> transferAccoutStatistics(final List<String> statisticsTrades) {

		final List<OrderTransferAccountsVO> transferAccounts = new ArrayList<>();

		final List<Future<List<OrderTransferAccountsVO>>> asyncRestul = new ArrayList<>();
		for (final String transactionId : statisticsTrades) {
			asyncRestul.add(executor.submit(new Callable<List<OrderTransferAccountsVO>>() {
				@Override
				public List<OrderTransferAccountsVO> call() throws Exception {
					final TransferAccountsDetailParamEntity baseParam = new TransferAccountsDetailParamEntity();
					baseParam.setTransactionId(transactionId);
					baseParam.setPageable(false);

					final List<TransferAccountsBaseDataEntity> baseDatas = orderReadMapper
							.queryTransferAccountsBaseData(baseParam);
					return queryTransferAccountsDetailEvent.transferAccounts(baseDatas);
				}
			}));
		}

		for (final Future<List<OrderTransferAccountsVO>> future : asyncRestul) {
			try {
				transferAccounts.addAll(future.get());
			} catch (InterruptedException | ExecutionException e) {
				logger.error("", e);
			}
		}
		return transferAccounts;
	}
}

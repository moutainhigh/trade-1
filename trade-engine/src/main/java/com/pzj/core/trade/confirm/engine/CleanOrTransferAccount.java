/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.engine;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.ConfirmVersionEnum;
import com.pzj.core.trade.confirm.event.TransferAccountsEvent;
import com.pzj.core.trade.confirm.model.AccountHandleModel;
import com.pzj.core.trade.context.common.VoucherSpeedTypeEnum;
import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.core.trade.context.model.ConsumerModel;

/**
 * 核销完毕，后续处理
 * <li>version 0:进行原有的清算操作</li>
 * <li>version 1:进行账号的分账操作</li>
 *
 * @author DongChunfu
 * @version $Id: CleanOrTransferAccount.java, v 0.1 2017年4月24日 上午10:41:12 DongChunfu Exp $
 */
@Component(value = "cleanOrTransferAccount")
public class CleanOrTransferAccount {

	private static final Logger logger = LoggerFactory.getLogger(CleanOrTransferAccount.class);

	@Autowired
	private TradePublisherFactory tradePublisherFactory;

	@Resource(name = "transferAccountsEvent")
	private TransferAccountsEvent transferAccountsEvent;

	public void handle(final AccountHandleModel reqModel) {
		CleanOrTransferAccount.logger.info("async clean or transfer accout request,reqModel:{}.", reqModel);
		final int tradeVersion = reqModel.getTradeVersion();

		CleanOrTransferAccount.logger.info("async clean handle,reqModel:{}.", reqModel);
		confirmForCleaning(reqModel);

		if (tradeVersion == ConfirmVersionEnum.NEW.getVersion()) {// 分账操作
			transferAccountsEvent.transferAccounts(reqModel.getTransactionId());
			CleanOrTransferAccount.logger.info("async transfer account success,reqModel:{}.", reqModel);
		}
	}

	/**
	 * 核销成功后，发布核销任务
	 *
	 * @author DongChunfu
	 * @param reqModel 核销请求参数
	 */
	private void confirmForCleaning(final AccountHandleModel reqModel) {
		final ConsumerModel model = new ConsumerModel();
		model.setVoucherId(reqModel.getVoucherId());
		model.setSaleOrderId(reqModel.getTransactionId());
		model.setConsumerSceneType(VoucherSpeedTypeEnum.SpeedSuccess.getKey());
		tradePublisherFactory.publish(model);
	}
}

package com.pzj.core.trade.integration.settlement.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.integration.engine.AfterRebateTransferredEngine;
import com.pzj.core.trade.integration.engine.exception.TransferredBaseException;
import com.pzj.core.trade.integration.settlement.service.SettlementMQConsumeCallbackService;
import com.pzj.framework.toolkit.Check;

@Service("afterRebateTransferredCallbackService")
public class AfterRebateTransferredCallbackServiceImpl implements SettlementMQConsumeCallbackService {

	private final static Logger logger = LoggerFactory.getLogger(AfterRebateTransferredCallbackServiceImpl.class);

	@Autowired
	private AfterRebateTransferredEngine afterRebateTransferredEngine;

	@Override
	public void doCallback(String mqContent) {
		if (Check.NuNString(mqContent)) {
			return;
		}
		try {
			afterRebateTransferredEngine.doEngine(mqContent);
		} catch (RuntimeException e) {
			logger.error("获取后返消息后,执行后续处理异常,消息体：[" + mqContent + "],error:", e);
			if (!(e instanceof TransferredBaseException)) {
				throw e;
			}
		}
	}
}

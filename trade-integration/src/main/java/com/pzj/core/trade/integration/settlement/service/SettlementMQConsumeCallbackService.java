package com.pzj.core.trade.integration.settlement.service;

/**
 * settlement服务MQ消息接受后回调处理service
 * @author kangzl
 *
 */
public interface SettlementMQConsumeCallbackService {
	void doCallback(final String mqContent);
}

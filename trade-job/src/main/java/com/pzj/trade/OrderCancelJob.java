package com.pzj.trade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.service.CancelService;

/**
 * 定时任务
 *
 */
@Component(value = "cancelOrderJob")
public class OrderCancelJob {
	private final Logger logger = LoggerFactory.getLogger(OrderCancelJob.class);

	@Autowired
	private CancelService cancelService;

	/**
	 * 取消订单,5分钟执行一次
	 */
	@Scheduled(cron = "0 * * * * *")
	public void cancelOrder() {
		logger.info("执行订单取消任务");
		Result<Boolean> result = cancelService.batchCancel(null);
		logger.info("执行支付取消任务结束,结果：" + JSONConverter.toJson(result));
	}

}

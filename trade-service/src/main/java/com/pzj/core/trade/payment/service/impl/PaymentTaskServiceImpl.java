package com.pzj.core.trade.payment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.payment.engine.PaymentCancelEngine;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.OrderForPaymentReadMapper;
import com.pzj.trade.payment.service.PaymentTaskService;

@Component("paymentTaskService")
public class PaymentTaskServiceImpl implements PaymentTaskService {
	private final static Logger logger = LoggerFactory.getLogger(PaymentTaskServiceImpl.class);

	@Autowired
	private OrderForPaymentReadMapper orderForPaymentReadMapper;

	@Autowired
	private PaymentCancelEngine paymentCancelEngine;

	private final int totalCacheNum = 1000;

	private final int poolSize = 10;

	@Override
	public Result<Boolean> batchCancelPayment(ServiceContext context) {
		// 执行支付取消业务
		Date cancelTime = new Date();
		List<OrderEntity> orders = new ArrayList<OrderEntity>();
		queryCancelOrders(orders, cancelTime, totalCacheNum, null);
		if (Check.NuNCollections(orders)) {
			return new Result<Boolean>();
		}
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(poolSize);
		final int sizeNum = orders.size();
		final CountDownLatch latch = new CountDownLatch(sizeNum);
		for (int i = 0; i < sizeNum; i++) {
			cachedThreadPool.execute(new CancelPaymentExecute(paymentCancelEngine, orders.get(i).getTransaction_id(), latch));
		}
		try {
			latch.await();
			cachedThreadPool.shutdown();
		} catch (Throwable e) {
			logger.error("等待批量任务执行发生异常,异常信息:", e);
		}
		return new Result<Boolean>();
	}

	@Override
	public Result<Boolean> cancelPaymentSinge(String saleOrderId, ServiceContext context) {
		try {
			paymentCancelEngine.doEngine(saleOrderId);
		} catch (Throwable e) {
			logger.error("等待批量任务执行发生异常,异常信息:", e);
			if (e instanceof TradeException) {
				TradeException ex = (TradeException) e;
				return new Result<Boolean>(ex.getErrCode(), ex.getMessage());
			}
			return new Result<Boolean>(10920, "系统出现问题");
		}
		return new Result<Boolean>();
	}

	private void queryCancelOrders(List<OrderEntity> orders, final Date cancelTime, final int totalCacheNum, final Date lastDate) {
		List<OrderEntity> orderPart = orderForPaymentReadMapper.queryChildOrderNotPaymentForCance(cancelTime, lastDate);
		if (Check.NuNCollections(orderPart) || orders.size() > totalCacheNum) {
			return;
		}
		orders.addAll(orderPart);
		queryCancelOrders(orders, cancelTime, totalCacheNum, orderPart.get(orderPart.size() - 1).getCreate_time());
	}

	private static class CancelPaymentExecute implements Runnable {

		private PaymentCancelEngine paymentCancelEngine;

		private String transactionId;

		private CountDownLatch latch;

		public CancelPaymentExecute(PaymentCancelEngine paymentCancelEngine, String transactionId, CountDownLatch latch) {
			this.paymentCancelEngine = paymentCancelEngine;
			this.transactionId = transactionId;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				paymentCancelEngine.doEngine(transactionId);
			} catch (Exception e) {
				logger.error("等待批量任务执行发生异常,[" + transactionId + "]异常信息:", e);
			} finally {
				latch.countDown();
			}
		}
	}
}

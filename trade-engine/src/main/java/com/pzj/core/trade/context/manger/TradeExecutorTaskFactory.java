package com.pzj.core.trade.context.manger;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;
import com.pzj.core.trade.context.exe.base.TradeExecutor;

@Component("tradeExecutorTaskFactory")
@Scope("singleton")
public class TradeExecutorTaskFactory {
	private final static Logger logger = LoggerFactory.getLogger(TradeExecutorTaskFactory.class);

	@Autowired
	private TradePublisherFactory tradePublisherFactory;

	@Autowired
	private TradeExecutorCache tradeExecutorCache;

	private final int work_num = 5;
	private ExecutorService pool;

	public TradeExecutorTaskFactory() {

	}

	@PostConstruct
	private void intMethod() {
		pool = Executors.newFixedThreadPool(work_num);
		for (int i = 0; i < work_num; i++) {
			final TradeExecutorTask task = new TradeExecutorTask();
			task.setName("tradeExecutorTask-" + i);
			pool.execute(task);
		}
	}

	private class TradeExecutorTask extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					final ExecuteBaseModel paramModel = tradePublisherFactory.getExecutorModel();
					if (paramModel == null) {
						Thread.sleep(5000);
						continue;
					}
					final Map<Integer, TradeExecutor<ExecuteBaseModel>> cache = tradeExecutorCache.getCache();
					final TradeExecutor<ExecuteBaseModel> executor = cache.get(paramModel.getTask().getKey());
					executor.execute(paramModel);
				} catch (final Exception e) {
					logger.error("TradeExecutorTaskFactory.TradeExecutorTask,threadName:" + this.getName() + " error:", e);
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}

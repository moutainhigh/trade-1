package com.pzj.core.trade.context.manger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;

@Component("tradePublisherFactory")
@Scope("singleton")
public class TradePublisherFactory {

	public TradePublisherFactory() {
		queryCache = new ArrayList<BlockingQueue<ExecuteBaseModel>>(queryNum);
		for (int i = 0; i < queryNum; i++) {
			BlockingQueue<ExecuteBaseModel> query = new LinkedBlockingDeque<ExecuteBaseModel>();
			queryCache.add(query);
		}
	}

	private final int queryNum = 5;

	private final int poolSize = 200;

	private List<BlockingQueue<ExecuteBaseModel>> queryCache;

	public void publish(ExecuteBaseModel paramModel) {
		for (BlockingQueue<ExecuteBaseModel> query : queryCache) {
			if (query.size() < poolSize) {
				query.offer(paramModel);
				break;
			}
		}
	}

	protected ExecuteBaseModel getExecutorModel() {
		ExecuteBaseModel model = null;
		synchronized (queryCache) {
			for (BlockingQueue<ExecuteBaseModel> query : queryCache) {
				if (query.isEmpty()) {
					continue;
				}
				model = query.poll();
				break;
			}
		}
		return model;
	}
}

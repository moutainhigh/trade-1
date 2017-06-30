package com.pzj.core.trade.context.manger;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.common.TradeTaskEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;
import com.pzj.core.trade.context.exe.base.TradeExecutor;

@Component("tradeExecutorCache")
@Scope("singleton")
public class TradeExecutorCache implements ApplicationContextAware {

	private Map<Integer, TradeExecutor<ExecuteBaseModel>> cache = new HashMap<Integer, TradeExecutor<ExecuteBaseModel>>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (cache.isEmpty()) {
			cache = new HashMap<Integer, TradeExecutor<ExecuteBaseModel>>();
			for (TradeTaskEnum taskEnum : TradeTaskEnum.values()) {
				TradeExecutor<ExecuteBaseModel> service = applicationContext.getBean(taskEnum.getTargetClass());
				cache.put(taskEnum.getKey(), service);
			}
		}
	}

	protected Map<Integer, TradeExecutor<ExecuteBaseModel>> getCache() {
		return cache;
	}
}

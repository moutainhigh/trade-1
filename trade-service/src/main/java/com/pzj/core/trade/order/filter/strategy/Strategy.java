package com.pzj.core.trade.order.filter.strategy;

import com.pzj.core.trade.order.engine.model.StrategyModel;

class Strategy {

	StrategyModel resellerStrategy;
	StrategyModel supplierStrategy;

	public Strategy(StrategyModel resellerStrategy, StrategyModel supplierStrategy) {
		this.resellerStrategy = resellerStrategy;
		this.supplierStrategy = supplierStrategy;
	}

	public StrategyModel getResellerStrategy() {
		return resellerStrategy;
	}

	public StrategyModel getSupplierStrategy() {
		return supplierStrategy;
	}
}

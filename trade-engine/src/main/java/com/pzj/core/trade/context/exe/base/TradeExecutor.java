package com.pzj.core.trade.context.exe.base;

@SuppressWarnings("hiding")
public interface TradeExecutor<ExecuteBaseModel> {
	void execute(ExecuteBaseModel paramModel);
}

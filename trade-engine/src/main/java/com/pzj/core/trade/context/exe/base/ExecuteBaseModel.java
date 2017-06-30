package com.pzj.core.trade.context.exe.base;

import java.io.Serializable;

import com.pzj.core.trade.context.common.TradeTaskEnum;

public class ExecuteBaseModel implements Serializable {
	public ExecuteBaseModel() {
		this.setTask(TradeTaskEnum.orderCreated);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 706869224875066248L;
	private String saleOrderId;

	/**
	 * 下单成功之后 1,订单二次确认成功后 2,订单代下单成功后 3
	 */
	private int orderSceneType = 1;

	private TradeTaskEnum task;

	public TradeTaskEnum getTask() {
		return task;
	}

	protected void setTask(TradeTaskEnum task) {
		this.task = task;
	}

	public int getOrderSceneType() {
		return orderSceneType;
	}

	public void setOrderSceneType(int orderSceneType) {
		this.orderSceneType = orderSceneType;
	}

	public String getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
	}
}

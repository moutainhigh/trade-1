package com.pzj.core.trade.context.common;

public enum OrderSceneTypeEnum {
	OrderCreated(1, "下单成功"), AckSucess(2, "二次确认通过"), ackFailure(3, "二次确认拒绝");
	private OrderSceneTypeEnum(int key, String msg) {
		this.key = key;
		this.msg = msg;
	}

	private int key;
	private String msg;

	public int getKey() {
		return key;
	}

	public String getMsg() {
		return msg;
	}
}

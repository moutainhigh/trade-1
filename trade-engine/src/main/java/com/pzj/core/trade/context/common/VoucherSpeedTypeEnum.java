package com.pzj.core.trade.context.common;

public enum VoucherSpeedTypeEnum {

	SpeedSuccess(1, "核销成功"), SpeedFailure(2, "核销失败");
	private VoucherSpeedTypeEnum(int key, String msg) {
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

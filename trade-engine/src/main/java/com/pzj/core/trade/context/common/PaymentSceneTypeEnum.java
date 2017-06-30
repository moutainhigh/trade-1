package com.pzj.core.trade.context.common;

public enum PaymentSceneTypeEnum {
	PaymentApply(1, "退款申请"), PaymentSucess(2, "退款成功"), PaymentFailure(3, "退款决绝");
	private PaymentSceneTypeEnum(int key, String msg) {
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

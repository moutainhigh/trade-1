package com.pzj.core.trade.context.common;

public enum RefundSceneTypeEnum {
	RefundApply(1, "退款申请"), RefundSucess(2, "退款成功"), RefundFailure(3, "退款决绝");
	private RefundSceneTypeEnum(int key, String msg) {
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

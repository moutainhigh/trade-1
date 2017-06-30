package com.pzj.core.trade.refund.engine.common;

public enum IsPartyRefundEnum {
	totalRefund(0, "整单退款"), partRefund(1, "部分退款");
	private IsPartyRefundEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}

	private int key;
	private String value;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

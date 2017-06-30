package com.pzj.core.trade.refund.engine.common;

/**
 * 退款申请类型.
 * @author YRJ
 *
 */
public enum RefundRuleTypeEnum {
	NO_RULE(0, "未执行退款规则"), BEFORE(1, "时间点前退款规则"), AFTER(2, "时间点后退款规则"), ANYTIME(3, "任何时间都可退款");

	private int type;

	private String note;

	public int getType() {
		return type;
	}

	public String getNote() {
		return note;
	}

	private RefundRuleTypeEnum(int type, String note) {
		this.type = type;
		this.note = note;
	}
}

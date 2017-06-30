/**
 * 
 */
package com.pzj.core.trade.refund.engine.common;

/**
 * 退款流水状态
 * 
 * @author DongChunfu
 * @date 2016年12月21日
 */
public enum RefundFlowStateEnum {

	REFUNDING(1, "退款中"), SUCCESS(2, "成功"), FAIL(3, "失败");

	private int state;

	private String note;

	private RefundFlowStateEnum(final int state, final String note) {
		this.state = state;
		this.note = note;
	}

	public int getState() {
		return state;
	}

	public String getNote() {
		return note;
	}

}

package com.pzj.core.trade.refund.model;

/**
 * 退款申请查询模型.
 * @author YRJ
 *
 */
public class RefundApplyQueryModel {

	private String orderId;

	private int[] refundStates;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public int[] getRefundStates() {
		return refundStates;
	}

	public void setRefundStates(final int[] refundStates) {
		this.refundStates = refundStates;
	}
}

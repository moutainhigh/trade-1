package com.pzj.core.trade.refund.model;

public class RefundAuditSaleOrderModel {

	private String orderId;

	private int totalNum;

	private int totalRefundNum;

	private int totalRefundingNum;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalRefundNum() {
		return totalRefundNum;
	}

	public void setTotalRefundNum(int totalRefundNum) {
		this.totalRefundNum = totalRefundNum;
	}

	public int getTotalRefundingNum() {
		return totalRefundingNum;
	}

	public void setTotalRefundingNum(int totalRefundingNum) {
		this.totalRefundingNum = totalRefundingNum;
	}
}

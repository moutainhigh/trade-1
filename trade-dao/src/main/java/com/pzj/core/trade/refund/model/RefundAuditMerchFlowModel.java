package com.pzj.core.trade.refund.model;

public class RefundAuditMerchFlowModel {
	private String orderId;
	private String merchId;
	private String transactionId;
	private int isRefunding;
	private int applyNum;
	private int refundNum;
	private int checkNum;
	private int refundingNum;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getRefundingNum() {
		return refundingNum;
	}

	public void setRefundingNum(int refundingNum) {
		this.refundingNum = refundingNum;
	}
}

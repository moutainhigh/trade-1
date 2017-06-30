package com.pzj.trade.order.model;

public class OrderRefundModel {
	private String orderId;
	private String refundId;
	private int refundApplyNum;
	private int checkNum;
	private int refundingNum;
	private int orderStatus;
	private int totalNum;
	private int refundNum;
	private double refundAmount;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getRefundingNum() {
		return refundingNum;
	}

	public void setRefundingNum(int refundingNum) {
		this.refundingNum = refundingNum;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public int getRefundApplyNum() {
		return refundApplyNum;
	}

	public void setRefundApplyNum(int refundApplyNum) {
		this.refundApplyNum = refundApplyNum;
	}

	public boolean checkOrderFinish() {
		if (this.getTotalNum() == this.getCheckNum() + this.getRefundNum() && this.refundingNum == 0) {
			return true;
		}
		return false;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
}

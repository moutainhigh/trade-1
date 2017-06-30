package com.pzj.trade.order.entity;

/**
 * 订单退款更新模型.
 * @author YRJ
 *
 */
public class OrderRefundEditModel {

	/** 退款数量 */
	private String orderId;

	/** 退款数量 */
	private int refundNum;

	/** 已核销数量 */
	private int checkNum;

	/** 退款金额 */
	private double refundAmount;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(final int checkNum) {
		this.checkNum = checkNum;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(final double refundAmount) {
		this.refundAmount = refundAmount;
	}
}

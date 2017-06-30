package com.pzj.trade.order.entity;

/**
 * 订单数量.
 * @author YRJ
 *
 */
public class OrderNumEntity {

	/**
	 * 订单ID.
	 */
	private String orderId;

	/**
	 * 订单总数量.
	 */
	private int totalNum;

	/**
	 * 核销数量.
	 */
	private int checkNum;

	/**
	 * 退款数量.
	 */
	private int refundNum;

	public boolean isAllChecked(final int currentCheckNum) {
		return totalNum == refundNum + checkNum + currentCheckNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(final int checkNum) {
		this.checkNum = checkNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

}

package com.pzj.trade.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class WithdrawDetailResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易ID
	 */
	private String transactionId;

	/**
	 * 订单 状态
	 */
	private int orderStatus;

	/**
	 * 订单创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 订单完成时间
	 */
	private Timestamp confirmTime;
	/**
	 * 订单金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 订单数量
	 */
	private int totalNum;
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;
	/**
	 * 退款数量
	 */
	private int refundNum;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public int getRefundNum() {
		return refundNum;
	}
	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	
}

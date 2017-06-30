package com.pzj.trade.order.model;

import java.util.Date;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 清结算订单商品明细列表请求参数.
 * @author GLG 
 *
 */
public class SettlementOrdersReqModel extends PageableRequestBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单号ID.
	 */
	private String orderId;
	/**
	 * transactionId.
	 */
	private String transactionId;

	/**
	 * 支付起始时间.
	 */
	private Date payStartTime;

	/**
	 * 支付结束时间.
	 */
	private Date payEndTime;

	/**
	 * 核销起始时间.
	 */
	private Date checkStartTime;

	/**
	 * 核销结束时间.
	 */
	private Date checkEndTime;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public Date getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(final Date payStartTime) {
		this.payStartTime = payStartTime;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(final Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	public Date getCheckStartTime() {
		return checkStartTime;
	}

	public void setCheckStartTime(final Date checkStartTime) {
		this.checkStartTime = checkStartTime;
	}

	public Date getCheckEndTime() {
		return checkEndTime;
	}

	public void setCheckEndTime(final Date checkEndTime) {
		this.checkEndTime = checkEndTime;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettlementOrdersReqModel [orderId=");
		builder.append(orderId);
		builder.append(", payStartTime=");
		builder.append(payStartTime);
		builder.append(", payEndTime=");
		builder.append(payEndTime);
		builder.append(", checkStartTime=");
		builder.append(checkStartTime);
		builder.append(", checkEndTime=");
		builder.append(checkEndTime);
		builder.append("]");
		return builder.toString();
	}

}

package com.pzj.core.trade.query.entity;

import java.util.Date;
import java.util.List;

/**
 * 订单商品明细查询模型.
 * @author YRJ
 *
 */
public class OrderMerchDetailQueryModel {

	/**
	 * 订单号ID.
	 */
	private String orderId;

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

	private List<String> transaction_ids;

	private String transaction_id;

	public List<String> getTransaction_ids() {
		return transaction_ids;
	}

	public void setTransaction_ids(List<String> transaction_ids) {
		this.transaction_ids = transaction_ids;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
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
}

package com.pzj.trade.order.entity;

import java.sql.Timestamp;

/**
 * 订单商品明细响应实体.
 * @author YRJ
 *
 */
public class OrderMerchDetailEntity {

	/**
	 * 订单号ID.
	 */
	private String order_id;
	/**
	 * 事务ID.
	 */
	private String transaction_id;

	/**
	 * 订单状态.
	 */
	private int order_status;
	/**
	 * 订单创建时间.
	 */
	private Timestamp create_time;
	/**
	 * 订单支付时间.
	 */
	private Timestamp pay_time;
	/**
	 * 订单完成时间.
	 */
	private Timestamp confirm_time;
	/**
	 * 付款者ID.
	 */
	private long payer_id;
	/**
	 * 分销商ID.
	 */
	private long reseller_id;

	/**
	 * 版本.
	 */
	private int version;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(final String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(final int order_status) {
		this.order_status = order_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getPay_time() {
		return pay_time;
	}

	public void setPay_time(final Timestamp pay_time) {
		this.pay_time = pay_time;
	}

	public Timestamp getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(final Timestamp confirm_time) {
		this.confirm_time = confirm_time;
	}

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(final long payer_id) {
		this.payer_id = payer_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(final long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
}

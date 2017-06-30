package com.pzj.trade.confirm.entity;

import java.util.Date;

/**
 * 核销码, 魔方码.
 * @author YRJ
 *
 */
public class ConfirmCodeEntity {
	/**
	 * 主键ID.
	 */
	private long code_id;
	/**
	 * 核销码, 魔方码.
	 */
	private String mf_code;

	/**
	 * 交易ID.
	 */
	private String transaction_id;
	/**
	 * 订单ID.
	 */
	private String order_id;
	/**
	 * 商品ID.
	 */
	private String merch_id;
	/**
	 * 供应商ID.
	 */
	private long supplier_id;
	/**
	 * 码状态.
	 */
	private int code_state;
	/**
	 * 来源.
	 */
	private int source;

	private Date create_time;

	private Date checkTime;

	public long getCode_id() {
		return code_id;
	}

	public void setCode_id(final long code_id) {
		this.code_id = code_id;
	}

	public String getMf_code() {
		return mf_code;
	}

	public void setMf_code(final String mf_code) {
		this.mf_code = mf_code;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(final String merch_id) {
		this.merch_id = merch_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(final long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getCode_state() {
		return code_state;
	}

	public void setCode_state(final int code_state) {
		this.code_state = code_state;
	}

	public int getSource() {
		return source;
	}

	public void setSource(final int source) {
		this.source = source;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * Getter method for property <tt>transaction_id</tt>.
	 *
	 * @return property value of transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * Setter method for property <tt>transaction_id</tt>.
	 *
	 * @param transaction_id value to be assigned to property transaction_id
	 */
	public void setTransaction_id(final String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(final Date checkTime) {
		this.checkTime = checkTime;
	}

}

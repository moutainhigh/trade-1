/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.merch.entity;

import java.io.Serializable;

/**
 * 批量核销封装参数
 *
 * @author DongChunfu
 * @version $Id: ConfirmBatchEntity.java, v 0.1 2017年2月28日 上午9:46:11 DongChunfu Exp $
 */
public class ConfirmBatchEntity implements Serializable {

	private static final long serialVersionUID = -2423259671920702339L;

	private String order_id;

	private Long voucher_id;

	private String merch_id;

	private String merch_name;

	public ConfirmBatchEntity() {
		super();
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public Long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(final Long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(final String merch_id) {
		this.merch_id = merch_id;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(final String merch_name) {
		this.merch_name = merch_name;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ConfirmBatchEntity [order_id=");
		builder.append(order_id);
		builder.append(", voucher_id=");
		builder.append(voucher_id);
		builder.append(", merch_id=");
		builder.append(merch_id);
		builder.append(", merch_name=");
		builder.append(merch_name);
		builder.append("]");
		return builder.toString();
	}

}

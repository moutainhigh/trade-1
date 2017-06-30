/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 结算商品信息
 *
 * @author DongChunfu
 * @version $Id: SettleMerchEntity.java, v 0.1 2017年5月18日 上午11:34:46 DongChunfu Exp $
 */
public class SettleMerchEntity implements Serializable {
	private static final long serialVersionUID = -6167988718234120481L;

	private String transactionId;
	private String orderId;
	private String merchId;

	public SettleMerchEntity() {
		super();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleMerchEntity [transactionId=");
		builder.append(transactionId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", merchId=");
		builder.append(merchId);
		builder.append("]");
		return builder.toString();
	}

}

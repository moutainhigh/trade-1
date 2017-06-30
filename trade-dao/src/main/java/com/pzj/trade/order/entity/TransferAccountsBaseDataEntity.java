/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 分账辅助数据
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsBaseDataEntity.java, v 0.1 2017年4月10日 下午2:34:52 DongChunfu Exp $
 */
public class TransferAccountsBaseDataEntity implements Serializable {
	private static final long serialVersionUID = 6688360440239209092L;

	/**交易ID*/
	private String transactionId;
	/**退款ID*/
	private String refundId;

	public TransferAccountsBaseDataEntity() {
		super();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TransferAccountsBaseDataEntity [transactionId=");
		builder.append(transactionId);
		builder.append(", refundId=");
		builder.append(refundId);
		builder.append("]");
		return builder.toString();
	}

}

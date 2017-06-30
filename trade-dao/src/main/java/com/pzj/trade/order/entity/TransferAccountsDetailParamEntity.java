/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 分账查询明细请求参数
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsDetailParamEntity.java, v 0.1 2017年3月25日 下午1:37:32 DongChunfu Exp $
 */
public class TransferAccountsDetailParamEntity implements Serializable {
	private static final long serialVersionUID = 6688360440239209092L;

	/**订单ID*/
	private String transactionId;
	/***********日期格式：YYYY-MM-DD***********/
	/**订单支付日期自*/
	private Date payStartTime;
	/**订单支付日期至*/
	private Date payEndTime;
	/**订单核销日期自*/
	private Date confirmStartTime;
	/**订单核销日期至*/
	private Date confirmEndTime;

	/**是否分页，默认分页为<code>true</code>*/
	private boolean pageable = true;
	/**条数限制*/
	private int limit;
	/**偏移量*/
	private int offSet;

	public TransferAccountsDetailParamEntity() {
		super();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
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

	public Date getConfirmStartTime() {
		return confirmStartTime;
	}

	public void setConfirmStartTime(final Date confirmStartTime) {
		this.confirmStartTime = confirmStartTime;
	}

	public Date getConfirmEndTime() {
		return confirmEndTime;
	}

	public void setConfirmEndTime(final Date confirmEndTime) {
		this.confirmEndTime = confirmEndTime;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(final boolean pageable) {
		this.pageable = pageable;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(final int limit) {
		this.limit = limit;
	}

	public int getOffSet() {
		return offSet;
	}

	public void setOffSet(final int offSet) {
		this.offSet = offSet;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TransferAccountsDetailParamEntity [transactionId=");
		builder.append(transactionId);
		builder.append(", payStartTime=");
		builder.append(payStartTime);
		builder.append(", payEndTime=");
		builder.append(payEndTime);
		builder.append(", confirmStartTime=");
		builder.append(confirmStartTime);
		builder.append(", confirmEndTime=");
		builder.append(confirmEndTime);
		builder.append(", pageable=");
		builder.append(pageable);
		builder.append(", limit=");
		builder.append(limit);
		builder.append(", offSet=");
		builder.append(offSet);
		builder.append("]");
		return builder.toString();
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 分账明细表查询请求参数
 *
 * @author DongChunfu
 * @version $Id: TransferAccountsReqModel.java, v 0.1 2017年3月25日 下午12:38:14 DongChunfu Exp $
 */
public class TransferAccountsReqModel extends PageableRequestBean implements Serializable {
	private static final long serialVersionUID = -6688786164304536821L;

	/**主订单ID*/
	private String transactionId;
	/**订单支付日期自*/
	private Date payStartTime;
	/**订单支付日期至*/
	private Date payEndTime;
	/**订单核销日期自*/
	private Date confirmStartTime;
	/**订单核销日期至*/
	private Date confirmEndTime;

	public TransferAccountsReqModel() {
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TransferAccountsReqModel [transactionId=");
		builder.append(transactionId);
		builder.append(", payStartTime=");
		builder.append(payStartTime);
		builder.append(", payEndTime=");
		builder.append(payEndTime);
		builder.append(", confirmStartTime=");
		builder.append(confirmStartTime);
		builder.append(", confirmEndTime=");
		builder.append(confirmEndTime);
		builder.append("]");
		return builder.toString();
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.model;

import java.util.Date;

/**
 * 清算分账操作模型
 *
 * @author DongChunfu
 * @version $Id: AccountHandleModel.java, v 0.1 2017年5月9日 上午10:09:42 DongChunfu Exp $
 */
public class AccountHandleModel {

	/**交易ID*/
	private String transactionId;
	/**凭证ID*/
	private long voucherId;
	/**交易版本*/
	private int tradeVersion;
	/**核销数量*/
	private int checkedNum;
	/**实际检票时间*/
	private Date checkedTime;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final long voucherId) {
		this.voucherId = voucherId;
	}

	public int getTradeVersion() {
		return tradeVersion;
	}

	public void setTradeVersion(final int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(final int checkedNum) {
		this.checkedNum = checkedNum;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(final Date checkedTime) {
		this.checkedTime = checkedTime;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AccountHandleModel [transactionId=");
		builder.append(transactionId);
		builder.append(", voucherId=");
		builder.append(voucherId);
		builder.append(", tradeVersion=");
		builder.append(tradeVersion);
		builder.append(", checkedNum=");
		builder.append(checkedNum);
		builder.append(", checkedTime=");
		builder.append(checkedTime);
		builder.append("]");
		return builder.toString();
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 相对当前用户结算汇总信息
 *
 * @author DongChunfu
 * @version $Id: SettlePartyRepModel.java, v 0.1 2017年5月15日 下午1:56:12 DongChunfu Exp $
 */
public class SettlePartyRepModel implements Serializable {
	private static final long serialVersionUID = -1238915706285040160L;

	/**相对用户ID*/
	private Long relativeUserId;

	/**魔方结算收入（订单返利）*/
	private Double onlineReceipt;
	/**魔方结算支付（结算价）*/
	private Double onlinePay;
	/**线下结算收入（订单返利）*/
	private Double offlineReceipt;
	/**线下结算支付（结算价）*/
	private Double offlinePay;

	public SettlePartyRepModel() {
		super();
	}

	public Long getRelativeUserId() {
		return relativeUserId;
	}

	public void setRelativeUserId(final Long relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	public Double getOnlineReceipt() {
		return amountFormat(onlineReceipt);
	}

	public void setOnlineReceipt(final Double onlineReceipt) {
		this.onlineReceipt = onlineReceipt;
	}

	public Double getOnlinePay() {
		return amountFormat(onlinePay);
	}

	public void setOnlinePay(final Double onlinePay) {
		this.onlinePay = onlinePay;
	}

	public Double getOfflineReceipt() {
		return amountFormat(offlineReceipt);
	}

	public void setOfflineReceipt(final Double offlineReceipt) {
		this.offlineReceipt = offlineReceipt;
	}

	public Double getOfflinePay() {
		return amountFormat(offlinePay);
	}

	public void setOfflinePay(final Double offlinePay) {
		this.offlinePay = offlinePay;
	}

	private double amountFormat(double amount) {
		DecimalFormat df = new DecimalFormat("#.00");
		String formatAmount = df.format(amount);
		return Double.valueOf(formatAmount);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleGatherRepModel [relativeUserId=");
		builder.append(relativeUserId);
		builder.append(", onlineReceipt=");
		builder.append(onlineReceipt);
		builder.append(", onlinePay=");
		builder.append(onlinePay);
		builder.append(", offlineReceipt=");
		builder.append(offlineReceipt);
		builder.append(", offlinePay=");
		builder.append(offlinePay);
		builder.append("]");
		return builder.toString();
	}

}

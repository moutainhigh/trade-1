package com.pzj.trade.order.model;

import java.util.Date;

public class TransferredMerchStrategyModel {

	private String orderId;
	private String merchId;
	private Date transfeeredTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public Date getTransfeeredTime() {
		return transfeeredTime;
	}

	public void setTransfeeredTime(Date transfeeredTime) {
		this.transfeeredTime = transfeeredTime;
	}
}

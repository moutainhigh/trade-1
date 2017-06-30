package com.pzj.trade.order.model;

import java.io.Serializable;

public class ResellerOrderDetailReqModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单Id */
	private String order_id;

	/** 分销商ID */
	private long reseller_id;
	/**订单级别*/
	private int orderLevel;
	/**交易号，注：传递交易号时，reseller_id参数也必须传递否则会报错*/
	private String transactionId;

	public int getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(int orderLevel) {
		this.orderLevel = orderLevel;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

}

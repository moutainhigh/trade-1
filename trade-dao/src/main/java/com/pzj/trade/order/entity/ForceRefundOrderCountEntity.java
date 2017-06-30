package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 强制退款条数查询Entity
 * 
 * */
public class ForceRefundOrderCountEntity implements Serializable {

	/**  */
	private static final long serialVersionUID = 6297419087650343561L;
	/**订单id  */
	private String order_id;
	/**退款申请id  */
	private String refund_id;
	private String transaction_id;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

}

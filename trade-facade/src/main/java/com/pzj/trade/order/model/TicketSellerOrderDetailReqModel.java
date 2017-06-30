package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 订单详情 - 售票员 - 入參模型.
 * @author YRJ
 *
 */
public class TicketSellerOrderDetailReqModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 订单Id */
	private String orderId;

	/**
	 * 操作者ID, 售票员.
	 * 代表当前登录者.
	 * 
	 */
	private long operatorId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
}

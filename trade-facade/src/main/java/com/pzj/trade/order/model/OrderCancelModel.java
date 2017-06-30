package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 订单取消请求参数.
 * @author YRJ
 *
 */
public class OrderCancelModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**订单ID(必传)*/
	private String orderId;

	/**操作者id(非必传)*/
	private long operatorId;

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(final long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderCancelModel [orderId=" + orderId + ", operatorId=" + operatorId + "]";
	}

}

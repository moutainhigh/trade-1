package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 打印票接口请求参数.
 * @author YRJ
 *
 */
public class PrintOrderDetailReqModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID, 查找该订单的票据信息用于打印.
	 * <p>该订单为主订单ID.</p>
	 */
	private String orderId;

	/** 操作者Id */
	private String operatorId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrintOrderDetailReqModel [orderId=");
		builder.append(orderId);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append("]");
		return builder.toString();
	}

}

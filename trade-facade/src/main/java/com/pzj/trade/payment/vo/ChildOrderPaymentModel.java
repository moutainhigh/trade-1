package com.pzj.trade.payment.vo;

import java.io.Serializable;

/**
 * 子订单付款参数模型.
 * @author YRJ
 *
 */
public class ChildOrderPaymentModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 待付款的子订单ID.
	 */
	private String orderId;

	/**
	 * 当前订单的所属分销商ID.
	 */
	private long resellerId;

	/**
	 * 是否进行补差通过
	 */
	private boolean isMakeUp = true;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(final long resellerId) {
		this.resellerId = resellerId;
	}

	public boolean isMakeUp() {
		return isMakeUp;
	}

	public void setMakeUp(boolean isMakeUp) {
		this.isMakeUp = isMakeUp;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ChildOrderPaymentModel [orderId=");
		builder.append(orderId);
		builder.append(", resellerId=");
		builder.append(resellerId);
		builder.append(", isMakeUp=");
		builder.append(isMakeUp);
		builder.append("]");
		return builder.toString();
	}

}

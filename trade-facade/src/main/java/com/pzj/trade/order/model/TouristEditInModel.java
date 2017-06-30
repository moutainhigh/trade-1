package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 游客编辑入參.
 * @author YRJ
 *
 */
public class TouristEditInModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID.
	 */
	private String orderId;

	/**
	 * 游客ID.
	 */
	private long touristId;

	/**
	 * 游客姓名.
	 */
	private String name;
	/**
	 * 游客手机号.
	 */
	private String mobile;

	/**
	 * 身份证.
	 */
	private String cardId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public long getTouristId() {
		return touristId;
	}

	public void setTouristId(final long touristId) {
		this.touristId = touristId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(final String cardId) {
		this.cardId = cardId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TouristEditInModel [orderId=");
		builder.append(orderId);
		builder.append(", touristId=");
		builder.append(touristId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", cardId=");
		builder.append(cardId);
		builder.append("]");
		return builder.toString();
	}

}

package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 批量核销响应结果
 *
 * @author DongChunfu
 * @version $Id: ConfirmFailValue.java, v 0.1 2017年2月27日 下午6:49:50 DongChunfu Exp $
 */
public class ConfirmFailValue implements Serializable, Cloneable {

	private static final long serialVersionUID = -6288163859893080859L;
	/**订单ID*/
	private String orderId;
	/**凭证ID*/
	private Long voucherId;
	/**商品ID*/
	private String merchId;
	/**商品名称*/
	private String merchName;
	/**失败原因*/
	private String failMsg;

	public ConfirmFailValue() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final Long voucherId) {
		this.voucherId = voucherId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(final String merchName) {
		this.merchName = merchName;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(final String failMsg) {
		this.failMsg = failMsg;
	}

	@Override
	public ConfirmFailValue clone() throws CloneNotSupportedException {
		return (ConfirmFailValue) super.clone();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ConfirmFailValue [orderId=");
		builder.append(orderId);
		builder.append(", voucherId=");
		builder.append(voucherId);
		builder.append(", merchId=");
		builder.append(merchId);
		builder.append(", merchName=");
		builder.append(merchName);
		builder.append(", failMsg=");
		builder.append(failMsg);
		builder.append("]");
		return builder.toString();
	}

}

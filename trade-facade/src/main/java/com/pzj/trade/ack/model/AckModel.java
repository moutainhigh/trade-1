package com.pzj.trade.ack.model;

import java.io.Serializable;

/**
 * 二次确认请求参数.
 * @author YRJ
 *
 */
public class AckModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单号.
	 */
	private String orderId;

	/**
	 * 发起人ID,默认为999
	 */
	private long applyUserId;

	/**
	 * 确认还是拒绝.
	 * true: 确认;
	 * false：拒绝.
	 */
	private boolean acknowledge;

	/**
	 * 第三方订单号、辅助码. 仅用于对接使用, 平台不使用.
	 */
	private String thirdCode;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean getAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

	public String getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}

	@Override
	public String toString() {
		StringBuilder tostr = new StringBuilder();
		tostr.append("[orderId=").append(orderId);
		tostr.append(", acknowledge=").append(acknowledge);
		tostr.append(", thirdCode=").append(thirdCode);
		tostr.append("]");
		return tostr.toString();
	}

	public long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}
}

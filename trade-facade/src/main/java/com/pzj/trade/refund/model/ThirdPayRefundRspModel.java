package com.pzj.trade.refund.model;

import java.io.Serializable;

/**
 * 退款回调的返回值参数
 * @author Administrator
 *
 */
public class ThirdPayRefundRspModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1293566656583223937L;

	/**
	 * 订单ID（非空）
	 */
	private String orderId;

	/**
	 * 退款申请ID（非空）
	 */
	private String refundId;
	/**
	 * 退款类型
	 *
	 * <li>0,普通退款</li>
	 * <li>1,强制退款</li>
	 */
	private Integer refundType;

	/**
	 * 退款是否成功的标识位（非空）
	 */
	private Boolean rspResult;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(final Integer refundType) {
		this.refundType = refundType;
	}

	public Boolean getRspResult() {
		return rspResult;
	}

	public void setRspResult(final Boolean rspResult) {
		this.rspResult = rspResult;
	}

}

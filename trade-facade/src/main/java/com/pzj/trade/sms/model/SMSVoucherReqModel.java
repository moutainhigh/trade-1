package com.pzj.trade.sms.model;

import java.io.Serializable;

/**
 * 发送凭证短信的请求产生Model
 * @author kangzl
 */
public class SMSVoucherReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6152390443817358626L;
	/**
	 * 销售订单ID(必传)
	 */
	private String resellerOrderId;

	public String getResellerOrderId() {
		return resellerOrderId;
	}

	public void setResellerOrderId(String resellerOrderId) {
		this.resellerOrderId = resellerOrderId;
	}

}

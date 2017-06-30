/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 批量核销封装参数
 *
 * @author DongChunfu
 * @version $Id: BatchConfirmWrapModel.java, v 0.1 2017年2月27日 下午7:01:43 DongChunfu Exp $
 */
public class BatchConfirmWrapModel implements Serializable, Cloneable {

	private static final long serialVersionUID = 6146976293618774199L;

	/**订单ID*/
	private String orderId;
	/**凭证ID*/
	private Long voucherId;
	/**商品ID*/
	private String merchId;
	/**商品名称*/
	private String merchName;

	public BatchConfirmWrapModel() {
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}

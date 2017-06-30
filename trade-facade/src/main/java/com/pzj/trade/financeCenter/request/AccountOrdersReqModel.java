/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.request;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author GLG
 * @version $Id: AccountOrdersReqModel.java, v 0.1 2017年3月10日 下午6:17:19 Administrator Exp $
 * @category
 */
public class AccountOrdersReqModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -8075589724491433414L;
	/** 供应商Id*/
	private long supplierId;
	/**交易id集合*/
	private List<String> transactionIds;

	/**订单id集合, 传入此参数查询的是主销售订单集合*/
	private List<String> orderId;

	public List<String> getOrderId() {
		return orderId;
	}

	public void setOrderId(List<String> orderId) {
		this.orderId = orderId;
	}

	public List<String> getTransactionIds() {
		return transactionIds;
	}

	public void setTransactionIds(List<String> transactionIds) {
		this.transactionIds = transactionIds;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

}

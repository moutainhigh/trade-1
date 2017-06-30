/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.query.entity;

import java.util.List;

/**
 * 
 * @author GLG
 * @version $Id: AccountOrdersOrdersQueryModel.java, v 0.1 2017年3月14日 下午5:30:10 Administrator Exp $
 */
public class AccountOrdersOrdersQueryModel {

	/** 供应商Id*/
	private long supplier_id;

	private List<String> transaction_ids;

	private List<String> order_ids;

	public List<String> getOrder_ids() {
		return order_ids;
	}

	public void setOrder_ids(List<String> order_ids) {
		this.order_ids = order_ids;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public List<String> getTransaction_ids() {
		return transaction_ids;
	}

	public void setTransaction_ids(List<String> transaction_ids) {
		this.transaction_ids = transaction_ids;
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.query.entity;

import java.util.List;

/**
 * app账户余额订单列表查询模型.
 * @author GLG
 * @version $Id: AppRebateOrdersOrdersQueryModel.java, v 0.1 2017年3月14日 下午5:30:10 Administrator Exp $
 */
public class AppOrdersOrdersQueryModel {

	/** 分销商Id*/
	private long reseller_id;

	private List<String> orderIds;

	/** 产品名称*/
	private String sup_name;

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

}

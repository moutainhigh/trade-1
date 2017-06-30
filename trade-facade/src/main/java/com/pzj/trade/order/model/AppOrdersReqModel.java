/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author GLG
 * @version $Id: AppOrderOrdersReqModel.java, v 0.1 2017年3月10日 下午6:17:19 Administrator Exp $
 */
public class AppOrdersReqModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -8075589724491433414L;
	/** 分销商Id*/
	private long reseller_id;
	/**订单id集合*/
	private List<String> orderIds;
	/** 产品名称*/
	private String supName;

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
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

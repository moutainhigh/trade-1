/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Administrator
 * @version $Id: OrderFillModel.java, v 0.1 2017年2月17日 下午3:59:35 Administrator Exp $
 */
public class OrderFillModel implements Serializable {
	/**  */
	private static final long serialVersionUID = -8463038797203167252L;
	/**
	 * 订单ID
	 */
	private String order_id;
	/**
	 * 填单项列表
	 */
	private List<FilledModel> filleds;

	/**
	 * Getter method for property <tt>order_id</tt>.
	 * 
	 * @return property value of order_id
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * Setter method for property <tt>order_id</tt>.
	 * 
	 * @param order_id value to be assigned to property order_id
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * Getter method for property <tt>filleds</tt>.
	 * 
	 * @return property value of filleds
	 */
	public List<FilledModel> getFilleds() {
		return filleds;
	}

	/**
	 * Setter method for property <tt>filleds</tt>.
	 * 
	 * @param filleds value to be assigned to property filleds
	 */
	public void setFilleds(List<FilledModel> filleds) {
		this.filleds = filleds;
	}

}

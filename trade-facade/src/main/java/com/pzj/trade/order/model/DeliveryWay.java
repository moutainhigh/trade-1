/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.model;

/**
 * 货物发送的配送方式
 * @author fanggang
 * @version $Id: DeliveryWay.java, v 0.1 2016年11月1日 上午11:00:54 fanggang Exp $
 */
public enum DeliveryWay {
	PICKUP_BY_SELF(1, "上门自提"), EXPRESS_SERVICE(2, "快递配送"), NULL_SEND_TYPE(0, "没有相关配送短信");

	private Integer key;
	private String desc;

	private DeliveryWay(final Integer key, final String desc) {
		this.key = key;
		this.desc = desc;
	}

	/**
	 * Getter method for property <tt>key</tt>.
	 *
	 * @return property value of key
	 */
	public Integer getKey() {
		return key;
	}

	/**
	 * Getter method for property <tt>desc</tt>.
	 *
	 * @return property value of desc
	 */
	public String getDesc() {
		return desc;
	}

}

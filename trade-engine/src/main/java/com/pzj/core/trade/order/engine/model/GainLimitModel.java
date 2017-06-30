/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.model;

/**
 * 联系人单人购买限制（防黄牛）
 * @author Administrator
 * @version $Id: GainLimitModel.java, v 0.1 2017年3月9日 下午4:09:47 Administrator Exp $
 */
public class GainLimitModel {
	private int num;//数量
	private int unit;//时间单位
	private int value;//时间值

	/**
	 * Getter method for property <tt>num</tt>.
	 * 
	 * @return property value of num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Setter method for property <tt>num</tt>.
	 * 
	 * @param num value to be assigned to property num
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * Getter method for property <tt>unit</tt>.
	 * 
	 * @return property value of unit
	 */
	public int getUnit() {
		return unit;
	}

	/**
	 * Setter method for property <tt>unit</tt>.
	 * 
	 * @param unit value to be assigned to property unit
	 */
	public void setUnit(int unit) {
		this.unit = unit;
	}

	/**
	 * Getter method for property <tt>value</tt>.
	 * 
	 * @return property value of value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter method for property <tt>value</tt>.
	 * 
	 * @param value value to be assigned to property value
	 */
	public void setValue(int value) {
		this.value = value;
	}

}

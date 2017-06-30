/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.model;

import java.util.Date;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExcelModel.java, v 0.1 2017年3月1日 上午11:51:24 Administrator Exp $
 */
public class SkuExcelModel {

	private int merch_state;

	private int num;

	private Date check_time;

	/**
	 * Getter method for property <tt>merch_state</tt>.
	 * 
	 * @return property value of merch_state
	 */
	public int getMerch_state() {
		return merch_state;
	}

	/**
	 * Setter method for property <tt>merch_state</tt>.
	 * 
	 * @param merch_state value to be assigned to property merch_state
	 */
	public void setMerch_state(int merch_state) {
		this.merch_state = merch_state;
	}

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
	 * Getter method for property <tt>check_time</tt>.
	 * 
	 * @return property value of check_time
	 */
	public Date getCheck_time() {
		return check_time;
	}

	/**
	 * Setter method for property <tt>check_time</tt>.
	 * 
	 * @param check_time value to be assigned to property check_time
	 */
	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

}

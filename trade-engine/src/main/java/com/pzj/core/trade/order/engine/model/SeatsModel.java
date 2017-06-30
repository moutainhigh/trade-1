/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Administrator
 * @version $Id: StockComputeModel.java, v 0.1 2017年3月20日 下午2:25:14 Administrator Exp $
 */
public class SeatsModel {
	/**需要占座列表 */
	private List<Long> needSeats = new ArrayList<Long>();
	/**需要释放座位列表 */
	private List<Long> releaseSeats = new ArrayList<Long>();

	/**
	 * Getter method for property <tt>needSeats</tt>.
	 * 
	 * @return property value of needSeats
	 */
	public List<Long> getNeedSeats() {
		return needSeats;
	}

	/**
	 * Setter method for property <tt>needSeats</tt>.
	 * 
	 * @param needSeats value to be assigned to property needSeats
	 */
	public void setNeedSeats(List<Long> needSeats) {
		this.needSeats = needSeats;
	}

	/**
	 * Getter method for property <tt>releaseSeats</tt>.
	 * 
	 * @return property value of releaseSeats
	 */
	public List<Long> getReleaseSeats() {
		return releaseSeats;
	}

	/**
	 * Setter method for property <tt>releaseSeats</tt>.
	 * 
	 * @param releaseSeats value to be assigned to property releaseSeats
	 */
	public void setReleaseSeats(List<Long> releaseSeats) {
		this.releaseSeats = releaseSeats;
	}

}

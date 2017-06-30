/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.model;

import java.io.Serializable;

/**
 *检票点模型
 * @author Administrator
 * @version $Id: StrategyModel.java, v 0.1 2016年12月5日 下午5:32:02 Administrator Exp $
 */
public class CheckinPointModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -5326036142308567776L;

	/** 景区ID*/
	private long sceneId;

	/** 景区名称*/
	private String sceneName;

	/** 供应商ID*/
	private long supplierId;

	/** 检票点*/
	private long checkinPointId;

	/** 最大使用时间*/
	private int maxUseTimes;

	/**
	 * Getter method for property <tt>sceneId</tt>.
	 * 
	 * @return property value of sceneId
	 */
	public long getSceneId() {
		return sceneId;
	}

	/**
	 * Setter method for property <tt>sceneId</tt>.
	 * 
	 * @param sceneId value to be assigned to property sceneId
	 */
	public void setSceneId(long sceneId) {
		this.sceneId = sceneId;
	}

	/**
	 * Getter method for property <tt>supplierId</tt>.
	 * 
	 * @return property value of supplierId
	 */
	public long getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for property <tt>supplierId</tt>.
	 * 
	 * @param supplierId value to be assigned to property supplierId
	 */
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Getter method for property <tt>checkinPointId</tt>.
	 * 
	 * @return property value of checkinPointId
	 */
	public long getCheckinPointId() {
		return checkinPointId;
	}

	/**
	 * Setter method for property <tt>checkinPointId</tt>.
	 * 
	 * @param checkinPointId value to be assigned to property checkinPointId
	 */
	public void setCheckinPointId(long checkinPointId) {
		this.checkinPointId = checkinPointId;
	}

	/**
	 * Getter method for property <tt>maxUseTimes</tt>.
	 * 
	 * @return property value of maxUseTimes
	 */
	public int getMaxUseTimes() {
		return maxUseTimes;
	}

	/**
	 * Setter method for property <tt>maxUseTimes</tt>.
	 * 
	 * @param maxUseTimes value to be assigned to property maxUseTimes
	 */
	public void setMaxUseTimes(int maxUseTimes) {
		this.maxUseTimes = maxUseTimes;
	}

	/**
	 * Getter method for property <tt>sceneName</tt>.
	 * 
	 * @return property value of sceneName
	 */
	public String getSceneName() {
		return sceneName;
	}

	/**
	 * Setter method for property <tt>sceneName</tt>.
	 * 
	 * @param sceneName value to be assigned to property sceneName
	 */
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

}
/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.util.Date;

/**
 * 
 * @author Administrator
 * @version $Id: TouristEntity.java, v 0.1 2017年3月14日 下午2:28:12 Administrator Exp $
 */
public class TouristEntity {
	/** */
	private long tourist_id;

	/**订单id*/
	private String order_id;

	/**商品id*/
	private String merch_id;

	/**姓名*/
	private String name;

	/**身份证*/
	private String idcard;

	/**手机号*/
	private String mobile;

	/**拼音*/
	private String name_spell;

	/**其他*/
	private String other;

	private Date create_time;

	/**
	 * Getter method for property <tt>tourist_id</tt>.
	 * 
	 * @return property value of tourist_id
	 */
	public long getTourist_id() {
		return tourist_id;
	}

	/**
	 * Setter method for property <tt>tourist_id</tt>.
	 * 
	 * @param tourist_id value to be assigned to property tourist_id
	 */
	public void setTourist_id(long tourist_id) {
		this.tourist_id = tourist_id;
	}

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
	 * Getter method for property <tt>merch_id</tt>.
	 * 
	 * @return property value of merch_id
	 */
	public String getMerch_id() {
		return merch_id;
	}

	/**
	 * Setter method for property <tt>merch_id</tt>.
	 * 
	 * @param merch_id value to be assigned to property merch_id
	 */
	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>idcard</tt>.
	 * 
	 * @return property value of idcard
	 */
	public String getIdcard() {
		return idcard;
	}

	/**
	 * Setter method for property <tt>idcard</tt>.
	 * 
	 * @param idcard value to be assigned to property idcard
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	/**
	 * Getter method for property <tt>mobile</tt>.
	 * 
	 * @return property value of mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Setter method for property <tt>mobile</tt>.
	 * 
	 * @param mobile value to be assigned to property mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Getter method for property <tt>name_spell</tt>.
	 * 
	 * @return property value of name_spell
	 */
	public String getName_spell() {
		return name_spell;
	}

	/**
	 * Setter method for property <tt>name_spell</tt>.
	 * 
	 * @param name_spell value to be assigned to property name_spell
	 */
	public void setName_spell(String name_spell) {
		this.name_spell = name_spell;
	}

	/**
	 * Getter method for property <tt>other</tt>.
	 * 
	 * @return property value of other
	 */
	public String getOther() {
		return other;
	}

	/**
	 * Setter method for property <tt>other</tt>.
	 * 
	 * @param other value to be assigned to property other
	 */
	public void setOther(String other) {
		this.other = other;
	}

	/**
	 * Getter method for property <tt>create_time</tt>.
	 * 
	 * @return property value of create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * Setter method for property <tt>create_time</tt>.
	 * 
	 * @param create_time value to be assigned to property create_time
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}

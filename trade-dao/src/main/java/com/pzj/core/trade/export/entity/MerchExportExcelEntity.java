/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.entity;

import java.util.Date;

/**
 * 
 * @author Administrator
 * @version $Id: MerchExportExcelEntity.java, v 0.1 2017年2月8日 下午4:12:36 Administrator Exp $
 */
public class MerchExportExcelEntity {
	private String sku_name;

	private int number;

	private int merch_state;

	private int merch_type;

	private Date check_time;

	private Date clear_time;

	private double sale_price;

	private double sale_after_rebate;

	private double purch_price;

	private double purch_after_rebate;

	/**
	 * Getter method for property <tt>sku_name</tt>.
	 * 
	 * @return property value of sku_name
	 */
	public String getSku_name() {
		return sku_name;
	}

	/**
	 * Setter method for property <tt>sku_name</tt>.
	 * 
	 * @param sku_name value to be assigned to property sku_name
	 */
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	/**
	 * Getter method for property <tt>number</tt>.
	 * 
	 * @return property value of number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Setter method for property <tt>number</tt>.
	 * 
	 * @param number value to be assigned to property number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

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
	 * Getter method for property <tt>merch_type</tt>.
	 * 
	 * @return property value of merch_type
	 */
	public int getMerch_type() {
		return merch_type;
	}

	/**
	 * Setter method for property <tt>merch_type</tt>.
	 * 
	 * @param merch_type value to be assigned to property merch_type
	 */
	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
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

	/**
	 * Getter method for property <tt>clear_time</tt>.
	 * 
	 * @return property value of clear_time
	 */
	public Date getClear_time() {
		return clear_time;
	}

	/**
	 * Setter method for property <tt>clear_time</tt>.
	 * 
	 * @param clear_time value to be assigned to property clear_time
	 */
	public void setClear_time(Date clear_time) {
		this.clear_time = clear_time;
	}

	/**
	 * Getter method for property <tt>sale_price</tt>.
	 * 
	 * @return property value of sale_price
	 */
	public double getSale_price() {
		return sale_price;
	}

	/**
	 * Setter method for property <tt>sale_price</tt>.
	 * 
	 * @param sale_price value to be assigned to property sale_price
	 */
	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}

	/**
	 * Getter method for property <tt>sale_after_rebate</tt>.
	 * 
	 * @return property value of sale_after_rebate
	 */
	public double getSale_after_rebate() {
		return sale_after_rebate;
	}

	/**
	 * Setter method for property <tt>sale_after_rebate</tt>.
	 * 
	 * @param sale_after_rebate value to be assigned to property sale_after_rebate
	 */
	public void setSale_after_rebate(double sale_after_rebate) {
		this.sale_after_rebate = sale_after_rebate;
	}

	/**
	 * Getter method for property <tt>purch_price</tt>.
	 * 
	 * @return property value of purch_price
	 */
	public double getPurch_price() {
		return purch_price;
	}

	/**
	 * Setter method for property <tt>purch_price</tt>.
	 * 
	 * @param purch_price value to be assigned to property purch_price
	 */
	public void setPurch_price(double purch_price) {
		this.purch_price = purch_price;
	}

	/**
	 * Getter method for property <tt>purch_after_rebate</tt>.
	 * 
	 * @return property value of purch_after_rebate
	 */
	public double getPurch_after_rebate() {
		return purch_after_rebate;
	}

	/**
	 * Setter method for property <tt>purch_after_rebate</tt>.
	 * 
	 * @param purch_after_rebate value to be assigned to property purch_after_rebate
	 */
	public void setPurch_after_rebate(double purch_after_rebate) {
		this.purch_after_rebate = purch_after_rebate;
	}

}

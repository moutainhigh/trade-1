/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExcelModel.java, v 0.1 2017年3月1日 上午11:51:24 Administrator Exp $
 */
public class MerchExcelModel {

	private String merch_name;

	private int merch_type;

	private String sku_name;

	private ArrayList<SkuExcelModel> skus = new ArrayList<SkuExcelModel>();

	private int total_num;

	private Date check_time;

	private String clean_time;

	private double sale_price;

	private double purch_price;

	private int overdue_num;

	private int overdue_cleaned_num;

	private double sale_after_rebate;

	private double purch_after_rebate;

	/**
	 * Getter method for property <tt>merch_name</tt>.
	 * 
	 * @return property value of merch_name
	 */
	public String getMerch_name() {
		return merch_name;
	}

	/**
	 * Setter method for property <tt>merch_name</tt>.
	 * 
	 * @param merch_name value to be assigned to property merch_name
	 */
	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
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
	 * Getter method for property <tt>total_num</tt>.
	 * 
	 * @return property value of total_num
	 */
	public int getTotal_num() {
		return total_num;
	}

	/**
	 * Setter method for property <tt>total_num</tt>.
	 * 
	 * @param total_num value to be assigned to property total_num
	 */
	public void setTotal_num(int total_num) {
		this.total_num = total_num;
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
	 * Getter method for property <tt>clean_time</tt>.
	 * 
	 * @return property value of clean_time
	 */
	public String getClean_time() {
		return clean_time;
	}

	/**
	 * Setter method for property <tt>clean_time</tt>.
	 * 
	 * @param clean_time value to be assigned to property clean_time
	 */
	public void setClean_time(String clean_time) {
		this.clean_time = clean_time;
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
	 * Getter method for property <tt>overdue_num</tt>.
	 * 
	 * @return property value of overdue_num
	 */
	public int getOverdue_num() {
		return overdue_num;
	}

	/**
	 * Setter method for property <tt>overdue_num</tt>.
	 * 
	 * @param overdue_num value to be assigned to property overdue_num
	 */
	public void setOverdue_num(int overdue_num) {
		this.overdue_num = overdue_num;
	}

	/**
	 * Getter method for property <tt>overdue_cleaned_num</tt>.
	 * 
	 * @return property value of overdue_cleaned_num
	 */
	public int getOverdue_cleaned_num() {
		return overdue_cleaned_num;
	}

	/**
	 * Setter method for property <tt>overdue_cleaned_num</tt>.
	 * 
	 * @param overdue_cleaned_num value to be assigned to property overdue_cleaned_num
	 */
	public void setOverdue_cleaned_num(int overdue_cleaned_num) {
		this.overdue_cleaned_num = overdue_cleaned_num;
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
	 * Getter method for property <tt>skus</tt>.
	 * 
	 * @return property value of skus
	 */
	public ArrayList<SkuExcelModel> getSkus() {
		return skus;
	}

	/**
	 * Setter method for property <tt>skus</tt>.
	 * 
	 * @param skus value to be assigned to property skus
	 */
	public void setSkus(ArrayList<SkuExcelModel> skus) {
		this.skus = skus;
	}

}

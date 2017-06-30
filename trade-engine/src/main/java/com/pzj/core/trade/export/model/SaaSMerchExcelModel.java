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
public class SaaSMerchExcelModel {

	private long product_id;

	private Date check_time;

	private String merch_name;

	private int merch_type;

	private String sku_name;
	//团散	
	private int product_varie;

	private int total_num;

	private int check_num;

	private int auto_check_num;

	private int refund_num;

	private int overdue_num;

	private double sale_price;

	private double sale_after_rebate;

	private double purch_price;

	private double purch_after_rebate;

	private long original_suppliler_id;

	private String original_suppliler_name;

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
	 * Getter method for property <tt>product_varie</tt>.
	 * 
	 * @return property value of product_varie
	 */
	public int getProduct_varie() {
		return product_varie;
	}

	/**
	 * Setter method for property <tt>product_varie</tt>.
	 * 
	 * @param product_varie value to be assigned to property product_varie
	 */
	public void setProduct_varie(int product_varie) {
		this.product_varie = product_varie;
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
	 * Getter method for property <tt>refund_num</tt>.
	 * 
	 * @return property value of refund_num
	 */
	public int getRefund_num() {
		return refund_num;
	}

	/**
	 * Setter method for property <tt>refund_num</tt>.
	 * 
	 * @param refund_num value to be assigned to property refund_num
	 */
	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
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
	 * Getter method for property <tt>check_num</tt>.
	 * 
	 * @return property value of check_num
	 */
	public int getCheck_num() {
		return check_num;
	}

	/**
	 * Setter method for property <tt>check_num</tt>.
	 * 
	 * @param check_num value to be assigned to property check_num
	 */
	public void setCheck_num(int check_num) {
		this.check_num = check_num;
	}

	/**
	 * Getter method for property <tt>auto_check_num</tt>.
	 * 
	 * @return property value of auto_check_num
	 */
	public int getAuto_check_num() {
		return auto_check_num;
	}

	/**
	 * Setter method for property <tt>auto_check_num</tt>.
	 * 
	 * @param auto_check_num value to be assigned to property auto_check_num
	 */
	public void setAuto_check_num(int auto_check_num) {
		this.auto_check_num = auto_check_num;
	}

	/**
	 * Getter method for property <tt>product_id</tt>.
	 * 
	 * @return property value of product_id
	 */
	public long getProduct_id() {
		return product_id;
	}

	/**
	 * Setter method for property <tt>product_id</tt>.
	 * 
	 * @param product_id value to be assigned to property product_id
	 */
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	/**
	 * Getter method for property <tt>original_suppliler_id</tt>.
	 * 
	 * @return property value of original_suppliler_id
	 */
	public long getOriginal_suppliler_id() {
		return original_suppliler_id;
	}

	/**
	 * Setter method for property <tt>original_suppliler_id</tt>.
	 * 
	 * @param original_suppliler_id value to be assigned to property original_suppliler_id
	 */
	public void setOriginal_suppliler_id(long original_suppliler_id) {
		this.original_suppliler_id = original_suppliler_id;
	}

	/**
	 * Getter method for property <tt>original_suppliler_name</tt>.
	 * 
	 * @return property value of original_suppliler_name
	 */
	public String getOriginal_suppliler_name() {
		return original_suppliler_name;
	}

	/**
	 * Setter method for property <tt>original_suppliler_name</tt>.
	 * 
	 * @param original_suppliler_name value to be assigned to property original_suppliler_name
	 */
	public void setOriginal_suppliler_name(String original_suppliler_name) {
		this.original_suppliler_name = original_suppliler_name;
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExcelModel.java, v 0.1 2017年3月1日 上午11:51:24 Administrator Exp $
 */
public class OrderExcelModel {

	private List<MerchExcelModel> merchs = new ArrayList<MerchExcelModel>();

	//销售订单号
	private String order_id;
	//订单状态
	private int order_status;
	//订单来源
	private int sale_port;
	//	取票人	
	private String contactee;

	//出游/入住时间
	private Date start_time;
	//下单时间
	private Date create_time;
	//	销售订单金额
	private double total_amount;
	//团散	
	private int product_varie;
	//	直签
	private int is_direct;
	//	分销商ID
	private long reseller_id;

	private String reseller_name;
	//供应商ID
	private long supplier_id;

	private String supplier_name;

	/**
	 * Getter method for property <tt>merchs</tt>.
	 * 
	 * @return property value of merchs
	 */
	public List<MerchExcelModel> getMerchs() {
		return merchs;
	}

	/**
	 * Setter method for property <tt>merchs</tt>.
	 * 
	 * @param merchs value to be assigned to property merchs
	 */
	public void setMerchs(List<MerchExcelModel> merchs) {
		this.merchs = merchs;
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
	 * Getter method for property <tt>order_status</tt>.
	 * 
	 * @return property value of order_status
	 */
	public int getOrder_status() {
		return order_status;
	}

	/**
	 * Setter method for property <tt>order_status</tt>.
	 * 
	 * @param order_status value to be assigned to property order_status
	 */
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	/**
	 * Getter method for property <tt>sale_port</tt>.
	 * 
	 * @return property value of sale_port
	 */
	public int getSale_port() {
		return sale_port;
	}

	/**
	 * Setter method for property <tt>sale_port</tt>.
	 * 
	 * @param sale_port value to be assigned to property sale_port
	 */
	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	/**
	 * Getter method for property <tt>contactee</tt>.
	 * 
	 * @return property value of contactee
	 */
	public String getContactee() {
		return contactee;
	}

	/**
	 * Setter method for property <tt>contactee</tt>.
	 * 
	 * @param contactee value to be assigned to property contactee
	 */
	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	/**
	 * Getter method for property <tt>start_time</tt>.
	 * 
	 * @return property value of start_time
	 */
	public Date getStart_time() {
		return start_time;
	}

	/**
	 * Setter method for property <tt>start_time</tt>.
	 * 
	 * @param start_time value to be assigned to property start_time
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
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

	/**
	 * Getter method for property <tt>total_amount</tt>.
	 * 
	 * @return property value of total_amount
	 */
	public double getTotal_amount() {
		return total_amount;
	}

	/**
	 * Setter method for property <tt>total_amount</tt>.
	 * 
	 * @param total_amount value to be assigned to property total_amount
	 */
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
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
	 * Getter method for property <tt>is_direct</tt>.
	 * 
	 * @return property value of is_direct
	 */
	public int getIs_direct() {
		return is_direct;
	}

	/**
	 * Setter method for property <tt>is_direct</tt>.
	 * 
	 * @param is_direct value to be assigned to property is_direct
	 */
	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	/**
	 * Getter method for property <tt>reseller_id</tt>.
	 * 
	 * @return property value of reseller_id
	 */
	public long getReseller_id() {
		return reseller_id;
	}

	/**
	 * Setter method for property <tt>reseller_id</tt>.
	 * 
	 * @param reseller_id value to be assigned to property reseller_id
	 */
	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	/**
	 * Getter method for property <tt>supplier_id</tt>.
	 * 
	 * @return property value of supplier_id
	 */
	public long getSupplier_id() {
		return supplier_id;
	}

	/**
	 * Setter method for property <tt>supplier_id</tt>.
	 * 
	 * @param supplier_id value to be assigned to property supplier_id
	 */
	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	/**
	 * Getter method for property <tt>reseller_name</tt>.
	 * 
	 * @return property value of reseller_name
	 */
	public String getReseller_name() {
		return reseller_name;
	}

	/**
	 * Setter method for property <tt>reseller_name</tt>.
	 * 
	 * @param reseller_name value to be assigned to property reseller_name
	 */
	public void setReseller_name(String reseller_name) {
		this.reseller_name = reseller_name;
	}

	/**
	 * Getter method for property <tt>supplier_name</tt>.
	 * 
	 * @return property value of supplier_name
	 */
	public String getSupplier_name() {
		return supplier_name;
	}

	/**
	 * Setter method for property <tt>supplier_name</tt>.
	 * 
	 * @param supplier_name value to be assigned to property supplier_name
	 */
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

}

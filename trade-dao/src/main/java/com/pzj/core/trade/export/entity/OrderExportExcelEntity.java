/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.entity;

import java.util.Date;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExportExcelEntity.java, v 0.1 2017年2月7日 下午5:58:02 Administrator Exp $
 */
public class OrderExportExcelEntity {
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

	private Date expire_time;
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
	//供应商ID
	private long supplier_id;

	private int merch_state;

	private long product_id;

	private String merch_id;

	private String merch_name;

	private String sku_name;

	private int merch_type;

	private int total_num;

	private int check_num;

	private Date check_time;

	private double sale_price;

	private long sale_after_price;

	private double purch_price;

	private long purch_after_price;

	private int is_refunding;

	private int refund_num;

	private int refunding_num;

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
	 * Getter method for property <tt>is_refunding</tt>.
	 * 
	 * @return property value of is_refunding
	 */
	public int getIs_refunding() {
		return is_refunding;
	}

	/**
	 * Setter method for property <tt>is_refunding</tt>.
	 * 
	 * @param is_refunding value to be assigned to property is_refunding
	 */
	public void setIs_refunding(int is_refunding) {
		this.is_refunding = is_refunding;
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
	 * Getter method for property <tt>sale_after_price</tt>.
	 * 
	 * @return property value of sale_after_price
	 */
	public long getSale_after_price() {
		return sale_after_price;
	}

	/**
	 * Setter method for property <tt>sale_after_price</tt>.
	 * 
	 * @param sale_after_price value to be assigned to property sale_after_price
	 */
	public void setSale_after_price(long sale_after_price) {
		this.sale_after_price = sale_after_price;
	}

	/**
	 * Getter method for property <tt>purch_after_price</tt>.
	 * 
	 * @return property value of purch_after_price
	 */
	public long getPurch_after_price() {
		return purch_after_price;
	}

	/**
	 * Setter method for property <tt>purch_after_price</tt>.
	 * 
	 * @param purch_after_price value to be assigned to property purch_after_price
	 */
	public void setPurch_after_price(long purch_after_price) {
		this.purch_after_price = purch_after_price;
	}

	/**
	 * Getter method for property <tt>refunding_num</tt>.
	 * 
	 * @return property value of refunding_num
	 */
	public int getRefunding_num() {
		return refunding_num;
	}

	/**
	 * Setter method for property <tt>refunding_num</tt>.
	 * 
	 * @param refunding_num value to be assigned to property refunding_num
	 */
	public void setRefunding_num(int refunding_num) {
		this.refunding_num = refunding_num;
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
	 * Getter method for property <tt>expire_time</tt>.
	 * 
	 * @return property value of expire_time
	 */
	public Date getExpire_time() {
		return expire_time;
	}

	/**
	 * Setter method for property <tt>expire_time</tt>.
	 * 
	 * @param expire_time value to be assigned to property expire_time
	 */
	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}

}

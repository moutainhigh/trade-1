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
public class SaaSOrderExcelModel {

	private List<SaaSMerchExcelModel> merchs = new ArrayList<SaaSMerchExcelModel>();

	private String order_id;

	private String transaction_id;
	//订单状态
	private int order_status;
	//下单时间
	private Date create_time;
	//出游/入住时间
	private Date start_time;

	//订单来源
	private int sale_port;

	//	分销商ID
	private long reseller_id;
	private String reseller_name;
	//供应商ID
	private long supplier_id;
	private String supplier_name;

	//	取票人	
	private String contactee;

	//	取票人手机号	
	private String contact_mobile;

	//	取票人身份证号	
	private String idcard_no;

	//	取票人邮箱	
	private String contact_email;

	//	取票人拼音	
	private String contact_spelling;

	/** 收货地址*/
	private String delivery_addr;

	//	上车地址
	private String get_on_addr;
	//	下车地址
	private String get_off_addr;
	//	期望接送时间
	private String expect_use_car_time;
	//	航班号
	private String flight_no;
	//	列次号
	private String train_no;
	//	预计到店消费时间
	private String expect_to_shop_time;

	//		分销端备注
	private String remark;

	/**
	 * Getter method for property <tt>merchs</tt>.
	 * 
	 * @return property value of merchs
	 */
	public List<SaaSMerchExcelModel> getMerchs() {
		return merchs;
	}

	/**
	 * Setter method for property <tt>merchs</tt>.
	 * 
	 * @param merchs value to be assigned to property merchs
	 */
	public void setMerchs(List<SaaSMerchExcelModel> merchs) {
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
	 * Getter method for property <tt>contact_mobile</tt>.
	 * 
	 * @return property value of contact_mobile
	 */
	public String getContact_mobile() {
		return contact_mobile;
	}

	/**
	 * Setter method for property <tt>contact_mobile</tt>.
	 * 
	 * @param contact_mobile value to be assigned to property contact_mobile
	 */
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	/**
	 * Getter method for property <tt>idcard_no</tt>.
	 * 
	 * @return property value of idcard_no
	 */
	public String getIdcard_no() {
		return idcard_no;
	}

	/**
	 * Setter method for property <tt>idcard_no</tt>.
	 * 
	 * @param idcard_no value to be assigned to property idcard_no
	 */
	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	/**
	 * Getter method for property <tt>contact_email</tt>.
	 * 
	 * @return property value of contact_email
	 */
	public String getContact_email() {
		return contact_email;
	}

	/**
	 * Setter method for property <tt>contact_email</tt>.
	 * 
	 * @param contact_email value to be assigned to property contact_email
	 */
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	/**
	 * Getter method for property <tt>contact_spelling</tt>.
	 * 
	 * @return property value of contact_spelling
	 */
	public String getContact_spelling() {
		return contact_spelling;
	}

	/**
	 * Setter method for property <tt>contact_spelling</tt>.
	 * 
	 * @param contact_spelling value to be assigned to property contact_spelling
	 */
	public void setContact_spelling(String contact_spelling) {
		this.contact_spelling = contact_spelling;
	}

	/**
	 * Getter method for property <tt>delivery_addr</tt>.
	 * 
	 * @return property value of delivery_addr
	 */
	public String getDelivery_addr() {
		return delivery_addr;
	}

	/**
	 * Setter method for property <tt>delivery_addr</tt>.
	 * 
	 * @param delivery_addr value to be assigned to property delivery_addr
	 */
	public void setDelivery_addr(String delivery_addr) {
		this.delivery_addr = delivery_addr;
	}

	/**
	 * Getter method for property <tt>get_on_addr</tt>.
	 * 
	 * @return property value of get_on_addr
	 */
	public String getGet_on_addr() {
		return get_on_addr;
	}

	/**
	 * Setter method for property <tt>get_on_addr</tt>.
	 * 
	 * @param get_on_addr value to be assigned to property get_on_addr
	 */
	public void setGet_on_addr(String get_on_addr) {
		this.get_on_addr = get_on_addr;
	}

	/**
	 * Getter method for property <tt>get_off_addr</tt>.
	 * 
	 * @return property value of get_off_addr
	 */
	public String getGet_off_addr() {
		return get_off_addr;
	}

	/**
	 * Setter method for property <tt>get_off_addr</tt>.
	 * 
	 * @param get_off_addr value to be assigned to property get_off_addr
	 */
	public void setGet_off_addr(String get_off_addr) {
		this.get_off_addr = get_off_addr;
	}

	/**
	 * Getter method for property <tt>expect_use_car_time</tt>.
	 * 
	 * @return property value of expect_use_car_time
	 */
	public String getExpect_use_car_time() {
		return expect_use_car_time;
	}

	/**
	 * Setter method for property <tt>expect_use_car_time</tt>.
	 * 
	 * @param expect_use_car_time value to be assigned to property expect_use_car_time
	 */
	public void setExpect_use_car_time(String expect_use_car_time) {
		this.expect_use_car_time = expect_use_car_time;
	}

	/**
	 * Getter method for property <tt>flight_no</tt>.
	 * 
	 * @return property value of flight_no
	 */
	public String getFlight_no() {
		return flight_no;
	}

	/**
	 * Setter method for property <tt>flight_no</tt>.
	 * 
	 * @param flight_no value to be assigned to property flight_no
	 */
	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
	}

	/**
	 * Getter method for property <tt>train_no</tt>.
	 * 
	 * @return property value of train_no
	 */
	public String getTrain_no() {
		return train_no;
	}

	/**
	 * Setter method for property <tt>train_no</tt>.
	 * 
	 * @param train_no value to be assigned to property train_no
	 */
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	/**
	 * Getter method for property <tt>expect_to_shop_time</tt>.
	 * 
	 * @return property value of expect_to_shop_time
	 */
	public String getExpect_to_shop_time() {
		return expect_to_shop_time;
	}

	/**
	 * Setter method for property <tt>expect_to_shop_time</tt>.
	 * 
	 * @param expect_to_shop_time value to be assigned to property expect_to_shop_time
	 */
	public void setExpect_to_shop_time(String expect_to_shop_time) {
		this.expect_to_shop_time = expect_to_shop_time;
	}

	/**
	 * Getter method for property <tt>remark</tt>.
	 * 
	 * @return property value of remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Setter method for property <tt>remark</tt>.
	 * 
	 * @param remark value to be assigned to property remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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

	/**
	 * Getter method for property <tt>transaction_id</tt>.
	 * 
	 * @return property value of transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * Setter method for property <tt>transaction_id</tt>.
	 * 
	 * @param transaction_id value to be assigned to property transaction_id
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

}

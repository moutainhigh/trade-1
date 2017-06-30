/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.entity;

import java.util.Date;

import com.pzj.framework.toolkit.Check;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExportExcelEntity.java, v 0.1 2017年2月7日 下午5:58:02 Administrator Exp $
 */
public class SaaSOrderExportEntity {
	private String order_id;

	private String transaction_id;
	//订单状态
	private int order_status;
	//下单时间
	private Date create_time;
	//出游/入住时间
	private Date start_time;

	//出游结束时间
	private Date expire_time;

	private Date check_time;

	//订单来源
	private int sale_port;

	//	分销商ID
	private long reseller_id;
	//供应商ID
	private long supplier_id;

	private long original_supplier_id;

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

	private long product_id;

	private String merch_name;

	private int merch_type;

	private int merch_state;

	private String sku_name;
	//团散	
	private int product_varie;

	private int total_num;

	private int refund_num;

	private int refunding_num;

	private int check_num;

	private double sale_price;

	private double sale_after_rebate;

	private double purch_price;

	private double purch_after_rebate;

	private Date clean_time;

	private int is_cleaned;
	/** 收货地址*/
	private String delivery_addr_nation;
	private String delivery_addr_province;
	private String delivery_addr_city;
	private String delivery_addr_county;
	private String delivery_addr_detail;

	//	上车地址
	private String get_on_addr_nation;
	private String get_on_addr_province;
	private String get_on_addr_city;
	private String get_on_addr_county;
	private String get_on_addr_detail;
	//	下车地址
	private String get_off_addr_nation;
	private String get_off_addr_province;
	private String get_off_addr_city;
	private String get_off_addr_county;
	private String get_off_addr_detail;
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
		return refund_num - refunding_num;
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
	 * Getter method for property <tt>clean_time</tt>.
	 * 
	 * @return property value of clean_time
	 */
	public Date getClean_time() {
		return clean_time;
	}

	/**
	 * Setter method for property <tt>clean_time</tt>.
	 * 
	 * @param clean_time value to be assigned to property clean_time
	 */
	public void setClean_time(Date clean_time) {
		this.clean_time = clean_time;
	}

	/**
	 * Getter method for property <tt>is_cleaned</tt>.
	 * 
	 * @return property value of is_cleaned
	 */
	public int getIs_cleaned() {
		return is_cleaned;
	}

	/**
	 * Setter method for property <tt>is_cleaned</tt>.
	 * 
	 * @param is_cleaned value to be assigned to property is_cleaned
	 */
	public void setIs_cleaned(int is_cleaned) {
		this.is_cleaned = is_cleaned;
	}

	/**
	 * Getter method for property <tt>delivery_addr_nation</tt>.
	 * 
	 * @return property value of delivery_addr_nation
	 */
	public String getDelivery_addr_nation() {
		return delivery_addr_nation;
	}

	/**
	 * Setter method for property <tt>delivery_addr_nation</tt>.
	 * 
	 * @param delivery_addr_nation value to be assigned to property delivery_addr_nation
	 */
	public void setDelivery_addr_nation(String delivery_addr_nation) {
		this.delivery_addr_nation = delivery_addr_nation;
	}

	/**
	 * Getter method for property <tt>delivery_addr_province</tt>.
	 * 
	 * @return property value of delivery_addr_province
	 */
	public String getDelivery_addr_province() {
		return delivery_addr_province;
	}

	/**
	 * Setter method for property <tt>delivery_addr_province</tt>.
	 * 
	 * @param delivery_addr_province value to be assigned to property delivery_addr_province
	 */
	public void setDelivery_addr_province(String delivery_addr_province) {
		this.delivery_addr_province = delivery_addr_province;
	}

	/**
	 * Getter method for property <tt>delivery_addr_city</tt>.
	 * 
	 * @return property value of delivery_addr_city
	 */
	public String getDelivery_addr_city() {
		return delivery_addr_city;
	}

	/**
	 * Setter method for property <tt>delivery_addr_city</tt>.
	 * 
	 * @param delivery_addr_city value to be assigned to property delivery_addr_city
	 */
	public void setDelivery_addr_city(String delivery_addr_city) {
		this.delivery_addr_city = delivery_addr_city;
	}

	/**
	 * Getter method for property <tt>delivery_addr_county</tt>.
	 * 
	 * @return property value of delivery_addr_county
	 */
	public String getDelivery_addr_county() {
		return delivery_addr_county;
	}

	/**
	 * Setter method for property <tt>delivery_addr_county</tt>.
	 * 
	 * @param delivery_addr_county value to be assigned to property delivery_addr_county
	 */
	public void setDelivery_addr_county(String delivery_addr_county) {
		this.delivery_addr_county = delivery_addr_county;
	}

	/**
	 * Getter method for property <tt>delivery_addr_detail</tt>.
	 * 
	 * @return property value of delivery_addr_detail
	 */
	public String getDelivery_addr_detail() {
		return delivery_addr_detail;
	}

	/**
	 * Setter method for property <tt>delivery_addr_detail</tt>.
	 * 
	 * @param delivery_addr_detail value to be assigned to property delivery_addr_detail
	 */
	public void setDelivery_addr_detail(String delivery_addr_detail) {
		this.delivery_addr_detail = delivery_addr_detail;
	}

	/**
	 * Getter method for property <tt>get_on_addr_nation</tt>.
	 * 
	 * @return property value of get_on_addr_nation
	 */
	public String getGet_on_addr_nation() {
		return get_on_addr_nation;
	}

	/**
	 * Setter method for property <tt>get_on_addr_nation</tt>.
	 * 
	 * @param get_on_addr_nation value to be assigned to property get_on_addr_nation
	 */
	public void setGet_on_addr_nation(String get_on_addr_nation) {
		this.get_on_addr_nation = get_on_addr_nation;
	}

	/**
	 * Getter method for property <tt>get_on_addr_province</tt>.
	 * 
	 * @return property value of get_on_addr_province
	 */
	public String getGet_on_addr_province() {
		return get_on_addr_province;
	}

	/**
	 * Setter method for property <tt>get_on_addr_province</tt>.
	 * 
	 * @param get_on_addr_province value to be assigned to property get_on_addr_province
	 */
	public void setGet_on_addr_province(String get_on_addr_province) {
		this.get_on_addr_province = get_on_addr_province;
	}

	/**
	 * Getter method for property <tt>get_on_addr_city</tt>.
	 * 
	 * @return property value of get_on_addr_city
	 */
	public String getGet_on_addr_city() {
		return get_on_addr_city;
	}

	/**
	 * Setter method for property <tt>get_on_addr_city</tt>.
	 * 
	 * @param get_on_addr_city value to be assigned to property get_on_addr_city
	 */
	public void setGet_on_addr_city(String get_on_addr_city) {
		this.get_on_addr_city = get_on_addr_city;
	}

	/**
	 * Getter method for property <tt>get_on_addr_county</tt>.
	 * 
	 * @return property value of get_on_addr_county
	 */
	public String getGet_on_addr_county() {
		return get_on_addr_county;
	}

	/**
	 * Setter method for property <tt>get_on_addr_county</tt>.
	 * 
	 * @param get_on_addr_county value to be assigned to property get_on_addr_county
	 */
	public void setGet_on_addr_county(String get_on_addr_county) {
		this.get_on_addr_county = get_on_addr_county;
	}

	/**
	 * Getter method for property <tt>get_on_addr_detail</tt>.
	 * 
	 * @return property value of get_on_addr_detail
	 */
	public String getGet_on_addr_detail() {
		return get_on_addr_detail;
	}

	/**
	 * Setter method for property <tt>get_on_addr_detail</tt>.
	 * 
	 * @param get_on_addr_detail value to be assigned to property get_on_addr_detail
	 */
	public void setGet_on_addr_detail(String get_on_addr_detail) {
		this.get_on_addr_detail = get_on_addr_detail;
	}

	/**
	 * Getter method for property <tt>get_off_addr_nation</tt>.
	 * 
	 * @return property value of get_off_addr_nation
	 */
	public String getGet_off_addr_nation() {
		return get_off_addr_nation;
	}

	/**
	 * Setter method for property <tt>get_off_addr_nation</tt>.
	 * 
	 * @param get_off_addr_nation value to be assigned to property get_off_addr_nation
	 */
	public void setGet_off_addr_nation(String get_off_addr_nation) {
		this.get_off_addr_nation = get_off_addr_nation;
	}

	/**
	 * Getter method for property <tt>get_off_addr_province</tt>.
	 * 
	 * @return property value of get_off_addr_province
	 */
	public String getGet_off_addr_province() {
		return get_off_addr_province;
	}

	/**
	 * Setter method for property <tt>get_off_addr_province</tt>.
	 * 
	 * @param get_off_addr_province value to be assigned to property get_off_addr_province
	 */
	public void setGet_off_addr_province(String get_off_addr_province) {
		this.get_off_addr_province = get_off_addr_province;
	}

	/**
	 * Getter method for property <tt>get_off_addr_city</tt>.
	 * 
	 * @return property value of get_off_addr_city
	 */
	public String getGet_off_addr_city() {
		return get_off_addr_city;
	}

	/**
	 * Setter method for property <tt>get_off_addr_city</tt>.
	 * 
	 * @param get_off_addr_city value to be assigned to property get_off_addr_city
	 */
	public void setGet_off_addr_city(String get_off_addr_city) {
		this.get_off_addr_city = get_off_addr_city;
	}

	/**
	 * Getter method for property <tt>get_off_addr_county</tt>.
	 * 
	 * @return property value of get_off_addr_county
	 */
	public String getGet_off_addr_county() {
		return get_off_addr_county;
	}

	/**
	 * Setter method for property <tt>get_off_addr_county</tt>.
	 * 
	 * @param get_off_addr_county value to be assigned to property get_off_addr_county
	 */
	public void setGet_off_addr_county(String get_off_addr_county) {
		this.get_off_addr_county = get_off_addr_county;
	}

	/**
	 * Getter method for property <tt>get_off_addr_detail</tt>.
	 * 
	 * @return property value of get_off_addr_detail
	 */
	public String getGet_off_addr_detail() {
		return get_off_addr_detail;
	}

	/**
	 * Setter method for property <tt>get_off_addr_detail</tt>.
	 * 
	 * @param get_off_addr_detail value to be assigned to property get_off_addr_detail
	 */
	public void setGet_off_addr_detail(String get_off_addr_detail) {
		this.get_off_addr_detail = get_off_addr_detail;
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
	 * Getter method for property <tt>expire_time</tt>.
	 * 
	 * @return property value of expire_time
	 */
	public Date getExpire_time() {
		return expire_time;
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
	 * Setter method for property <tt>expire_time</tt>.
	 * 
	 * @param expire_time value to be assigned to property expire_time
	 */
	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
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

	public int getAuto_check_num() {
		if (check_num > 0) {
			if (!Check.NuNObject(check_time, expire_time)) {
				boolean isAuto = (expire_time.getTime() - check_time.getTime() == 1000);
				if (isAuto) {
					return check_num;
				}
			}

		}
		return 0;
	}

	public int getOverdueNum() {
		boolean isOverdueCheck = !Check.NuNObject(check_time, expire_time) && check_time.getTime() > expire_time.getTime();
		boolean isOverdueNotCheck = (check_time == null && expire_time != null && expire_time.getTime() < System
				.currentTimeMillis());

		if (isOverdueCheck) {
			return check_num;
		}
		if (isOverdueNotCheck) {
			return total_num - getRefund_num();
		}

		return 0;
	}

	public int getAfterRebateNumForSale() {
		if (this.sale_after_rebate > 0) {
			return total_num - getRefund_num();
		}
		return 0;
	}

	public int getAfterRebateNumForPurch() {
		if (this.purch_after_rebate > 0) {
			return total_num - getRefund_num();
		}
		return 0;
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
	 * Getter method for property <tt>original_supplier_id</tt>.
	 * 
	 * @return property value of original_supplier_id
	 */
	public long getOriginal_supplier_id() {
		return original_supplier_id;
	}

	/**
	 * Setter method for property <tt>original_supplier_id</tt>.
	 * 
	 * @param original_supplier_id value to be assigned to property original_supplier_id
	 */
	public void setOriginal_supplier_id(long original_supplier_id) {
		this.original_supplier_id = original_supplier_id;
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

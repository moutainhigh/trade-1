package com.pzj.trade.order.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 售票员订单列表.
 * @author YRJ
 *
 */
public class TicketSellerOrdersEntity {
	//订单属性
	private String order_id;
	private String transaction_id;
	private int order_status;
	private long reseller_id;
	private String contactee;
	private String contact_mobile;
	private Timestamp create_time;
	private double total_amount;
	private double refund_amount;
	//商品属性
	private String merch_id;
	private long voucher_id;
	private String merch_name;
	private String sku_name;
	private int merch_state;
	private int merch_type;
	private int is_refunding;
	private int total_num;
	private int check_num;
	private int refund_num;
	private int refunding_num;
	private double price;
	private long product_id;
	private int product_varie;
	private String start_time;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	public long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(int merch_state) {
		this.merch_state = merch_state;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(int is_refunding) {
		this.is_refunding = is_refunding;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	//	public int getCheck_num() {
	//		return check_num;
	//	}
	//
	//	public void setCheck_num(int check_num) {
	//		this.check_num = check_num;
	//	}
	//
	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	//
	//	public BigDecimal getPrice() {
	//		return price;
	//	}
	//
	//	public void setPrice(BigDecimal price) {
	//		this.price = price;
	//	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public int getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(int product_varie) {
		this.product_varie = product_varie;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		if (start_time != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			this.start_time = df.format(start_time);
		}
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
	 * Getter method for property <tt>price</tt>.
	 * 
	 * @return property value of price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Setter method for property <tt>price</tt>.
	 * 
	 * @param price value to be assigned to property price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Setter method for property <tt>start_time</tt>.
	 * 
	 * @param start_time value to be assigned to property start_time
	 */
	public void setStart_time(String start_time) {
		this.start_time = start_time;
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

}

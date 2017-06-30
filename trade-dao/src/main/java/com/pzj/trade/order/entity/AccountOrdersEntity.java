package com.pzj.trade.order.entity;

import java.util.Date;

/**
 * 
 * @author GLG
 *
 */
public class AccountOrdersEntity {
	/**订单编号*/
	private String order_id;

	private String transaction_id;

	/**订单状态*/
	private int order_status;

	/**分销商id*/
	private long reseller_id;
	/**分销商id*/
	private long supplier_id;

	/**下单时间*/
	private Date create_time;

	/**总数 */
	private int total_num;

	/**订单总金额  */
	private double total_amount;

	/** 订单完结时间 */
	private Date confirm_time;

	private int third_pay_type;

	private int order_level;

	private int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getOrder_level() {
		return order_level;
	}

	public void setOrder_level(int order_level) {
		this.order_level = order_level;
	}

	public int getThird_pay_type() {
		return third_pay_type;
	}

	public void setThird_pay_type(int third_pay_type) {
		this.third_pay_type = third_pay_type;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Date getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(Date confirm_time) {
		this.confirm_time = confirm_time;
	}

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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

}

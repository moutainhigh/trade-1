package com.pzj.trade.order.entity;

import java.sql.Timestamp;

/**
 * 订单商品明细响应实体，按用户规格区分
 * @author GLG
 *
 */
public class SettlementOrderMerchDetailEntity {

	/**
	 * 订单号ID.
	 */
	private String order_id;
	/**
	 * 事务ID.
	 */
	private String transaction_id;
	/**用户id*/
	private long user_id;
	/**初始供应商id*/
	private long osupplier_id;
	/**skuname*/
	private String sku_name;
	/**分销金额*/
	private double sale_price;
	/**采购金额*/
	private double purch_price;

	/**后返金额*/
	private double after_rebate_amount;
	/**订单级别*/
	private int order_level;

	private String merch_id;

	private String merch_name;
	/**
	 * 订单状态.
	 */
	private int order_status;
	/**
	 * 订单创建时间.
	 */
	private Timestamp create_time;
	/**
	 * 订单支付时间.
	 */
	private Timestamp pay_time;
	/**
	 * 订单完成时间.
	 */
	private Timestamp confirm_time;

	/**
	 * 版本.
	 */
	private int version;

	private long product_id;

	private int total_num;

	private int refund_num;

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public long getOsupplier_id() {
		return osupplier_id;
	}

	public void setOsupplier_id(long osupplier_id) {
		this.osupplier_id = osupplier_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public double getSale_price() {
		return sale_price;
	}

	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}

	public double getPurch_price() {
		return purch_price;
	}

	public void setPurch_price(double purch_price) {
		this.purch_price = purch_price;
	}

	public double getAfter_rebate_amount() {
		return after_rebate_amount;
	}

	public void setAfter_rebate_amount(double after_rebate_amount) {
		this.after_rebate_amount = after_rebate_amount;
	}

	public int getOrder_level() {
		return order_level;
	}

	public void setOrder_level(int order_level) {
		this.order_level = order_level;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(final String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(final int order_status) {
		this.order_status = order_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getPay_time() {
		return pay_time;
	}

	public void setPay_time(final Timestamp pay_time) {
		this.pay_time = pay_time;
	}

	public Timestamp getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(final Timestamp confirm_time) {
		this.confirm_time = confirm_time;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

}

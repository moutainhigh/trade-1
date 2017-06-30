package com.pzj.trade.merch.entity;

/**
 * 商品基本信息表数据.
 * @author YRJ
 *
 */
public class MerchBasicEntity {

	private String merch_id;

	private int merch_state;

	private String merch_name;

	private String sku_name;

	private long product_id;

	private int total_num;

	private int refund_num;

	private double price;

	private int merch_type;

	//是否退款中. 0是1否,
	private int is_refunding;

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(final String merch_id) {
		this.merch_id = merch_id;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(final int merch_state) {
		this.merch_state = merch_state;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(final String merch_name) {
		this.merch_name = merch_name;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(final String sku_name) {
		this.sku_name = sku_name;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(final long product_id) {
		this.product_id = product_id;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(final int total_num) {
		this.total_num = total_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(final int refund_num) {
		this.refund_num = refund_num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(final int merch_type) {
		this.merch_type = merch_type;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(final int is_refunding) {
		this.is_refunding = is_refunding;
	}

}

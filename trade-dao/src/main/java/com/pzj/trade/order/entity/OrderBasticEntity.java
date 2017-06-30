package com.pzj.trade.order.entity;

/**
 * 订单基本属性信息.
 * @author YRJ
 *
 */
public class OrderBasticEntity {

	private String order_id;

	private int order_level;

	private long supplier_id;

	private long reseller_id;

	private int version;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getOrder_level() {
		return order_level;
	}

	public void setOrder_level(int order_level) {
		this.order_level = order_level;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}

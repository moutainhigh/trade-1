package com.pzj.trade.order.entity;

public class PurchaseOrderEntity {

	/** 订单ID */
	private String orderId;

	/** 供应商ID */
	private long supplierId;

	public PurchaseOrderEntity() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(final long supplierId) {
		this.supplierId = supplierId;
	}

}

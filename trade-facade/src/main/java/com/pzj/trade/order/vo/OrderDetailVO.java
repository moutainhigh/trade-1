package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * 订单详情查询参数.
 * @author CHJ
 *
 */
public class OrderDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单ID 必填*/
	private String order_id;

	/** 调用端口，供应商调用为1，分销商调用为2，支撑平台调用为3 必填*/
	private int call_port;

	/**
	 * 查询类型:
	 * 
	 *supplierDetail订单详情查询(供应)
	 *supplierDetailDownLine订单详情详情查询(供应线下)
	 *supplierDetailOnline订单详情查询(供应线上)
	 *supplierDetailRefund退款详情查询（供应）
	 *supplierDetailConfirm手动确认订单详情查询（供应）
	 *supplierDetailIsForceRefund强制退款申请订单详情查询（供应）
	 *suppplierDetailCheck批量核销_检票订单详情查询（供应）
	 *suppplierDetailRefundAudit退款审核订单详情查询（供应）
	 *suppplierRefundResult退款审核结果详情查询（供应）
	 *supplierQueryRefundAudit退款审核订单查询（供应）
	 * */
	private String query_type;

	/** 供应商ID */
	private long supplier_id;

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getCall_port() {
		return call_port;
	}

	public void setCall_port(int call_port) {
		this.call_port = call_port;
	}

}

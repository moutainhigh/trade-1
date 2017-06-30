package com.pzj.trade.order.vo;

import java.io.Serializable;

/**
 * sales订单查询参数.
 * @author CHJ
 *
 */
public class SalesOrderVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单ID 必填*/
	private String order_id;

	/**
	 * 商品ID.
	 */
	private String merch_id;

	/** 订单类型：1采购 2销售*/
	private int order_type;

	/**
	 * 是否是强制退款的请求 0普通请求，1强制退款的请求
	 */
	private int isFourceRefund = 0;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}

	public int getIsFourceRefund() {
		return isFourceRefund;
	}

	public void setIsFourceRefund(int isFourceRefund) {
		this.isFourceRefund = isFourceRefund;
	}

}

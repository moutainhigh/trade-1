package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 代下单参数.
 * @author CHJ
 *
 */
public class OrderRemarkModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单ID 必填*/
	private String order_id;

	/** 备注信息*/
	private String remark;

	/** 操作人*/
	private long operator;

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
	 * Getter method for property <tt>operator</tt>.
	 * 
	 * @return property value of operator
	 */
	public long getOperator() {
		return operator;
	}

	/**
	 * Setter method for property <tt>operator</tt>.
	 * 
	 * @param operator value to be assigned to property operator
	 */
	public void setOperator(long operator) {
		this.operator = operator;
	}

}

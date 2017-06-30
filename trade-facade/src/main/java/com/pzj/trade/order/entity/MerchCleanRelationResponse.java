/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 订单商品清算关系信息
 * @author glg
 * @version
 *  */
public class MerchCleanRelationResponse implements Serializable {

	/**  */
	private static final long serialVersionUID = -1807313205732799419L;

	/**
	 * 清算状态. 0: 未清算; 1: 已清算; 2: 清算失败; 3：冻结(强制退款,退已经核销的商品,在退款申请事触发）
	 */
	private int clean_state;

	/**
	 * 正常清算数量
	 */
	private int normal_clean_num;
	/**
	 * 逾期清算数量
	 */
	private int overdue_clean_num;
	/**
	 * 退款清算数量
	 */
	private int refund_clean_num;

	/**
	 * 正常结算金额
	 */
	private double normal_clean_amount;
	/**
	 * 逾期结算金额
	 */
	private double overdue_clean_amount;
	/**
	 * 退款结算金额
	 */
	private double refund_clean_amount;
	/**
	 * 是否是为负数的结算单 0否（默认）1是，强制退款生成
	 * 
	 * */
	private int is_minus_clean;

	/**
	 * 清算类别. 1: 正常清算; 2: 强制退款清算，3 订单未支付清算'
	 */
	private int clean_type;

	public int getClean_type() {
		return clean_type;
	}

	public void setClean_type(int clean_type) {
		this.clean_type = clean_type;
	}

	public int getClean_state() {
		return clean_state;
	}

	public void setClean_state(int clean_state) {
		this.clean_state = clean_state;
	}

	public int getNormal_clean_num() {
		return normal_clean_num;
	}

	public void setNormal_clean_num(int normal_clean_num) {
		this.normal_clean_num = normal_clean_num;
	}

	public int getOverdue_clean_num() {
		return overdue_clean_num;
	}

	public void setOverdue_clean_num(int overdue_clean_num) {
		this.overdue_clean_num = overdue_clean_num;
	}

	public int getRefund_clean_num() {
		return refund_clean_num;
	}

	public void setRefund_clean_num(int refund_clean_num) {
		this.refund_clean_num = refund_clean_num;
	}

	public double getNormal_clean_amount() {
		return normal_clean_amount;
	}

	public void setNormal_clean_amount(double normal_clean_amount) {
		this.normal_clean_amount = normal_clean_amount;
	}

	public double getOverdue_clean_amount() {
		return overdue_clean_amount;
	}

	public void setOverdue_clean_amount(double overdue_clean_amount) {
		this.overdue_clean_amount = overdue_clean_amount;
	}

	public double getRefund_clean_amount() {
		return refund_clean_amount;
	}

	public void setRefund_clean_amount(double refund_clean_amount) {
		this.refund_clean_amount = refund_clean_amount;
	}

	public int getIs_minus_clean() {
		return is_minus_clean;
	}

	public void setIs_minus_clean(int is_minus_clean) {
		this.is_minus_clean = is_minus_clean;
	}

}

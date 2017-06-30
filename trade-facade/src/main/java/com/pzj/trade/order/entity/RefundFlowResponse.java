/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单商品的退款流水信息
 * @author chj
 * @version
 *  */
public class RefundFlowResponse implements Serializable {

	/**  */
	private static final long serialVersionUID = 3123370145969146700L;
	/** 退款单号 */
	private String refund_id;
	/**  商品名*/
	private String merch_name;
	/**  订单ID*/
	private String order_id;
	/**商品ID  */
	private String merchandise_id;
	/** 政策ID */
	private long strategy_id;
	/** 退款数量 */
	private Integer refund_num;
	/** 退款价格 */
	private Double refund_price;
	/**  退款状态（1 退款中  2退款成功 3 退款失败 ）*/
	private Integer refund_state;
	/** 退款类型 1供应商 2 分销商 */
	private Integer refund_type;
	/** 退款总额 */
	private Double amount;
	/** 更新时间 */
	private Date update_time;
	/** 创建时间 */
	private Date create_time;
	/** 
	 * <li>0,未使用规则</li>
	 * <li>1,时间点前规则</li>
	 * <li>2,时间点后规则</li>
	 */
	private int refundRuleType;

	/**
	 * 退款审核状态
	 * <li>1,平台待审核</li>
	 * <li>2,财务待审核</li>
	 * <li>8,对接审核中</li>
	 * <li>9,审核完毕</li>
	 * <li>7,拒绝退款</li>
	 */
	private Integer refundAuditState;
	/**退款申请人*/
	private long applier_id;

	/**是否强制退款*/
	private Integer is_force;

	/**
	 * 退款发起方
	 * <li>1,分销商</li>
	 * <li>2,大平台</li>
	 * <li>3,确认拒绝</li>
	 */
	private int initParty;

	public int getInitParty() {
		return initParty;
	}

	public void setInitParty(int initParty) {
		this.initParty = initParty;
	}

	public Integer getIs_force() {
		return is_force;
	}

	public void setIs_force(Integer is_force) {
		this.is_force = is_force;
	}

	public long getApplier_id() {
		return applier_id;
	}

	public void setApplier_id(long applier_id) {
		this.applier_id = applier_id;
	}

	public Integer getRefundAuditState() {
		return refundAuditState;
	}

	public void setRefundAuditState(Integer refundAuditState) {
		this.refundAuditState = refundAuditState;
	}

	public int getRefundRuleType() {
		return refundRuleType;
	}

	public void setRefundRuleType(int refundRuleType) {
		this.refundRuleType = refundRuleType;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMerchandise_id() {
		return merchandise_id;
	}

	public void setMerchandise_id(String merchandise_id) {
		this.merchandise_id = merchandise_id;
	}

	public Integer getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(Integer refund_num) {
		this.refund_num = refund_num;
	}

	public Double getRefund_price() {
		return refund_price;
	}

	public void setRefund_price(Double refund_price) {
		this.refund_price = refund_price;
	}

	public Integer getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(Integer refund_state) {
		this.refund_state = refund_state;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public long getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(long strategy_id) {
		this.strategy_id = strategy_id;
	}

	public Integer getRefund_type() {
		return refund_type;
	}

	public void setRefund_type(Integer refund_type) {
		this.refund_type = refund_type;
	}

}

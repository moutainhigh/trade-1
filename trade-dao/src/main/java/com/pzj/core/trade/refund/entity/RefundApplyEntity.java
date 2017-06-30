/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 退款申请信息
 * 
 * 库:trade 表:t_refund_apply
 * 
 * @author DongChunfu
 * @date 2016年12月23日
 */
public class RefundApplyEntity implements Serializable {
	private static final long serialVersionUID = -9222592051355603700L;

	/** 退款申请ID */
	private Long applyId;

	/** 申请发起人ID */
	private Long applierId;

	private String transactionId = "";
	/**
	 * 退款发起的时候，销售订单对应的订单状态
	 */
	private int applySaleOrderStatus = 10;

	/**
	 * 退款发起方
	 * <li>1,分销商</li>
	 * <li>2,大平台</li>
	 * <li>3,确认拒绝</li>
	 */
	private int initParty;

	/** 退款ID */
	private String refundId;

	/**
	 * 部分退款
	 * 
	 * <li>0,整单退</li>
	 * <li>1,部分退</li>
	 */
	private int isParty;

	/**
	 * 强制退款
	 * 
	 * <li>0,普通退</li>
	 * <li>1,强制退</li>
	 */
	private Integer isForce;

	/**
	 * 退款状态
	 * <li>1,退款中</li>
	 * <li>2,成功</li>
	 * <li>3,失败</li>
	 */
	private Integer refundState;
	/**
	 * 退款审核状态
	 * <li>1,平台待审核</li>
	 * <li>2,财务待审核</li>
	 * <li>8,对接审核中</li>
	 * <li>9,审核完毕</li>
	 * <li>7,拒绝退款</li>
	 */
	private Integer refundAuditState;

	/**
	 * 退款申请时间
	 */
	private Date createTime;

	/**
	 * 退款申请更新时间
	 */
	private Date updateTime;

	public RefundApplyEntity() {
		super();
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(final Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplierId() {
		return applierId;
	}

	public int getInitParty() {
		return initParty;
	}

	public void setInitParty(final int initParty) {
		this.initParty = initParty;
	}

	public void setApplierId(final Long applierId) {
		this.applierId = applierId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public int getIsParty() {
		return isParty;
	}

	public void setIsParty(final int isParty) {
		this.isParty = isParty;
	}

	public Integer getIsForce() {
		return isForce;
	}

	public void setIsForce(final Integer isForce) {
		this.isForce = isForce;
	}

	public Integer getRefundState() {
		return refundState;
	}

	public void setRefundState(final Integer refundState) {
		this.refundState = refundState;
	}

	public Integer getRefundAuditState() {
		return refundAuditState;
	}

	public void setRefundAuditState(final Integer refundAuditState) {
		this.refundAuditState = refundAuditState;
	}

	public static final RefundApplyEntity newInstance() {
		return new RefundApplyEntity();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(final Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getApplySaleOrderStatus() {
		return applySaleOrderStatus;
	}

	public void setApplySaleOrderStatus(int applySaleOrderStatus) {
		this.applySaleOrderStatus = applySaleOrderStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}

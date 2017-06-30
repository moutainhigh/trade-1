/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.refund.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 退款申请财务前端展示实体
 *
 * @author DongChunfu
 * @date 2016年12月13日
 */
public class ForceRefundApplyVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单号 */
	private String orderId;
	/** 退款单号 */
	private String refundId;
	/**
	 *  支付类型
	 *  <li>4,其它支付</li>
	 *  <li>0,全余额支付</li>
	 *  <li>1,支付宝支付</li>
	 *  <li>11,账扣+支付宝支付</li>
	 *  <li>2,微信支付</li>
	 *  <li>12,账扣+微信支付</li>
	 */
	private Integer payType;
	/** 支付人 */
	private Long paierId;
	/** 支付金额 */
	private Double payAmound;
	/** 退款金额 */
	private Double refundAmound;
	/** 商品名称 */
	private List<MerchVO> merches;
	/** 申请人 */
	private Long applierId;
	/** 申请日期 */
	private Date applyDate;
	/** 审核人 */
	private Long auditor;
	/** 审核时间 */
	private Date auditDate;
	/**
	 * 退款审核状态
	 * <ul>
	 * <li>1. 待审核态</li>
	 * <li>2. 审核通过</li>
	 * <li>3. 拒绝退款</li>
	 * <ul>
	 */
	private int auditState;
	/**
	 * 是否需要三方审核,默认为0;
	 * <li>0,不需要对接审核</li>
	 * <li>1,需要对接审核</li>
	 */
	private int dockAudit;
	/** 拒绝原因 */
	private String refuseReason;

	public ForceRefundApplyVO() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(final Integer payType) {
		this.payType = payType;
	}

	public Long getPaierId() {
		return paierId;
	}

	public void setPaierId(final Long paierId) {
		this.paierId = paierId;
	}

	public Long getApplierId() {
		return applierId;
	}

	public void setApplierId(final Long applierId) {
		this.applierId = applierId;
	}

	public List<MerchVO> getMerches() {
		return merches;
	}

	public void setMerches(final List<MerchVO> merches) {
		this.merches = merches;
	}

	public Double getPayAmound() {
		return payAmound;
	}

	public void setPayAmound(final Double payAmound) {
		this.payAmound = payAmound;
	}

	public Double getRefundAmound() {
		return refundAmound;
	}

	public void setRefundAmound(final Double refundAmound) {
		this.refundAmound = refundAmound;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(final Date applyDate) {
		this.applyDate = applyDate;
	}

	public Long getAuditor() {
		return auditor;
	}

	public void setAuditor(final Long auditor) {
		this.auditor = auditor;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(final Date auditDate) {
		this.auditDate = auditDate;
	}

	public int getAuditState() {
		return auditState;
	}

	public void setAuditState(final int auditState) {
		this.auditState = auditState;
	}

	public int getDockAudit() {
		return dockAudit;
	}

	public void setDockAudit(final int dockAudit) {
		this.dockAudit = dockAudit;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(final String refuseReason) {
		this.refuseReason = refuseReason;
	}
}

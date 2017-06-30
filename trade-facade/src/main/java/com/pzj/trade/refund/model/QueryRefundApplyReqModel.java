/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.refund.model;

import java.io.Serializable;
import java.util.Date;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 退款申请查询请求参数(财务)
 *
 * @author DongChunfu
 * @date 2016年12月13日
 */
public class QueryRefundApplyReqModel extends PageableRequestBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单ID */
	private String orderId;

	/**
	 * 退款审核状态
	 * <ul>
	 * <li>1. 待审核态</li>
	 * <li>2. 审核通过</li>
	 * <li>3. 拒绝退款</li>
	 * <ul>
	 */
	private Integer auditState;

	/**
	 * 三方审核状态
	 *
	 * <ul>
	 * <li>0. 对接审核中</li>
	 * <li>1. 对接审核通过</li>
	 * <li>2. 对接审核拒绝</li>
	 * <li>3. 不需要对接审核</li>
	 * <ul>
	 */
	private Integer thirdAuditState;

	/**是否分页*/
	private Boolean isPage = Boolean.TRUE;

	/** 开始申请时间 */
	private Date startApplyDate;

	/** 申请结束时间 */
	private Date endApplyDate;

	/** 开始审核时间 */
	private Date startAuditDate;

	/** 审核结束时间 */
	private Date endAuditDate;

	public QueryRefundApplyReqModel() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(final Integer auditState) {
		this.auditState = auditState;
	}

	public Integer getThirdAuditState() {
		return thirdAuditState;
	}

	public void setThirdAuditState(final Integer thirdAuditState) {
		this.thirdAuditState = thirdAuditState;
	}

	public Boolean getIsPage() {
		return isPage;
	}

	public void setIsPage(final Boolean isPage) {
		this.isPage = isPage;
	}

	public Date getStartApplyDate() {
		return startApplyDate;
	}

	public void setStartApplyDate(final Date startApplyDate) {
		this.startApplyDate = startApplyDate;
	}

	public Date getEndApplyDate() {
		return endApplyDate;
	}

	public void setEndApplyDate(final Date endApplyDate) {
		this.endApplyDate = endApplyDate;
	}

	public Date getStartAuditDate() {
		return startAuditDate;
	}

	public void setStartAuditDate(final Date startAuditDate) {
		this.startAuditDate = startAuditDate;
	}

	public Date getEndAuditDate() {
		return endAuditDate;
	}

	public void setEndAuditDate(final Date endAuditDate) {
		this.endAuditDate = endAuditDate;
	}

}

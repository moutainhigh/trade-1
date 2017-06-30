/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 退款申请信息分页查询参数
 */
public class RefundApplyQueryPageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 订单ID */
	private String orderId;

	/** 财务审核状态 */
	private Integer auditState;

	/** 对接审核状态 */
	private Integer thirdAuditState;

	/** 开始申请时间 */
	private Date startApplyDate;

	/** 申请结束时间 */
	private Date endApplyDate;

	/** 开始审核时间 */
	private Date startAuditDate;

	/** 审核结束时间 */
	private Date endAuditDate;

	/**是否分页*/
	private Boolean isPage;
	/** 起始条数 */
	private Integer startIndex;
	/** 结束条数 */
	private Integer endIndex;

	public RefundApplyQueryPageEntity() {
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

	public Boolean getIsPage() {
		return isPage;
	}

	public void setIsPage(final Boolean isPage) {
		this.isPage = isPage;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(final Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(final Integer endIndex) {
		this.endIndex = endIndex;
	}

	public static final RefundApplyQueryPageEntity newInstance() {
		return new RefundApplyQueryPageEntity();
	}

}

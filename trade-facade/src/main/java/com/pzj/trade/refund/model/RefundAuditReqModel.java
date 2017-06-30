/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.refund.model;

import java.io.Serializable;

/**
 * 退款流程封装的请求参数
 * 
 * @author Administrator
 * @version $Id: RefundReqVo.java, v 0.1 2016年8月3日 下午6:02:06 Administrator Exp $
 */
public class RefundAuditReqModel implements Serializable {

	private static final long serialVersionUID = -7982449475851585787L;

	/** 退款唯一凭证(必传) */
	private String refundId;

	/**
	 * 主订单ID(必传)
	 */
	private String saleOrderId;
	/** 审核人ID (非必传)*/
	private Long auditorId;

	/**
	 * 审核方(必传)
	 * <ul>
	 * <li>1. 平台</li>
	 * <li>2. 财务</li>
	 * <li>3. 对接</li>
	 * <ul>
	 */
	private int auditorParty;
	/**
	 * 退款审核结果(必传)
	 * <ul>
	 * <li>1. 初始状态</li>
	 * <li>2. 审核通过</li>
	 * <li>3. 拒绝退款</li>
	 * <ul>
	 */
	private int auditResult;

	/** 拒绝原因(非必传) */
	private String refusedMsg;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(final Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getRefusedMsg() {
		return refusedMsg;
	}

	public void setRefusedMsg(final String refusedMsg) {
		this.refusedMsg = refusedMsg;
	}

	public int getAuditorParty() {
		return auditorParty;
	}

	public void setAuditorParty(final int auditorParty) {
		this.auditorParty = auditorParty;
	}

	public int getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(final int auditResult) {
		this.auditResult = auditResult;
	}

	public String getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

}

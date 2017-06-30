/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.entity;

import java.io.Serializable;

/**
 * 退款申请信息
 * 
 * 库:trade 表:t_refund_apply_info
 * 
 * @author DongChunfu
 * @date 2016年12月23日
 */
public class RefundApplyInfoEntity implements Serializable {
	private static final long serialVersionUID = -9222592051355603700L;

	/** 主键 */
	private Long infoId;

	/** 退款ID */
	private String refundId;

	/**
	 * 操作类型 【0 申请的相关信息 1审核操作的相关信息】
	 */
	private int operType = 1;

	/** 退款原因 */
	private String reason;

	/** 图片路径 */
	private String imgSrc;

	public RefundApplyInfoEntity() {
		super();
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(final Long infoId) {
		this.infoId = infoId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(final String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public int getOperType() {
		return operType;
	}

	public void setOperType(int operType) {
		this.operType = operType;
	}

}

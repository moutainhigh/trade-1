/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 退款操作日志记录
 * 对应trade库t_refund_oper_log表
 * 
 * @author DongChunfu
 * @version $Id: RefundOperLog.java, v 0.1 2016年11月30日 下午6:03:11 dongchunfu Exp $
 */
public class RefundOperLog implements Serializable {
	private static final long serialVersionUID = 4840828823790859716L;

	/**主键*/
	private Long logId;
	/**退款ID*/
	private String refundId;
	/**操作人ID*/
	private Long operatorId;
	/**前置状态*/
	private Integer prev;
	/**未来状态*/
	private Integer later;
	/**操作来源*/
	private Integer action;
	/**创建时间*/
	private Date createTime;

	public RefundOperLog() {
		super();
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getPrev() {
		return prev;
	}

	public void setPrev(Integer prev) {
		this.prev = prev;
	}

	public Integer getLater() {
		return later;
	}

	public void setLater(Integer later) {
		this.later = later;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

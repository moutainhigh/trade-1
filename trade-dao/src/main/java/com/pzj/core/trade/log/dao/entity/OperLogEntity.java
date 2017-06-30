/*
 * TOperLog.java
 
 * www.piaozhijia.coim
 */
package com.pzj.core.trade.log.dao.entity;

import java.util.Date;

/**
 * vo.区域
 * @author 票之家
 */

public class OperLogEntity {
	/** 日志主键 */
	private Long logId;
	/** 业务主键，一般为交易ID */
	private String orderId;
	/** 操作人ID */
	private Long operator;
	/** 操作前状态 */
	private Integer prev;
	/** 操作后状态 */
	private Integer next;
	/** 事件类型：book_create:预约单创建 book_update:预约单更新 book_audit:预约单审核通过 book_refuse:预约单审核拒绝 book_cancel:预约单取消 */
	private String event;
	/** 内容 */
	private String context;
	/** 创建时间 */
	private Long createTime;

	/** 设置 日志主键 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}

	/** 得到 日志主键 */
	public Long getLogId() {
		return logId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** 设置 操作人ID */
	public void setOperator(Long operator) {
		this.operator = operator;
	}

	/** 得到 操作人ID */
	public Long getOperator() {
		return operator;
	}

	/** 设置 操作前状态 */
	public void setPrev(Integer prev) {
		this.prev = prev;
	}

	/** 得到 操作前状态 */
	public Integer getPrev() {
		return prev;
	}

	/** 设置 操作后状态 */
	public void setNext(Integer next) {
		this.next = next;
	}

	/** 得到 操作后状态 */
	public Integer getNext() {
		return next;
	}

	/** 设置 事件类型：book_create:预约单创建 book_update:预约单更新 book_audit:预约单审核通过 book_refuse:预约单审核拒绝 book_cancel:预约单取消 */
	public void setEvent(String event) {
		this.event = event;
	}

	/** 得到 事件类型：book_create:预约单创建 book_update:预约单更新 book_audit:预约单审核通过 book_refuse:预约单审核拒绝 book_cancel:预约单取消 */
	public String getEvent() {
		return event;
	}

	/** 设置 内容 */
	public void setContext(String context) {
		this.context = context;
	}

	/** 得到 内容 */
	public String getContext() {
		return context;
	}

	/** 设置 创建时间 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/** 得到 创建时间 */
	public Long getCreateTime() {
		return createTime;
	}

	public OperLogEntity() {
		this.createTime = new Date().getTime();
	}

	public OperLogEntity(String orderId, long operator, String event, int next) {
		this.createTime = new Date().getTime();
		this.orderId = orderId;
		this.operator = operator;
		this.event = event;
		this.next = next;

	}

}

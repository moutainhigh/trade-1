package com.pzj.core.trade.refund.engine.model;

import com.pzj.core.trade.refund.engine.common.RefundLogEventEnum;

/**
 * 退款操作日志对应的Model
 * @author kangzl
 *
 */
public class RefundOperMQLogModel {

	private String refundId;
	private String rootOrderId;
	private RefundLogEventEnum event;
	private long operationId;
	private String operMsg;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRootOrderId() {
		return rootOrderId;
	}

	public void setRootOrderId(String rootOrderId) {
		this.rootOrderId = rootOrderId;
	}

	public RefundLogEventEnum getEvent() {
		return event;
	}

	public void setEvent(RefundLogEventEnum event) {
		this.event = event;
	}

	public String getOperMsg() {
		return operMsg;
	}

	public void setOperMsg(String operMsg) {
		this.operMsg = operMsg;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}
}

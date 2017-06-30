package com.pzj.core.trade.refund.engine.model;

/**
 * 退款申请的业务返回直
 * @author kangzl
 *
 */
public class RefundApplyResultModel {
	private String refundId;
	private int auditState;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public int getAuditState() {
		return auditState;
	}

	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}
}

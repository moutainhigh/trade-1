package com.pzj.core.trade.refund.engine.exception;

/**
 * 当前退款状态审核方错误.
 * @author DongChunfu
 *
 */
public class RefundStateAuditPartyException extends RefundException{

	public RefundStateAuditPartyException(int errCode, String message) {
		super(errCode, message);
	}
	
	public RefundStateAuditPartyException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

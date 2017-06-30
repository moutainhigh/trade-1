package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款流水状态异常.
 * @author DongChunfu
 *
 */
public class RefundFlowStateException extends RefundException{

	public RefundFlowStateException(int errCode, String message) {
		super(errCode, message);
	}
	
	public RefundFlowStateException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

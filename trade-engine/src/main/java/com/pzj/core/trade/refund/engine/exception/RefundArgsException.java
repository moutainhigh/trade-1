package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款参数异常.
 * @author YRJ
 *
 */
public class RefundArgsException extends RefundException{

	public RefundArgsException(int errCode, String message) {
		super(errCode, message);
	}
	
	public RefundArgsException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

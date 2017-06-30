package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款订单异常.
 * 
 * @author DongChunfu
 *
 */
public class RefundOrderException extends RefundException {
	private static final long serialVersionUID = 1L;

	public RefundOrderException(final int errCode, final String message) {
		super(errCode, message);
	}

	public RefundOrderException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}

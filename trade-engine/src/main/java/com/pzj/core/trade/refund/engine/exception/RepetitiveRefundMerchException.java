package com.pzj.core.trade.refund.engine.exception;

/**
 * 重复的退款商品异常
 * 
 * @author DongChunfu
 *
 */
public class RepetitiveRefundMerchException extends RefundException {

	public RepetitiveRefundMerchException(final int errCode, final String message) {
		super(errCode, message);
	}

	public RepetitiveRefundMerchException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

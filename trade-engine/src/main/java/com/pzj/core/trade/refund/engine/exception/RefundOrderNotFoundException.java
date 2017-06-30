package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款订单不存在异常.
 * 
 * @author DongChunfu
 * @date 2016年12月15日
 */
public class RefundOrderNotFoundException extends RefundException {
	private static final long serialVersionUID = 1L;

	public RefundOrderNotFoundException(final int errCode, final String message) {
		super(errCode, message);
	}

	public RefundOrderNotFoundException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}

package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款标识为空
 * 
 * @author DongChunfu
 * @date 2016年12月22日
 */
public class RefundSignIsNoneException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundSignIsNoneException(final int errCode, final String message) {
		super(errCode, message);
	}

	public RefundSignIsNoneException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

package com.pzj.core.trade.refund.engine.exception;

/**
 * 对接确认退款异常
 * 
 * @author DongChunfu
 * @date 2016年12月22日
 */
public class DockConfirmRefundException extends RefundException {

	private static final long serialVersionUID = 1L;

	public DockConfirmRefundException(final int errCode, final String message) {
		super(errCode, message);
	}

	public DockConfirmRefundException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

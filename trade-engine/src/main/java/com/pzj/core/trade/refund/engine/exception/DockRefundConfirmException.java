package com.pzj.core.trade.refund.engine.exception;

/**
 * 对接退款确认异常
 * @author DongChunfu
 */
public class DockRefundConfirmException extends RefundException {

	private static final long serialVersionUID = 1L;

	public DockRefundConfirmException(int errCode, String message) {
		super(errCode, message);
	}

}

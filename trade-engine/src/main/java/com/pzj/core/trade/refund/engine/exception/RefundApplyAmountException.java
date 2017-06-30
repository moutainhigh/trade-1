package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款申请金额异常.
 * @author DongChunfu
 *
 */
public class RefundApplyAmountException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundApplyAmountException(int errCode, String message) {
		super(errCode, message);
	}

}

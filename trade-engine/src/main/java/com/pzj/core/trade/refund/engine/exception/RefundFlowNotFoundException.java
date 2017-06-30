package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款流水不存在异常.
 * @author DongChunfu
 *
 */
public class RefundFlowNotFoundException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundFlowNotFoundException(int errCode, String message) {
		super(errCode, message);
	}

}

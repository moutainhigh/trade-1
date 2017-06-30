package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款操作时间间隔异常.
 * @author DongChunfu
 *
 */
public class RefundOperationIntervalException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundOperationIntervalException(int errCode, String message) {
		super(errCode, message);
	}

}

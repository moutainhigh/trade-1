package com.pzj.core.trade.order.engine.exception;

/**
 * 返利计算异常.
 * @author YRJ
 *
 */
public class RebateFilterException extends OrderException {

	private static final long serialVersionUID = 1L;

	public RebateFilterException(int errCode, String message) {
		super(errCode, message);
	}

	public RebateFilterException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

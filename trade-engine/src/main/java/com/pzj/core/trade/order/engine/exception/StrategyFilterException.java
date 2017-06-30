package com.pzj.core.trade.order.engine.exception;

/**
 * 政策过滤异常.
 * @author YRJ
 *
 */
public class StrategyFilterException extends OrderException {

	private static final long serialVersionUID = 1L;

	public StrategyFilterException(int errCode, String message) {
		super(errCode, message);
	}

	public StrategyFilterException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

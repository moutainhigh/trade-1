package com.pzj.core.trade.order.engine.exception;

/**
 * 游玩日期异常.
 * @author YRJ
 *
 */
public class PlayTimeException extends OrderException {

	private static final long serialVersionUID = 1L;

	public PlayTimeException(int errCode, String message) {
		super(errCode, message);
	}

	public PlayTimeException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

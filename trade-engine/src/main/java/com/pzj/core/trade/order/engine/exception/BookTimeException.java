package com.pzj.core.trade.order.engine.exception;

/**
 * 产品预定时间异常.
 * @author YRJ
 *
 */
public class BookTimeException extends OrderException {

	private static final long serialVersionUID = 1L;

	public BookTimeException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	public BookTimeException(int errCode, String message) {
		super(errCode, message);
	}
}

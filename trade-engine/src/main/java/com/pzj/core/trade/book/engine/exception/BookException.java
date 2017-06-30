package com.pzj.core.trade.book.engine.exception;

import com.pzj.core.trade.exception.TradeException;

public class BookException extends TradeException {

	private final static int errCode = 10040;

	/**  */
	private static final long serialVersionUID = 7410706206190418978L;

	public BookException(String message) {
		super(errCode, message);
	}

	public BookException(String message, Throwable ex) {
		super(errCode, message, ex);
	}

}

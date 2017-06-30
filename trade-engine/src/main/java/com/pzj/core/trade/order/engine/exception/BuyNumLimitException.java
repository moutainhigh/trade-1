package com.pzj.core.trade.order.engine.exception;

/**
 * 购买数量限制.
 * @author YRJ
 *
 */
public class BuyNumLimitException extends OrderException {

	private static final long serialVersionUID = 1L;

	public BuyNumLimitException(int errCode, String message) {
		super(errCode, message);
	}

	public BuyNumLimitException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

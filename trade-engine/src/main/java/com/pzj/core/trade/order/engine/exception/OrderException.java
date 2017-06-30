package com.pzj.core.trade.order.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 订单异常基类.
 * @author YRJ
 *
 */
public class OrderException extends TradeException {

	private static final long serialVersionUID = 1L;

	public OrderException(int errCode, String message) {
		super(errCode, message);
	}

	public OrderException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

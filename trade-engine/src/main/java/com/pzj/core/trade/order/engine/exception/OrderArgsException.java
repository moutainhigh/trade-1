package com.pzj.core.trade.order.engine.exception;

/**
 * 下单参数异常.
 * @author YRJ
 *
 */
public class OrderArgsException extends OrderException {

	private static final long serialVersionUID = 1L;

	public OrderArgsException(int errCode, String message) {
		super(errCode, message);
	}

}

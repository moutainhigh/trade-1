package com.pzj.core.trade.order.engine.exception;

/**
 * 订单不存在异常.
 * @author YRJ
 *
 */
public class OrderNotExistException extends OrderException {

	private static final long serialVersionUID = 1L;

	public OrderNotExistException(final int errCode, final String message) {
		super(errCode, message);
	}

}

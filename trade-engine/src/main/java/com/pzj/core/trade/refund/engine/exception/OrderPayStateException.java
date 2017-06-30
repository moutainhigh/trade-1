package com.pzj.core.trade.refund.engine.exception;

/**
 * 订单不存在.
 * @author DongChunfu
 *
 */
public class OrderPayStateException extends RefundException {

	private static final long serialVersionUID = 1L;

	public OrderPayStateException(int errCode, String message) {
		super(errCode, message);
	}

}

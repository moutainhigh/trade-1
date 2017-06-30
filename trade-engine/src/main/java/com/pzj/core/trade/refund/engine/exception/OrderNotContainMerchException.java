package com.pzj.core.trade.refund.engine.exception;

/**
 * 订单不包含商品异常.
 * @author DongChunfu
 *
 */
public class OrderNotContainMerchException extends RefundException {

	private static final long serialVersionUID = 1L;

	public OrderNotContainMerchException(int errCode, String message) {
		super(errCode, message);
	}

}

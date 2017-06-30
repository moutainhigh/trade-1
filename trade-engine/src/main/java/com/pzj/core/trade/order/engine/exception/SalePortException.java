package com.pzj.core.trade.order.engine.exception;

/**
 * 销售端口异常.
 * @author YRJ
 *
 */
public class SalePortException extends OrderException {

	private static final long serialVersionUID = 1L;

	public SalePortException(int errCode, String message) {
		super(errCode, message);
	}

}

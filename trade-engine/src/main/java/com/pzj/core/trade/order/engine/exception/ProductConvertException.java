package com.pzj.core.trade.order.engine.exception;

/**
 * 产品转换异常.
 * @author YRJ
 *
 */
public class ProductConvertException extends OrderException {

	private static final long serialVersionUID = 1L;

	public ProductConvertException(int errCode, String message) {
		super(errCode, message);
	}

	public ProductConvertException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

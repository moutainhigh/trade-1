package com.pzj.core.trade.refund.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 退款异常基类.
 * @author YRJ
 *
 */
public class RefundException extends TradeException{
	

	public RefundException(int errCode, String message) {
		super(errCode, message);
	}
	
	public RefundException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

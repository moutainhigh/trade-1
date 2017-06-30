package com.pzj.core.trade.payment.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 支付异常基类
 *
 * @author DongChunfu
 * @date 2017年1月4日
 */
public class PayException extends TradeException {
	private static final long serialVersionUID = 1L;

	public PayException(final int errCode, final String message) {
		super(errCode, message);
	}

	public PayException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}

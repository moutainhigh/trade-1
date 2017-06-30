package com.pzj.core.trade.payment.engine.exception;

/**
 * 支付账户冻结异常
 *
 * @author DongChunfu
 * @date 2017年1月4日
 */
public class PayAccountFrozenErrorException extends PayException {

	public PayAccountFrozenErrorException(final int errCode, final String message) {
		super(errCode, message);
	}

	public PayAccountFrozenErrorException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

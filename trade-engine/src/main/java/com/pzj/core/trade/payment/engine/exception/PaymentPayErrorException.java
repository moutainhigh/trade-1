package com.pzj.core.trade.payment.engine.exception;

/**
 * 支付状态不可支付
 *
 * @author DongChunfu
 * @date 2017年1月4日
 */
public class PaymentPayErrorException extends PayException {

	public PaymentPayErrorException(final int errCode, final String message) {
		super(errCode, message);
	}

	public PaymentPayErrorException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

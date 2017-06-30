package com.pzj.core.trade.payment.engine.exception;

/**
 * 支付请求参数错误
 *
 * @author DongChunfu
 * @date 2017年1月4日
 */
public class PayReqParamErrorException extends PayException {

	public PayReqParamErrorException(final int errCode, final String message) {
		super(errCode, message);
	}

	public PayReqParamErrorException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

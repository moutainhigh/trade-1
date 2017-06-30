package com.pzj.core.trade.confirm.exception;

/**
 * 魔方码验证异常.
 * @author YRJ
 *
 */
public class ConfirmCodeException extends ConfirmException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param errCode
	 * @param message
	 */
	public ConfirmCodeException(final int errCode, final String message) {
		super(errCode, message);
	}

	public ConfirmCodeException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}

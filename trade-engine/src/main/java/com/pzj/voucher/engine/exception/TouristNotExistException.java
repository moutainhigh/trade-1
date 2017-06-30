package com.pzj.voucher.engine.exception;

/**
 * 游客不存在.
 * @author YRJ
 *
 */
public class TouristNotExistException extends VoucherException {

	private static final long serialVersionUID = 1L;

	public TouristNotExistException(final int errCode, final String message) {
		super(errCode, message);
	}

	public TouristNotExistException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

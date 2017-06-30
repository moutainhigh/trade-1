package com.pzj.voucher.engine.exception;

public class IllegalTouristException extends VoucherException {

	private static final long serialVersionUID = 1L;

	public IllegalTouristException(final int errCode, final String message) {
		super(errCode, message);
	}

	public IllegalTouristException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}

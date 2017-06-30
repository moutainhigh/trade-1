package com.pzj.voucher.engine.exception;

/**
 * 游客信息修改入參模型异常.
 * @author YRJ
 *
 */
public class TouristInModelException extends VoucherException {

	private static final long serialVersionUID = 1L;

	public TouristInModelException(final int errCode, final String message) {
		super(errCode, message);
	}

	public TouristInModelException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}

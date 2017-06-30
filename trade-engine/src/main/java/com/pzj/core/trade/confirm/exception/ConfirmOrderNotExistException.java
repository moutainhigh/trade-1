package com.pzj.core.trade.confirm.exception;

/**
 * 核销订单不存在
 *
 * @author DongChunfu
 * @version $Id: ConfirmOrderNotExistException.java, v 0.1 2017年2月28日 上午10:54:32 DongChunfu Exp $
 */
public class ConfirmOrderNotExistException extends ConfirmException {

	public ConfirmOrderNotExistException(final int errCode, final String message) {
		super(errCode, message);
	}

	public ConfirmOrderNotExistException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

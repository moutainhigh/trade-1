package com.pzj.core.trade.confirm.exception;

/**
 * 分账异常
 *
 * @author DongChunfu
 * @version $Id: TransferAccountException.java, v 0.1 2017年4月8日 上午11:56:06 DongChunfu Exp $
 */
public class TransferAccountException extends ConfirmException {

	public TransferAccountException(final int errCode, final String message) {
		super(errCode, message);
	}

	public TransferAccountException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

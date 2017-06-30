package com.pzj.core.trade.confirm.exception;

/**
 * 检票点异常
 *
 * @author DongChunfu
 * @version $Id: TicketPointException.java, v 0.1 2017年3月3日 下午5:44:51 Administrator Exp $
 */
public class TicketPointException extends ConfirmException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param errCode
	 * @param message
	 */
	public TicketPointException(final int errCode, final String message) {
		super(errCode, message);
	}

	public TicketPointException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

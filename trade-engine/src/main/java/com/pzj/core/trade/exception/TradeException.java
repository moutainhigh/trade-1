package com.pzj.core.trade.exception;

import com.pzj.framework.exception.ServiceException;

/**
 * 交易服务异常基类.
 * 
 * @author YRJ
 *
 */
public class TradeException extends ServiceException {

	private int errCode;

	public TradeException(final int errCode, final String message) {
		this(errCode, message, null);
	}

	public TradeException(int errCode, final String message, final Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
		illegalErrorCode();
	}

	public int getErrCode() {
		return errCode;
	}

	protected final void illegalErrorCode() {
		if (errCode < 10001 || errCode > 19999) {
			throw new ServiceException("交易服务错误码不符合要求.");
		}
	}

	private static final long serialVersionUID = 1L;

}

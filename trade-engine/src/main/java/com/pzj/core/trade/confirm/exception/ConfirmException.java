package com.pzj.core.trade.confirm.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 核销服务异常基类
 *
 * @author DongChunfu
 * @version $Id: ConfirmException.java, v 0.1 2017年2月27日 下午5:23:29 DongChunfu Exp $
 */
public class ConfirmException extends TradeException {

	public ConfirmException(final int errCode, final String message) {
		super(errCode, message);
	}

	public ConfirmException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

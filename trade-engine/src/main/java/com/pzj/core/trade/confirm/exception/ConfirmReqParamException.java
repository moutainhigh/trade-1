package com.pzj.core.trade.confirm.exception;

/**
 * 核销请求参数错误
 *
 * @author DongChunfu
 * @version $Id: ConfirmReqParamException.java, v 0.1 2017年2月27日 下午5:23:17 DongChunfu Exp $
 */
public class ConfirmReqParamException extends ConfirmException {

	public ConfirmReqParamException(final int errCode, final String message) {
		super(errCode, message);
	}

	public ConfirmReqParamException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

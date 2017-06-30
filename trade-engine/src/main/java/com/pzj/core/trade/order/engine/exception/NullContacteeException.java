package com.pzj.core.trade.order.engine.exception;

/**
 * 联系人信息异常.
 * @author YRJ
 *
 */
public class NullContacteeException extends OrderException {

	private static final long serialVersionUID = 1L;

	public NullContacteeException(int errCode, String message) {
		super(errCode, message);
	}

}

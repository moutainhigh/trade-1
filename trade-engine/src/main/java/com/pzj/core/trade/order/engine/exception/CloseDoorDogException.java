package com.pzj.core.trade.order.engine.exception;

/**
 * 关门狗异常.
 * @author YRJ
 *
 */
public class CloseDoorDogException extends OrderException {

	private static final long serialVersionUID = 1L;

	public CloseDoorDogException(int errCode, String message) {
		super(errCode, message);
	}

	public CloseDoorDogException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}
}

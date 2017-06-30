package com.pzj.core.trade.ack.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 二次确认异常基类.
 * @author YRJ
 *
 */
public class AckException extends TradeException {

	private static final long serialVersionUID = 1L;

	public AckException(int errCode, String message) {
		super(errCode, message);
	}

}

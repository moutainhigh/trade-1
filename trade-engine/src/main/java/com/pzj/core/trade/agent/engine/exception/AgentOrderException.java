package com.pzj.core.trade.agent.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 代下单异常基类.
 * @author YRJ
 *
 */
public class AgentOrderException extends TradeException {

	private static final long serialVersionUID = 1L;

	public AgentOrderException(int errCode, String message) {
		super(errCode, message);
	}

}

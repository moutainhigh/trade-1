package com.pzj.core.trade.ack.engine.exception;

/**
 * 代下单订单号不存在.
 * @author YRJ
 *
 */
public class AgentOrderNotExistException extends AckException {

	private static final long serialVersionUID = 1L;

	public AgentOrderNotExistException(int errCode, String message) {
		super(errCode, message);
	}

}

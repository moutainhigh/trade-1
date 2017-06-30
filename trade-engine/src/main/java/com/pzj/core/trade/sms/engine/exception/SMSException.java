package com.pzj.core.trade.sms.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 发送短信异常
 * @author Administrator
 *
 */
public class SMSException extends TradeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517385834943962469L;

	public SMSException(int errCode, String message) {
		super(errCode, message);
	}

}

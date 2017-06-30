package com.pzj.core.trade.clean.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 交易结算信息异常
 * @author kangzl
 *
 */
public class MerchCleanException extends TradeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MerchCleanException(int errCode, String message) {
		super(errCode, message);
	}
}

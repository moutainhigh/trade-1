package com.pzj.core.trade.integration.engine.exception;

import com.pzj.core.trade.exception.TradeException;

public class TransferredBaseException extends TradeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7540814852455949979L;

	public TransferredBaseException(int errCode, String message) {
		super(errCode, message);
	}

}

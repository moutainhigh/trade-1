package com.pzj.trade.withdraw.exception;

import com.pzj.core.trade.exception.TradeException;

public class WithdrawException extends TradeException {

	private static final long serialVersionUID = 1645042298993516100L;

	public WithdrawException(int errCode, String message) {
		super(errCode, message);
	}
}

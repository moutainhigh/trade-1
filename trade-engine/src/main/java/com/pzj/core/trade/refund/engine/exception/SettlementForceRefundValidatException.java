package com.pzj.core.trade.refund.engine.exception;

/**
 * 清结算强制退款验证异常
 * @author DongChunfu
 *
 */
public class SettlementForceRefundValidatException extends RefundException {

	private static final long serialVersionUID = 1L;

	public SettlementForceRefundValidatException(int errCode, String message) {
		super(errCode, message);
	}

}

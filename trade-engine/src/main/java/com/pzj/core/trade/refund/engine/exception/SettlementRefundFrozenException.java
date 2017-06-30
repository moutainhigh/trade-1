package com.pzj.core.trade.refund.engine.exception;

/**
 * 清结算退款冻结异常
 * @author DongChunfu
 *
 */
public class SettlementRefundFrozenException extends RefundException {

	private static final long serialVersionUID = 1L;

	public SettlementRefundFrozenException(int errCode, String message) {
		super(errCode, message);
	}

}

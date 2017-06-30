package com.pzj.core.trade.refund.engine.exception;

/**
 * 清结算强制退款回调异常
 * 
 * @author DongChunfu
 * @date 2016年12月22日
 */
public class SettlementForceRefundCallbackException extends RefundException {

	private static final long serialVersionUID = 1L;

	public SettlementForceRefundCallbackException(final int errCode, final String message) {
		super(errCode, message);
	}

	public SettlementForceRefundCallbackException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

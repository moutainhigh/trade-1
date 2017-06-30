package com.pzj.core.trade.refund.engine.exception;

/**
 * 查询产品退款规则异常
 * @author DongChunfu
 *
 */
public class QueryRefundRuleException extends RefundException {

	private static final long serialVersionUID = 1L;

	public QueryRefundRuleException(final int errCode, final String message) {
		super(errCode, message);
	}

	public QueryRefundRuleException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

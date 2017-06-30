package com.pzj.core.trade.refund.engine.exception;

/**
 * 产品退款规则不存在异常
 * @author DongChunfu
 *
 */
public class RefundRuleNotFoundException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundRuleNotFoundException(int errCode, String message) {
		super(errCode, message);
	}

}

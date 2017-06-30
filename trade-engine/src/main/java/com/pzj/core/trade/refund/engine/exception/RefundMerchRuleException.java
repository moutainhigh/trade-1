package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款订单异常.
 * @author DongChunfu
 *
 */
public class RefundMerchRuleException extends RefundException{

	public RefundMerchRuleException(int errCode, String message) {
		super(errCode, message);
	}
	
	public RefundMerchRuleException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

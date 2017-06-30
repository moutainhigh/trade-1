package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款解决回滚异常
 * 
 * @author DongChunfu
 * @date 2016年12月16日
 */
public class RefundRollbackException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundRollbackException(final int errCode, final String message) {
		super(errCode, message);
	}

}

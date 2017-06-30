package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款申请操作类型异常.
 * @author YRJ
 *
 */
public class RefundApplyTypeException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundApplyTypeException(int errCode, String message) {
		super(errCode, message);
	}

}

package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款申请数量异常
 * @author YRJ
 *
 */
public class RefundMerchCalculateException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundMerchCalculateException(int errCode, String message) {
		super(errCode, message);
	}

}

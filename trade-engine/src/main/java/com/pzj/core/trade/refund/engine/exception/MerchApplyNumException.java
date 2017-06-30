package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款申请数量异常
 * @author YRJ
 *
 */
public class MerchApplyNumException extends RefundException {

	private static final long serialVersionUID = 1L;

	public MerchApplyNumException(int errCode, String message) {
		super(errCode, message);
	}

}

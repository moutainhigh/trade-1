package com.pzj.core.trade.refund.engine.exception;

/**
 * 商品状态错误异常.
 * @author YRJ
 *
 */
public class MerchStateException extends RefundException {

	private static final long serialVersionUID = 1L;

	public MerchStateException(int errCode, String message) {
		super(errCode, message);
	}

}

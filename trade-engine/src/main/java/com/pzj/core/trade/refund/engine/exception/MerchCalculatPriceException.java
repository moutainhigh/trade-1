package com.pzj.core.trade.refund.engine.exception;

/**
 * 商品状态错误异常.
 * @author YRJ
 *
 */
public class MerchCalculatPriceException extends RefundException {

	private static final long serialVersionUID = 1L;

	public MerchCalculatPriceException(int errCode, String message) {
		super(errCode, message);
	}

}

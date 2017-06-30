package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款商品不存在异常.
 * @author YRJ
 *
 */
public class RefundMerchNotFoundException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundMerchNotFoundException(int errCode, String message) {
		super(errCode, message);
	}

}

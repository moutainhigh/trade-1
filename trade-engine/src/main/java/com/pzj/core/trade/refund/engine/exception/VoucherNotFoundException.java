package com.pzj.core.trade.refund.engine.exception;

/**
 * 商品核销凭证异常
 * @author DongChunfu
 *
 */
public class VoucherNotFoundException extends RefundException {

	private static final long serialVersionUID = 1L;

	public VoucherNotFoundException(int errCode, String message) {
		super(errCode, message);
	}

}

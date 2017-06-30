package com.pzj.voucher.engine.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 核销异常基类
 *
 * @author DongChunfu
 * @date 2017年1月7日
 */
public class VoucherException extends TradeException {

	private static final long serialVersionUID = 1L;

	public VoucherException(final int errCode, final String message) {
		super(errCode, message);
	}

	public VoucherException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

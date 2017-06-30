package com.pzj.core.trade.refund.engine.exception;

/**
 * 获取产品信息失败异常.
 * @author DongChunfu
 *
 */
public class GetProductInfoFailException extends RefundException {

	private static final long serialVersionUID = 1L;

	public GetProductInfoFailException(int errCode, String message) {
		super(errCode, message);
	}

}

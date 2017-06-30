package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款流水为空异常
 * 
 * @author DongChunfu
 * @date 2016年12月18日
 */
public class RefundFlowIsNoneException extends RefundException {

	private static final long serialVersionUID = -9221775863696335062L;

	public RefundFlowIsNoneException(final int errCode, final String message) {
		super(errCode, message);
	}

}

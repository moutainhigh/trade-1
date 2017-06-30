package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款流水构建失败.
 * @author DongChunfu
 *
 */
public class RefundFlowBuildException extends RefundException {

	private static final long serialVersionUID = 1L;

	public RefundFlowBuildException(int errCode, String message) {
		super(errCode, message);
	}

}

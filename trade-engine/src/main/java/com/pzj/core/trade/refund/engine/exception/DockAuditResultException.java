package com.pzj.core.trade.refund.engine.exception;

/**
 * 对接审核结果异常.
 * @author DongChunfu
 *
 */
public class DockAuditResultException extends RefundException{

	public DockAuditResultException(int errCode, String message) {
		super(errCode, message);
	}
	
	public DockAuditResultException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	private static final long serialVersionUID = 1L;

}

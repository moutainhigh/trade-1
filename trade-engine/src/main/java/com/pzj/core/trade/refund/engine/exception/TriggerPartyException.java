package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款发起方异常.
 *
 * @author DongChunfu
 * @date 2016年12月8日
 */
public class TriggerPartyException extends RefundException {

	private static final long serialVersionUID = 1L;

	public TriggerPartyException(int errCode, String message) {
		super(errCode, message);
	}

}

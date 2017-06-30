/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.exception;

/**
 * 退款的商品不可以核销
 *
 * @author DongChunfu
 * @version $Id: RefundedCanNotConfirmException.java, v 0.1 2017年3月3日 下午5:01:59 Administrator Exp $
 */
public class RefundedCanNotConfirmException extends ConfirmException {

	private static final long serialVersionUID = -5935560187249945666L;

	public RefundedCanNotConfirmException(final int errCode, final String message) {
		super(errCode, message);
	}

	public RefundedCanNotConfirmException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

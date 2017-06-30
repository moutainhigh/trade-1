/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.exception;

/**
 * 凭证不存在异常
 *
 * @author DongChunfu
 * @version $Id: VoucherIsNotExitException.java, v 0.1 2017年3月3日 下午4:35:22 Administrator Exp $
 */
public class VoucherIsNotExitException extends ConfirmException {

	private static final long serialVersionUID = -5935560187249945666L;

	/**
	 * @param errCode
	 * @param message
	 */
	public VoucherIsNotExitException(final int errCode, final String message) {
		super(errCode, message);
	}

	public VoucherIsNotExitException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.exception;

/**
 * 逾期的的凭证不可以核销
 *
 * @author DongChunfu
 * @version $Id: ExpireVoucherCanNotConfirmException.java, v 0.1 2017年3月3日 下午5:23:53 Administrator Exp $
 */
public class VoucherConfirmException extends ConfirmException {

	private static final long serialVersionUID = -1748297212041265595L;

	public VoucherConfirmException(final int errCode, final String message) {
		super(errCode, message);
	}

	public VoucherConfirmException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

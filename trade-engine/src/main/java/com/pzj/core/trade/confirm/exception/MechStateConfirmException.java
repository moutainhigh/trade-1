/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.exception;

/**
 * 商品状态核销异常
 *
 * @author DongChunfu
 * @version $Id: MechStateConfirmException.java, v 0.1 2017年3月22日 下午5:44:32 DongChunfu Exp $
 */
public class MechStateConfirmException extends ConfirmException {

	private static final long serialVersionUID = -5935560187249945666L;

	/**
	 * @param errCode
	 * @param message
	 */
	public MechStateConfirmException(final int errCode, final String message) {
		super(errCode, message);
	}

	public MechStateConfirmException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}
}

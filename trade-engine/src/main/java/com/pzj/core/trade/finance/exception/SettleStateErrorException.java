/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.exception;

/**
 * 结算状态错误异常
 * @author DongChunfu
 * @version $Id: SettleStateErrorException.java, v 0.1 2017年5月18日 上午9:41:31 DongChunfu Exp $
 */
public class SettleStateErrorException extends FinanceErrorException {

	private static final long serialVersionUID = 8772817825085547686L;

	/**
	 * @param errCode
	 * @param message
	 */
	public SettleStateErrorException(final int errCode, final String message) {
		super(errCode, message);
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.exception;

import com.pzj.core.trade.exception.TradeException;

/**
 * 财务中心错误
 *
 * @author DongChunfu
 * @version $Id: FinanceErrorException.java, v 0.1 2017年5月18日 上午9:40:47 DongChunfu Exp $
 */
public class FinanceErrorException extends TradeException {

	private static final long serialVersionUID = 4831157400488383442L;

	public FinanceErrorException(final int errCode, final String message) {
		super(errCode, message);
	}

	public FinanceErrorException(final int errCode, final String message, final Throwable cause) {
		super(errCode, message, cause);
	}

}
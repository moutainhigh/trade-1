/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.exception;

/**
 * 冻结预清算订单异常
 * 
 * @author DongChunfu
 * @date 2016年12月14日
 */
public class FrozenCleanFlowFailException extends RefundException {

	private static final long serialVersionUID = -9221775863696335062L;

	public FrozenCleanFlowFailException(final int errCode, final String message) {
		super(errCode, message);
	}

}

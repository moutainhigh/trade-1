/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.exception;

/**
 * 部分退款异常
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
public class PartRefundException extends RefundException {

	private static final long serialVersionUID = -9221775863696335062L;

	public PartRefundException(int errCode, String message) {
		super(errCode, message);
	}


}

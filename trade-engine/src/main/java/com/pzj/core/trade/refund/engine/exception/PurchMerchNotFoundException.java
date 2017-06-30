/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.exception;

/**
 * 支付系统退款失败
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
public class PurchMerchNotFoundException extends RefundException {

	private static final long serialVersionUID = -9221775863696335062L;

	public PurchMerchNotFoundException(final int errCode, final String message) {
		super(errCode, message);
	}

}

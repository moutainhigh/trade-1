/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款审核结果不存在
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
public class RefundAuditPartyException extends RefundException {

	private static final long serialVersionUID = -9221775863696335062L;

	public RefundAuditPartyException(int errCode, String message) {
		super(errCode, message);
	}


}

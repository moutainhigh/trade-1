/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.exception;

/**
 * 退款发起方不存在
 * 
 * @author DongChunfu
 * @version $Id: RefundInitPartyException.java, v 0.1 2016年11月30日 上午10:36:12 dongchunfu Exp $
 */
public class RefundInitPartyException extends RefundException {

	private static final long serialVersionUID = -9221775863696335062L;

	public RefundInitPartyException(int errCode, String message) {
		super(errCode, message);
	}


}

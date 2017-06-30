/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.common;

/**
 * 核销版本
 *
 * @author DongChunfu
 * @version $Id: ConfirmVersionEnum.java, v 0.1 2017年3月23日 下午4:26:35 DongChunfu Exp $
 */
public enum ConfirmVersionEnum {
	OLD(0), NEW(1);
	private int version;

	private ConfirmVersionEnum(final int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

}

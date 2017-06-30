/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.common;

/**
 * 自动核销枚举
 *
 * @author DongChunfu
 * @version $Id: AutoConfirmEnum.java, v 0.1 2017年3月23日 下午4:58:47 DongChunfu Exp $
 */
public enum AutoConfirmEnum {
	/**手动的*/
	MANUAL(0),
	/**自动的*/
	AUTOMATIC(1);

	private int type;

	public int getType() {
		return type;
	}

	private AutoConfirmEnum(final int confirmType) {
		this.type = confirmType;
	}

}

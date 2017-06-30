/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.common;

import com.pzj.core.trade.finance.exception.FinanceErrorCode;
import com.pzj.core.trade.finance.exception.SettleStateErrorException;

/**
 * 结算状态枚举
 *
 * @author DongChunfu
 * @version $Id: SettleStateEnum.java, v 0.1 2017年5月15日 上午11:07:42 DongChunfu Exp $
 */
public enum SettleWayEnum {
	OFFLINE(0, "线下"), ONLINE(1, "线上");

	private int way;
	private String desc;

	private SettleWayEnum(final int way, final String desc) {
		this.way = way;
		this.desc = desc;
	}

	public int getWay() {
		return way;
	}

	public String getDesc() {
		return desc;
	}

	public static SettleWayEnum getSettleWayEnumByWay(final int way) {
		for (final SettleWayEnum settleWay : SettleWayEnum.values()) {
			if (settleWay.getWay() == way) {
				return settleWay;
			}
		}
		throw new SettleStateErrorException(FinanceErrorCode.FINANCE_SETTLE_STATE_ERROR_CODE, "财务中心,结算状态错误");
	}
}

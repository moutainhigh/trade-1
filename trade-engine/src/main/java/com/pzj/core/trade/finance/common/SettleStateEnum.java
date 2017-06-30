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
public enum SettleStateEnum {
	YET_SETTLE(0, "待结算"), WAIT_SETTL(1, "已结算");

	private int state;
	private String desc;

	private SettleStateEnum(final int state, final String desc) {
		this.state = state;
		this.desc = desc;
	}

	public int getState() {
		return state;
	}

	public String getDesc() {
		return desc;
	}

	public static SettleStateEnum getSettleStateEnumByState(final int state) {
		for (final SettleStateEnum settleState : SettleStateEnum.values()) {
			if (settleState.getState() == state) {
				return settleState;
			}
		}
		throw new SettleStateErrorException(FinanceErrorCode.FINANCE_SETTLE_STATE_ERROR_CODE, "财务中心,结算状态错误");
	}
}

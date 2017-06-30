package com.pzj.trade.book.common;

import com.pzj.framework.toolkit.Check;

public enum SparpreisOrderRuleTEnum {
	ORDER_BY_ASC(1, "顺序"), ORDER_BY_DESC(2, "倒序");

	private int type;
	private String msg;

	private SparpreisOrderRuleTEnum(int type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

	public static boolean isValidOrderType(int orderType) {
		for (SparpreisOrderRuleTEnum type : SparpreisOrderRuleTEnum.values()) {
			if (type.getType() == orderType) {
				return true;
			}
		}
		return false;
	}

	public static SparpreisOrderRuleTEnum getOrderType(Integer orderType) {
		if (Check.NuNObject(orderType)) {
			return null;
		}
		for (SparpreisOrderRuleTEnum type : SparpreisOrderRuleTEnum.values()) {
			if (type.getType() == orderType) {
				return type;
			}
		}
		return null;
	}
}

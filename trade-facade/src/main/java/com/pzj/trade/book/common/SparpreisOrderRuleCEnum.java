package com.pzj.trade.book.common;

import com.pzj.framework.toolkit.Check;

public enum SparpreisOrderRuleCEnum {
	ORDER_BY_TRAVELDATE(1, "按照游玩时间排序"), ORDER_BY_BOOKDATE(2, "按照创建时间排序");

	private int column;
	private String msg;

	private SparpreisOrderRuleCEnum(int column, String msg) {
		this.column = column;
		this.msg = msg;
	}

	public int getColumn() {
		return column;
	}

	public String getMsg() {
		return msg;
	}

	public static boolean isValidOrderColumn(int orderColumn) {
		for (SparpreisOrderRuleCEnum column : SparpreisOrderRuleCEnum.values()) {
			if (column.getColumn() == orderColumn) {
				return true;
			}
		}
		return false;
	}

	public static SparpreisOrderRuleCEnum getOrderColumn(Integer orderColumn) {
		if (Check.NuNObject(orderColumn)) {
			return null;
		}
		for (SparpreisOrderRuleCEnum column : SparpreisOrderRuleCEnum.values()) {
			if (column.getColumn() == orderColumn) {
				return column;
			}
		}
		return null;
	}
}

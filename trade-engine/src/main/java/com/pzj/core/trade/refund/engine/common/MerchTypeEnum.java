package com.pzj.core.trade.refund.engine.common;

/**
 * 商品类型枚举
 *
 * @author DongChunfu
 * @date 2016年12月20日
 */
public enum MerchTypeEnum {
	SELL(1, "销售商品"), PURCHASE(2, "采购商品");

	private int type;
	private String note;

	private MerchTypeEnum(final int type, final String note) {
		this.type = type;
		this.note = note;
	}

	public int getType() {
		return type;
	}

	public String getNote() {
		return note;
	}

}

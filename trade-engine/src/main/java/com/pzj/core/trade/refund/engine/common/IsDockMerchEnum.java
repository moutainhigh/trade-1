/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.common;

/**
 * 是否为对接商品
 *
 * @author DongChunfu
 * @date 2016年12月9日
 */
public enum IsDockMerchEnum {
	NO_DOCK(0, "不是"), IS_DOCK(1, "对接");

	private int dock;
	private String note;

	private IsDockMerchEnum(int dock, String note) {
		this.dock = dock;
		this.note = note;
	}

	public int getDock() {
		return dock;
	}

	public String getNote() {
		return note;
	}

}

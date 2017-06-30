package com.pzj.core.trade.book.engine.model;

public enum BookOperateEnum {
	ADD_BOOK(1, "添加预约单"), UPDATE_BOOK(2, "更新预约单"), ADD_PREORDER(3, "添加前置订单"), UPDATE_PREORDER(4,
			"更新前置订单"), CANCEL_BOOK(5, "取消预约单"), CANCEL_PREORDER(6, "取消前置订单");

	private int operate;

	private String msg;

	private BookOperateEnum(int operate, String msg) {
		this.operate = operate;
		this.msg = msg;
	}

	public int getOperate() {
		return operate;
	}

	public String getMsg() {
		return msg;
	}

}

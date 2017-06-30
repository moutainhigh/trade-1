package com.pzj.core.trade.log.common;

public enum LogEventEnum {
	BOOK_CREATE("book_create", "预约单创建"), BOOK_UPDATE("book_update", "预约单更新"), BOOK_AUDIT("book_audit",
			"预约单审核通过"), BOOK_REFUSE("book_refuse", "预约单审核拒绝"), BOOK_CANCEL("book_cancel", "预约单取消");
	private String event;
	private String msg;

	public String getEvent() {
		return event;
	}

	public String getMsg() {
		return msg;
	}

	private LogEventEnum(String event, String msg) {
		this.event = event;
		this.msg = msg;
	}

}

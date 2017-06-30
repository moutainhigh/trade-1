package com.pzj.trade.book.common;

public enum BookStatusEnum {

	CANCELED(0, "已取消"), BOOKING(1, "待出票"), DRAWNED(2, "已出票"), AUDITING(3, "待审核"), REFUSED(4, "已拒绝"), INITIAL(-1, "初始");

	private int status;
	private String msg;

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * @param type
	 * @param msg
	 */
	private BookStatusEnum(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public static String getMsg(int status) {
		for (BookStatusEnum book : BookStatusEnum.values()) {
			if (status == book.getStatus()) {
				return book.getMsg();
			}
		}
		return null;
	}

	public static boolean isValidStatus(int bookStatus) {
		for (BookStatusEnum type : BookStatusEnum.values()) {
			if (type.getStatus() == bookStatus) {
				return true;
			}
		}
		return false;
	}
}

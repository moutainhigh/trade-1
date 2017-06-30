/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.book.common;

/**
 * 
 * @author Administrator
 * @version $Id: BookTypeEnum.java, v 0.1 2017年3月8日 上午10:43:48 Administrator Exp $
 */
public enum BookTypeEnum {
	NORMAL(0, "普通订单"), BOOK(1, "预约"), CHEAP_TICKET(2, "特价票"), FREE_TICKET(3, "免票"), BOOK_PRE_ORDER(4, "预约前置订单");

	private int type;
	private String msg;

	public int getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * @param type
	 * @param msg
	 */
	private BookTypeEnum(int type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public static boolean isValidType(int bookType) {
		for (BookTypeEnum type : BookTypeEnum.values()) {
			if (type.getType() == bookType) {
				return true;
			}
		}
		return false;
	}

}

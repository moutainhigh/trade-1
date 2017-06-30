package com.pzj.trade.order.entity;

/**
 * 订单类型的枚举
 * @author Administrator
 * @version $Id: OrderTypeEnum.java, v 0.1 2016年8月24日 下午3:50:10 Administrator Exp $
 */
public enum OrderTypeEnum {
	mfourToSupplierOrder(1, "采购订单"), resellerToMfourOrder(2, "销售订单");
	private OrderTypeEnum(int key, String msg) {
		this.key = key;
		this.msg = msg;
	}

	private int key;
	private String msg;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

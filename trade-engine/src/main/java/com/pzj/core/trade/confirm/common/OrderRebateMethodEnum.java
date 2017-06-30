package com.pzj.core.trade.confirm.common;

/**
 * 订单返利方式
 *
 * @author DongChunfu
 * @version $Id: OrderRebateMethodEnum.java, v 0.1 2017年4月8日 下午12:10:18 DongChunfu Exp $
 */
public enum OrderRebateMethodEnum {

	ALL(0, "所有"), BEFORE(1, "前返"), AFTER(2, "后返");

	private int method;
	private String desc;

	public int getMethod() {
		return method;
	}

	public String getDesc() {
		return desc;
	}

	private OrderRebateMethodEnum(final int method, final String desc) {
		this.method = method;
		this.desc = desc;
	}

}

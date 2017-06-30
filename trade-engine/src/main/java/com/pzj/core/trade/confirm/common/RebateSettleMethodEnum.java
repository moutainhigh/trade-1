package com.pzj.core.trade.confirm.common;

/**
 * 订单返利结算方式
 *
 * @author DongChunfu
 * @version $Id: RebateSettleMethodEnum.java, v 0.1 2017年4月8日 下午12:15:27 DongChunfu Exp $
 */
public enum RebateSettleMethodEnum {

	IMMEDIATE(1, "立返"), PERIOD(2, "周期返");

	private int method;
	private String desc;

	public int getMethod() {
		return method;
	}

	public String getDesc() {
		return desc;
	}

	private RebateSettleMethodEnum(final int method, final String desc) {
		this.method = method;
		this.desc = desc;
	}

}

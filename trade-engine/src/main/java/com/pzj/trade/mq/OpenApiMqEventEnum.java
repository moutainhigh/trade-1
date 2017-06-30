/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.mq;

/**
 * OpenApi 交易操作事件
 *
 * @author DongChunfu
 * @version $Id: OpenApiMqEventEnum.java, v 0.1 2017年3月22日 上午9:47:06 Administrator Exp $
 */
public enum OpenApiMqEventEnum {
	// 订单确认结果
	CREATE_ORDER("create_order", "订单确认结果"),
	// 整单核销完毕
	CONFIRM_ORDER("confirm_order", "整单核销完毕"),
	// 退款审核通过
	REFUND_AUDIT("refund_audit", "退款审核通过");

	/**事件*/
	private String event;
	/**事件描述*/
	private String desc;

	private OpenApiMqEventEnum(final String event, final String desc) {
		this.event = event;
		this.desc = desc;
	}

	public String getEvent() {
		return event;
	}

	public String getDesc() {
		return desc;
	}

}

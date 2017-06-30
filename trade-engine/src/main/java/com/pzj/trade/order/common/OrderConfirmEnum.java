package com.pzj.trade.order.common;

import com.pzj.core.trade.ack.engine.exception.AckException;

/**
 * 订单二次确认类型.
 * @author YRJ
 *
 */
public enum OrderConfirmEnum {

	/**
	 * 未确认.
	 */
	CONFIRMABLE(2, true),
	/**
	 * 无需确认.
	 */
	UNCONFIRM(1, false),
	/**
	 * 已确认.
	 */
	CONFIRMED(3, false);

	private int value;

	public int getValue() {
		return value;
	}

	/**
	 * 是否可二次确认.
	 */
	private boolean able;

	public boolean isAble() {
		return able;
	}

	private OrderConfirmEnum(int value, boolean able) {
		this.value = value;
		this.able = able;
	}

	/**
	 * 获取订单二次确认对象.
	 * @param value
	 * @return
	 */
	public static final OrderConfirmEnum getOrderConfirm(int value) {
		for (OrderConfirmEnum confirm : OrderConfirmEnum.values()) {
			if (confirm.getValue() == value) {
				return confirm;
			}
		}
		throw new AckException(10400, "订单二次确认数据错误.");
	}
}

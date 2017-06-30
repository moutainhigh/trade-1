package com.pzj.trade.order.common;

/**
 * 订单类型的枚举
 * @author Administrator
 * @version $Id: OrderTypeEnum.java, v 0.1 2016年8月24日 下午3:50:10 Administrator Exp $
 */
public enum StrategyTypeEnum {
	DIRECT(1, "直签"), MF(2, "魔方分销");
	private int type;
	private String message;

	private StrategyTypeEnum(int type, String message) {
		this.type = type;
		this.message = message;
	}

	/**
	 * Getter method for property <tt>type</tt>.
	 * 
	 * @return property value of type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Getter method for property <tt>message</tt>.
	 * 
	 * @return property value of message
	 */
	public String getMessage() {
		return message;
	}

}

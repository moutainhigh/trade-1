package com.pzj.core.trade.refund.engine.common;

/**
 * 退款中状态值.
 * @author YRJ
 *
 */
public class RefundingEnum {

	/**
	 * 退款中.
	 */
	public final static RefundingEnum REFUNDING = new RefundingEnum(1);
	public final static RefundingEnum NOTREFUNDING = new RefundingEnum(0);

	private int value;

	private RefundingEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

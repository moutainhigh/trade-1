package com.pzj.core.trade.confirm.common;

/**
 * 分账订单状态
 *
 * @author DongChunfu
 * @version $Id: TransferAccountOrderStateEnum.java, v 0.1 2017年4月8日 下午12:06:45 DongChunfu Exp $
 */
public enum TransferAccountOrderStateEnum {

	PAIED(1, "已支付"), CONFIRMED(2, "已核销"), REFUNDING(3, "退款中"), REFUNDED(4, "已退款");

	private int state;
	private String desc;

	public int getState() {
		return state;
	}

	public String getDesc() {
		return desc;
	}

	private TransferAccountOrderStateEnum(final int state, final String desc) {
		this.state = state;
		this.desc = desc;
	}

}

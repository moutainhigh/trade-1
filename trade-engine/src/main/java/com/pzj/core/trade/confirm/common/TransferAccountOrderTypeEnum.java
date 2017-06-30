
package com.pzj.core.trade.confirm.common;

/**
 * 分账订单类型
 *
 * @author DongChunfu
 * @version $Id: TransferAccountOrderTypeEnum.java, v 0.1 2017年4月8日 下午12:01:02 DongChunfu Exp $
 */
public enum TransferAccountOrderTypeEnum {

	/**SAAS订单*/
	SAAS_ORDER(1, "SAAS订单"),
	/**退款单*/
	REFUND_ORDER(2, "退款单");

	private int type;
	private String desc;

	public int getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	private TransferAccountOrderTypeEnum(final int type, final String desc) {
		this.type = type;
		this.desc = desc;
	}

}

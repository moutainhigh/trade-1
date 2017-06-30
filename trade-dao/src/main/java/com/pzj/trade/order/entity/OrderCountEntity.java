package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderCountEntity implements Serializable {

	private static final long serialVersionUID = 4420770586224680188L;
	/**订单总数量*/
	private int order_num;
	/**商品总数量*/
	private int merch_num;

	/**总金额 */
	private BigDecimal amount = BigDecimal.valueOf(0D);

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public int getMerch_num() {
		return merch_num;
	}

	public void setMerch_num(int merch_num) {
		this.merch_num = merch_num;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}

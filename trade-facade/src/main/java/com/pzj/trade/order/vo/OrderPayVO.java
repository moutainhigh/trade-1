/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.pzj.framework.toolkit.Check;

/**
 * 订单支付金额
 *
 * @author DongChunfu
 * @version $Id: OrderPayVO.java, v 0.1 2017年4月27日 下午5:15:41 DongChunfu Exp $
 */
public class OrderPayVO implements Serializable {
	private static final long serialVersionUID = 4053840192688107791L;

	/**
	 * 订单支付金额
	 * sasa订单为正数；
	 * 退款订单为负数；
	 */
	private Double orderPayAmount;
	/**
	 * 订单支付方式
	 * 0:纯余额;
	 * 1:支付宝;
	 * 2:微信;
	 * 4:混合支付;
	 * 5:现金;
	 * 6:后付;
	 * 7:货款支付.
	 */
	private Integer orderPayWay;

	public OrderPayVO() {
		super();
	}

	private Double precisionHandle(final Double d) {
		return Check.NuNObject(d) ? null : new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public Double getOrderPayAmount() {
		return precisionHandle(orderPayAmount);
	}

	public void setOrderPayAmount(final Double orderPayAmount) {
		this.orderPayAmount = precisionHandle(orderPayAmount);
	}

	public Integer getOrderPayWay() {
		return orderPayWay;
	}

	public void setOrderPayWay(final Integer orderPayWay) {
		this.orderPayWay = orderPayWay;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderPayVO [orderPayAmount=");
		builder.append(orderPayAmount);
		builder.append(", orderPayWay=");
		builder.append(orderPayWay);
		builder.append("]");
		return builder.toString();
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pzj.framework.toolkit.Check;

/**
 * 订单返利信息
 *
 * @author DongChunfu
 * @version $Id: OrderRebateEntity.java, v 0.1 2017年3月25日 下午1:16:37 DongChunfu Exp $
 */
public class OrderRebateEntity implements Serializable {
	private static final long serialVersionUID = -6618033782891915063L;

	/**订单ID*/
	private String orderId;
	/**商品ID*/
	private String merchId;
	/**返利方式(1：前返   2：后返)*/
	private int rebateMethod;
	/**结算方式(1即时返/2周期返)*/
	private int rebateSettlement;
	/**后返金额*/
	private double afterRebateAmount;
	/**政策建议零售价*/
	private double advicePrice;
	/**政策结算价*/
	private double settlementPrice;
	/**支付单价 前返取政策结算价，后返取政策建议零售价*/
	private double price;

	public OrderRebateEntity() {
		super();
	}

	/**
	 * 根据返利结算方式过滤订单返利信息
	 *
	 * @param orderRebates 订单返利数据模型
	 * @param rebateMethod 订单返利结算方式(返利方式（1：前返   2：后返）)
	 * @param rebateSettleWay 订单返利结算方式(结算方式（1即时返/2周期返）)
	 * @return 同一返利结算方式的订单返利数据模型
	 */
	public static List<OrderRebateEntity> filterRebateWay(final List<OrderRebateEntity> orderRebates, final int rebateMethod,
			final int rebateSettleWay) {
		final List<OrderRebateEntity> conRebates = new ArrayList<>();
		if (Check.NuNCollections(orderRebates)) {
			return conRebates;
		}
		for (final OrderRebateEntity rebate : orderRebates) {
			if (rebate.getRebateMethod() == rebateMethod) {
				if (rebateSettleWay == 0) {
					conRebates.add(rebate);
				}
				if (rebate.getRebateSettlement() == rebateSettleWay) {
					conRebates.add(rebate);
				}
			}
		}
		return conRebates;
	}

	/**
	 * 根据返利结算方式过滤订单返利信息
	 *
	 * @param orderRebates 订单商品返利集合
	 * @return 订单商品MAP
	 */
	public static Map<String, OrderRebateEntity> convertRebateToMap(final List<OrderRebateEntity> orderRebates) {
		final Map<String, OrderRebateEntity> conRebates = new HashMap<>();
		if (Check.NuNCollections(orderRebates)) {
			return conRebates;
		}
		for (final OrderRebateEntity rebateEntity : orderRebates) {
			conRebates.put(rebateEntity.getMerchId(), rebateEntity);
		}
		return conRebates;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public int getRebateMethod() {
		return rebateMethod;
	}

	public void setRebateMethod(final int rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	public int getRebateSettlement() {
		return rebateSettlement;
	}

	public void setRebateSettlement(final int rebateSettlement) {
		this.rebateSettlement = rebateSettlement;
	}

	public double getAfterRebateAmount() {
		return afterRebateAmount;
	}

	public void setAfterRebateAmount(final double afterRebateAmount) {
		this.afterRebateAmount = afterRebateAmount;
	}

	public double getAdvicePrice() {
		return advicePrice;
	}

	public void setAdvicePrice(final double advicePrice) {
		this.advicePrice = advicePrice;
	}

	public double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(final double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderRebateEntity [orderId=");
		builder.append(orderId);
		builder.append(", merchId=");
		builder.append(merchId);
		builder.append(", rebateMethod=");
		builder.append(rebateMethod);
		builder.append(", rebateSettlement=");
		builder.append(rebateSettlement);
		builder.append(", afterRebateAmount=");
		builder.append(afterRebateAmount);
		builder.append(", advicePrice=");
		builder.append(advicePrice);
		builder.append(", settlementPrice=");
		builder.append(settlementPrice);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

}

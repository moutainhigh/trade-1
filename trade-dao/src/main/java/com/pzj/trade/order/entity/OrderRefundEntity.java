/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pzj.framework.toolkit.Check;

/**
 * 订单退款信息
 *
 * @author DongChunfu
 * @version $Id: OrderRefundEntity.java, v 0.1 2017年3月25日 下午1:30:33 DongChunfu Exp $
 */
public class OrderRefundEntity implements Serializable {
	private static final long serialVersionUID = -3429468889865350509L;
	/**订单ID*/
	private String orderId;
	/**退款ID*/
	private String refundId;
	/**商品ID*/
	private String merchId;
	/**退款数量*/
	private int refundNum;
	/**退款单价*/
	private double refundPrice;
	/**退款创建时间*/
	private Date createTime;
	/**退款日期，成功后最后更新时间及退款审核时间和退款时间*/
	private Date refundDate;
	/**退款状态*/
	private int refundState;
	/**退款类型*/
	private int isForce;

	public OrderRefundEntity() {
		super();
	}

	/**
	 * 计算退款的总数量
	 * @author DongChunfu
	 *
	 * @param orderRefunds 订单退款记录
	 * @return 退款总数量
	 */
	public static int calculateRefundCount(final List<OrderRefundEntity> orderRefunds) {
		if (Check.NuNCollections(orderRefunds)) {
			return 0;
		}
		int totalRefundNum = 0;
		for (final OrderRefundEntity orderRefund : orderRefunds) {
			totalRefundNum += orderRefund.getRefundNum();
		}
		return totalRefundNum;
	}

	/**
	 * 将订单对应的所有退款流水按商品ID拆分
	 * key:商品ID
	 * value:退款流水
	 * @author DongChunfu
	 *
	 * @param orderRefunds
	 * @return
	 */
	public static Map<String, OrderRefundEntity> convertToMapByMerchId(final List<OrderRefundEntity> orderRefunds) {
		final Map<String, OrderRefundEntity> conMap = new HashMap<String, OrderRefundEntity>();
		for (final OrderRefundEntity orderRefund : orderRefunds) {
			final String merchId = orderRefund.getMerchId();
			conMap.put(merchId, orderRefund);
		}
		return conMap;
	}

	public static List<OrderRefundEntity> fillterByOrderId(final List<OrderRefundEntity> orderRefunds, final String orderId) {
		final List<OrderRefundEntity> fillterResult = new ArrayList<OrderRefundEntity>();
		for (final OrderRefundEntity orderRefund : orderRefunds) {
			if (orderRefund.getOrderId().equals(orderId)) {
				fillterResult.add(orderRefund);
			}
		}
		return fillterResult;
	}

	/**
	 * 将订单对应的所有退款流水按退款申请拆分
	 * key:退款申请ID
	 * value:退款流水
	 * @author DongChunfu
	 *
	 * @param orderRefunds
	 * @return
	 */
	public static Map<String, List<OrderRefundEntity>> convertToMapByRefundId(final List<OrderRefundEntity> orderRefunds) {
		final Map<String, List<OrderRefundEntity>> conMap = new HashMap<String, List<OrderRefundEntity>>();

		for (final OrderRefundEntity orderRefund : orderRefunds) {
			final String refundId = orderRefund.getRefundId();
			if (conMap.containsKey(refundId)) {
				conMap.get(refundId).add(orderRefund);
			} else {
				final List<OrderRefundEntity> refunds = new ArrayList<OrderRefundEntity>(1);
				refunds.add(orderRefund);
				conMap.put(refundId, refunds);
			}
		}
		return conMap;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
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

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	public double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(final double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(final Date refundDate) {
		this.refundDate = refundDate;
	}

	public int getRefundState() {
		return refundState;
	}

	public void setRefundState(final int refundState) {
		this.refundState = refundState;
	}

	public int getIsForce() {
		return isForce;
	}

	public void setIsForce(final int isForce) {
		this.isForce = isForce;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderRefundEntity [orderId=");
		builder.append(orderId);
		builder.append(", refundId=");
		builder.append(refundId);
		builder.append(", merchId=");
		builder.append(merchId);
		builder.append(", refundNum=");
		builder.append(refundNum);
		builder.append(", refundPrice=");
		builder.append(refundPrice);
		builder.append(", refundDate=");
		builder.append(refundDate);
		builder.append(", refundState=");
		builder.append(refundState);
		builder.append(", isForce=");
		builder.append(isForce);
		builder.append("]");
		return builder.toString();
	}

}

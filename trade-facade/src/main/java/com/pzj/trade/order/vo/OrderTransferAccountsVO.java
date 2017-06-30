/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.framework.toolkit.Check;

/**
 * 订单分账明细表实体
 *
 * @author DongChunfu
 * @version $Id: OrderTransferAccountsVO.java, v 0.1 2017年3月24日 下午3:51:19 DongChunfu Exp $
 */
public class OrderTransferAccountsVO implements Serializable {

	private static final long serialVersionUID = -1146652362017793422L;

	/**交易ID*/
	private String transactionId;
	/**退款ID*/
	private String refundId;
	/**
	 * 订单类型
	 * <li>1:SAAS订单</li>
	 * <li>2:退款单</li>
	 */
	private int orderType;
	/**
	 * 订单状态
	 * <li>1:已支付</li>
	 * <li>2:已核销</li>
	 * <li>3:待审核</li>
	 * <li>4:已退款</li>
	 */
	private int orderState;
	/**订单创建时间*/
	private Date orderCreateTime;
	/**
	 * 订单完成时间
	 * <li>已支付订单为：支付时间</li>
	 * <li>已消费订单为：核销时间</li>
	 * <li>已退款订单为：退款时间</li>
	 */
	private Date orderFinishTime;
	/**订单金额(单位元，两位小数)*/
	private double orderAmount;
	/**本级用户名称*/
	private Long currentUserId;
	/**收款方(当前供应商ID)*/
	private Long payeeParty;
	/**订单支付时间*/
	private Date orderPayTime;

	private List<OrderPayVO> orderPay;

	/**订单总数量*/
	private int orderTotalCount;
	/**
	 * 订单返利金
	 * sasa订单为正数；
	 * 退款订单为负数；
	 */
	private List<OrderRebateVO> orderRebates = new ArrayList<>(0);
	/**
	 * 微店返利金
	 * sasa订单为正数；
	 * 退款订单为负数；
	 */

	private List<OrderRebateVO> weshopRebates = new ArrayList<>(0);
	/**返利结算方*/
	private Long rebateClearingParty;
	/**商品结算金额*/
	private Double merchClearingAmount;
	/**商品结算方*/
	private Long merchClearingParty;

	public OrderTransferAccountsVO() {
		super();
	}

	private Double precisionHandle(final Double d) {
		return Check.NuNObject(d) ? null : new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 获取返利的总金额
	 *
	 * @return
	 */
	public double getRebateTotalAmout() {

		double rebateTotalAmout = 0D;
		if (Check.NuNCollections(orderRebates) && Check.NuNCollections(weshopRebates)) {
			return rebateTotalAmout;
		}

		if (!Check.NuNCollections(orderRebates)) {
			for (final OrderRebateVO orderRebate : orderRebates) {
				rebateTotalAmout += orderRebate.getRebateAmount();
			}
		}
		if (!Check.NuNCollections(weshopRebates)) {
			for (final OrderRebateVO orderRebate : weshopRebates) {
				rebateTotalAmout += orderRebate.getRebateAmount();
			}
		}
		return precisionHandle(rebateTotalAmout);
	}

	/**
	 * 获取总的结算金额
	 *
	 * @return
	 */
	public Double getTotalClearingAmount() {
		final Double rebateTotalAmout = getRebateTotalAmout();
		if (Check.NuNObject(merchClearingAmount) && Check.NuNObject(rebateTotalAmout)) {
			return 0D;
		}

		final double conMerchClearingAmount = Check.NuNObject(merchClearingAmount) ? 0D : merchClearingAmount;
		final double conRebateTotalAmout = Check.NuNObject(rebateTotalAmout) ? 0D : rebateTotalAmout;
		return precisionHandle(conMerchClearingAmount + conRebateTotalAmout);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(final String refundId) {
		this.refundId = refundId;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(final int orderType) {
		this.orderType = orderType;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(final int orderState) {
		this.orderState = orderState;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(final Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getOrderFinishTime() {
		return orderFinishTime;
	}

	public void setOrderFinishTime(final Date orderFinishTime) {
		this.orderFinishTime = orderFinishTime;
	}

	public double getOrderAmount() {
		return precisionHandle(orderAmount);
	}

	public void setOrderAmount(final double orderAmount) {
		this.orderAmount = precisionHandle(orderAmount);
	}

	public Long getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(final Long currentUserId) {
		this.currentUserId = currentUserId;
	}

	public Long getPayeeParty() {
		return payeeParty;
	}

	public void setPayeeParty(final Long payeeParty) {
		this.payeeParty = payeeParty;
	}

	public Date getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(final Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	public List<OrderPayVO> getOrderPay() {
		return orderPay;
	}

	public void setOrderPay(final List<OrderPayVO> orderPay) {
		this.orderPay = orderPay;
	}

	public int getOrderTotalCount() {
		return orderTotalCount;
	}

	public void setOrderTotalCount(final int orderTotalCount) {
		this.orderTotalCount = orderTotalCount;
	}

	public List<OrderRebateVO> getOrderRebates() {
		return orderRebates;
	}

	public void setOrderRebates(final List<OrderRebateVO> orderRebates) {
		this.orderRebates = orderRebates;
	}

	public List<OrderRebateVO> getWeshopRebates() {
		return weshopRebates;
	}

	public void setWeshopRebates(final List<OrderRebateVO> weshopRebates) {
		this.weshopRebates = weshopRebates;
	}

	public Long getRebateClearingParty() {
		return rebateClearingParty;
	}

	public void setRebateClearingParty(final Long rebateClearingParty) {
		this.rebateClearingParty = rebateClearingParty;
	}

	public Double getMerchClearingAmount() {
		return precisionHandle(merchClearingAmount);
	}

	public void setMerchClearingAmount(final Double merchClearingAmount) {
		this.merchClearingAmount = precisionHandle(merchClearingAmount);
	}

	public Long getMerchClearingParty() {
		return merchClearingParty;
	}

	public void setMerchClearingParty(final Long merchClearingParty) {
		this.merchClearingParty = merchClearingParty;
	}

	public double getOrderPayAmount() {
		double payAmount = 0D;

		if (!Check.NuNCollections(orderPay)) {
			for (final OrderPayVO pay : orderPay) {
				payAmount += pay.getOrderPayAmount();
			}
		}

		return payAmount;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderTransferAccountsVO [transactionId=");
		builder.append(transactionId);
		builder.append(", refundId=");
		builder.append(refundId);
		builder.append(", orderType=");
		builder.append(orderType);
		builder.append(", orderState=");
		builder.append(orderState);
		builder.append(", orderCreateTime=");
		builder.append(orderCreateTime);
		builder.append(", orderFinishTime=");
		builder.append(orderFinishTime);
		builder.append(", orderAmount=");
		builder.append(orderAmount);
		builder.append(", currentUserId=");
		builder.append(currentUserId);
		builder.append(", payeeParty=");
		builder.append(payeeParty);
		builder.append(", orderPayTime=");
		builder.append(orderPayTime);
		builder.append(", orderPay=");
		builder.append(orderPay);
		builder.append(", orderTotalCount=");
		builder.append(orderTotalCount);
		builder.append(", orderRebates=");
		builder.append(orderRebates);
		builder.append(", weshopRebates=");
		builder.append(weshopRebates);
		builder.append(", rebateClearingParty=");
		builder.append(rebateClearingParty);
		builder.append(", merchClearingAmount=");
		builder.append(merchClearingAmount);
		builder.append(", merchClearingParty=");
		builder.append(merchClearingParty);
		builder.append("]");
		return builder.toString();
	}

}

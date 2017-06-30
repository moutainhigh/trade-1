/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.common.SalePortEnum;

/**
 * 订单分账明细数据模型
 *
 * @author DongChunfu
 * @version $Id: OrderTransferAccountsDetailEntity.java, v 0.1 2017年3月25日 下午1:34:57 DongChunfu Exp $
 */
public class OrderTransferAccountsDetailEntity implements Serializable {

	private static final long serialVersionUID = -7426141977078331937L;

	/**订单ID*/
	private String orderId;
	/**交易ID*/
	private String transactionId;
	/**上级订单ID*/
	private String parentOrderId;
	/**订单级别*/
	private int orderLevel;
	/**本级用户名称*/
	private long resellerId;
	/**收款方(当前供应商ID)*/
	private long supplierId;
	/**销售端口*/
	private int salePort;
	/**订单金额(单位元，两位小数)*/
	private Double orderAmount;
	/**订单总数量*/
	private int totalNum;
	/**退款总数量*/
	private int refundNum;
	/**
	 * 订单状态
	 * <li>1:已支付</li>
	 * <li>2:已核销</li>
	 * <li>3:已退款</li>
	 */
	private int orderState;
	/**订单创建时间*/
	private Date createTime;
	/**订单支付时间*/
	private Date payTime;
	/**订单支付方式*/
	private int payWay;
	/**三方支付方式*/
	private int thirdPayType;
	/**核销时间*/
	private Date confirmTime;

	private List<OrderRebateEntity> orderRebates;

	public OrderTransferAccountsDetailEntity() {
		super();
	}

	/**
	 * 初始分销商订单
	 *
	 * @return
	 */
	public boolean isParentOrder() {
		return orderId.equals(transactionId);
	}

	/**
	 * 初始供应商订单
	 *
	 * @return
	 */
	public boolean isRootOrder() {
		return orderLevel == 1;
	}

	/**
	 * 是微店订单
	 *
	 * @return
	 */
	public boolean isMerchantOrder() {
		return (isParentOrder() && salePort == SalePortEnum.MERCHANT_MICSHOP.getValue());
	}

	public boolean isAllRefund() {
		return totalNum == refundNum;
	}

	public boolean isAfterPay() {
		return payWay == 6 || payWay == 5;
	}

	public boolean whetherRefund() {
		return refundNum > 0;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	public String getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(final String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(final long resellerId) {
		this.resellerId = resellerId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(final long supplierId) {
		this.supplierId = supplierId;
	}

	public int getSalePort() {
		return salePort;
	}

	public void setSalePort(final int salePort) {
		this.salePort = salePort;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(final Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(final int orderState) {
		this.orderState = orderState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(final Date payTime) {
		this.payTime = payTime;
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(final int payWay) {
		this.payWay = payWay;
	}

	public int getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(final int orderLevel) {
		this.orderLevel = orderLevel;
	}

	public int getThirdPayType() {
		return thirdPayType;
	}

	public void setThirdPayType(final int thirdPayType) {
		this.thirdPayType = thirdPayType;
	}

	public List<OrderRebateEntity> getOrderRebates() {
		return orderRebates;
	}

	public void setOrderRebates(final List<OrderRebateEntity> orderRebates) {
		this.orderRebates = orderRebates;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(final Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderTransferAccountsDetailEntity [orderId=");
		builder.append(orderId);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", parentOrderId=");
		builder.append(parentOrderId);
		builder.append(", resellerId=");
		builder.append(resellerId);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", salePort=");
		builder.append(salePort);
		builder.append(", orderAmount=");
		builder.append(orderAmount);
		builder.append(", totalNum=");
		builder.append(totalNum);
		builder.append(", refundNum=");
		builder.append(refundNum);
		builder.append(", orderState=");
		builder.append(orderState);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", payTime=");
		builder.append(payTime);
		builder.append(", payWay=");
		builder.append(payWay);
		builder.append(", thirdPayType=");
		builder.append(thirdPayType);
		builder.append(", confirmTime=");
		builder.append(confirmTime);
		builder.append(", orderRebates=");
		builder.append(orderRebates);
		builder.append("]");
		return builder.toString();
	}

}

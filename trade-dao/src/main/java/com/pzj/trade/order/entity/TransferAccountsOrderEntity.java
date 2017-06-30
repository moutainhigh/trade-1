package com.pzj.trade.order.entity;

import java.io.Serializable;

public class TransferAccountsOrderEntity implements Serializable {
	private static final long serialVersionUID = -1L;

	/**订单ID*/
	private String orderId;
	/**交易ID，此次级联订单所用的整个交易ID，多个交易相同*/
	private String transactionId;
	/**父订单ID*/
	private String pOrderId;
	/**订单等级，供应端为1*/
	private int orderLevel;
	/**订单来源 0. 普通订单 1. 预约单 2. 免票 3. 特价票*/
	private int orderSource;
	/**订单支付者的资金帐号*/
	private long payerId;
	/**供应商ID*/
	private long supplierId;
	/**分销商ID(旅行社)*/
	private long resellerId;
	/**订单总金额*/
	private double totalAmount;
	/**已退款总金额*/
	private double refundAmount;
	/**订单包含的商品总数量*/
	private int totalNum;
	/**已确认的商品数量*/
	private int checkedNum;
	/**销售端口（APP, OTA, 微店）*/
	private int salePort;
	/**
	 * 支付方式.
	 * <ul>
	 * <li>0: 纯余额;</li>
	 * <li>1. 支付宝; </li>
	 * <li>2. 微信; </li>
	 * <li>4: 混合支付;</li>
	 * <li>5: 现金;</li>
	 * <li>6: 后付</li>
	 * </ul>
	 */
	private int payWay;

	public TransferAccountsOrderEntity() {
		super();
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

	public String getpOrderId() {
		return pOrderId;
	}

	public void setpOrderId(final String pOrderId) {
		this.pOrderId = pOrderId;
	}

	public int getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(final int orderLevel) {
		this.orderLevel = orderLevel;
	}

	public int getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(final int orderSource) {
		this.orderSource = orderSource;
	}

	public long getPayerId() {
		return payerId;
	}

	public void setPayerId(final long payerId) {
		this.payerId = payerId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(final long supplierId) {
		this.supplierId = supplierId;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(final long resellerId) {
		this.resellerId = resellerId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(final double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(final double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(final int checkedNum) {
		this.checkedNum = checkedNum;
	}

	public int getSalePort() {
		return salePort;
	}

	public void setSalePort(final int salePort) {
		this.salePort = salePort;
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(final int payWay) {
		this.payWay = payWay;
	}

}

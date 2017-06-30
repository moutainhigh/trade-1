package com.pzj.trade.order.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 清结算订单商品明细列表中商品模型.
 * @author GLG
 *
 */
public class SettlementOrdersOutModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单号ID.
	 */
	private String orderId;

	/**
	 * 订单号ID.
	 */
	private String transactionId;

	/**
	 * 订单状态.
	 */
	private String orderStatus;

	/**
	 * 订单类型.
	 * <p>1. SaaS订单</p>
	 * <p>2. 退款单</p>
	 */
	private String orderType;

	/**
	 * 订单创建时间
	 */
	private Timestamp createTime;

	/**
	 * 订单支付时间.
	 */
	private Timestamp payTime;

	/**
	 * 订单完成时间
	 */
	private Timestamp confirmTime;

	/**
	 * 用户名.
	 */
	private long userId;

	/**
	 * 订单包含的商品列表.
	 */
	@Deprecated
	private List<SettlementMerchOutModel> merchs;

	/**
	 *  商品id.
	 */
	private String merchId;
	/**
	 *  商品spu名称.
	 */
	private String merchName;
	/**
	 *  商品sku名称.
	 */
	private String skuName;
	/**
	 *  产品id.
	 */
	private long productId;
	/**
	 *  商品数量.
	 */
	private int totalNum;

	/**
	 *  销售金额
	 */
	private double salePrice;

	/**
	 *   采购金额
	 */
	private double purchPrice;
	/**
	 *  应收返利
	 */
	private double rebate;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getPurchPrice() {
		return purchPrice;
	}

	public void setPurchPrice(double purchPrice) {
		this.purchPrice = purchPrice;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(final String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(final String orderType) {
		this.orderType = orderType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(final Timestamp payTime) {
		this.payTime = payTime;
	}

	public Timestamp getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(final Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(final long userId) {
		this.userId = userId;
	}

	public List<SettlementMerchOutModel> getMerchs() {
		return merchs;
	}

	public void setMerchs(final List<SettlementMerchOutModel> merchs) {
		this.merchs = merchs;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettlementOrdersOutModel [orderId=");
		builder.append(orderId);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", orderType=");
		builder.append(orderType);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", payTime=");
		builder.append(payTime);
		builder.append(", confirmTime=");
		builder.append(confirmTime);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", merchs=");
		builder.append(merchs);
		builder.append("]");
		return builder.toString();
	}
}

package com.pzj.trade.order.model;

public class RefundMerchStrategyModel {
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 商品ID
	 */
	private String merchId;

	/**
	 * 交易ID
	 */
	private String transactionId;
	/**
	 * 建议零售价
	 */
	private double advicePrice;
	/**
	 * 结算价
	 */
	private double settlementPrice;

	/**
	 * 购买单价
	 */
	private double price;

	/**
	 * 返利方式 1：前返   2：后返
	 */
	private int rebateMethod;

	/**
	 * 后返金额
	 */
	private double afterRebateAmount;

	/**
	 * 上级订单ID
	 */
	private String porderId;

	/**
	 * 支付者Id
	 */
	private long payerId;

	/**
	 * 分销商ID
	 */
	private long resellerId;

	/**
	 * 供应商ID
	 */
	private long supplierId;

	/**
	 * 支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付
	 */
	private int payWay;

	/**
	 * 订单的销售端口（1：线下窗口、2：二维码微店、3：PC分销端、4：导游APP、5：商户APP、6：导游微店、7：商户微店、8：OTA、9：APP）
	 */
	private int salePort;

	/**
	 * 订单等级，初始供应商为1，各级递增
	 */
	private int orderLevel;
	
	
	/**
	 * 上级订单的商品政策信息
	 */
	private RefundMerchStrategyModel parentMerchStrategyModel;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getAdvicePrice() {
		return advicePrice;
	}

	public void setAdvicePrice(double advicePrice) {
		this.advicePrice = advicePrice;
	}

	public double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRebateMethod() {
		return rebateMethod;
	}

	public void setRebateMethod(int rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	public double getAfterRebateAmount() {
		return afterRebateAmount;
	}

	public void setAfterRebateAmount(double afterRebateAmount) {
		this.afterRebateAmount = afterRebateAmount;
	}

	public String getPorderId() {
		return porderId;
	}

	public void setPorderId(String porderId) {
		this.porderId = porderId;
	}

	public long getPayerId() {
		return payerId;
	}

	public void setPayerId(long payerId) {
		this.payerId = payerId;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public int getSalePort() {
		return salePort;
	}

	public void setSalePort(int salePort) {
		this.salePort = salePort;
	}

	public int getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(int orderLevel) {
		this.orderLevel = orderLevel;
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}

	public RefundMerchStrategyModel getParentMerchStrategyModel() {
		return parentMerchStrategyModel;
	}

	public void setParentMerchStrategyModel(RefundMerchStrategyModel parentMerchStrategyModel) {
		this.parentMerchStrategyModel = parentMerchStrategyModel;
	}
}

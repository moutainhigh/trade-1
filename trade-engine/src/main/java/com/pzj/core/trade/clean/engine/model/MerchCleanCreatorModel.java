package com.pzj.core.trade.clean.engine.model;

import java.util.Date;

/**
 * 创建商品的待清算信息的初始化模型
 * @author kangzl
 *
 */
public class MerchCleanCreatorModel {
	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * merchId
	 */
	private String merchId;
	/**
	 * 产品的SKUID
	 */
	private long productId;
	/**
	 * 商品的总数
	 */
	private int totalNum;

	/**
	 * 退款数量
	 */
	private int refundNum;
	/**
	 * 商品的核销数量
	 */
	private int checkNum;

	private int isCleaned;
	/**
	 * 商品状态
	 */
	private int merchState;
	/**
	 * 商品的单价
	 */
	private double price;
	/**
	 * 商品的退款结算价格
	 */
	private double refundAmount;
	/**
	 * 商品的核销凭证
	 */
	private int vourType;

	/**
	 * 是否是退款中的商品
	 */
	private int isRefunding;
	/**
	 * 采购订单对应的销售订单上的merchId
	 */
	private String rootMerchId;

	/**
	 * 销售订单上的商品单价
	 */
	private double rootPrice;
	/**
	 * 销售订单上的商品总数
	 */
	private int rootTotalNum;
	/**
	 * 销售订单的商品退款数量
	 */
	private int rootRefundNum;
	/**
	 * 销售订单上给分销商的退款金额
	 */
	private double rootRefundAmount;
	/**
	 * 产品有效期的截至时间
	 */
	private Date expireDate;

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getVourType() {
		return vourType;
	}

	public void setVourType(int vourType) {
		this.vourType = vourType;
	}

	public String getRootMerchId() {
		return rootMerchId;
	}

	public void setRootMerchId(String rootMerchId) {
		this.rootMerchId = rootMerchId;
	}

	public int getRootRefundNum() {
		return rootRefundNum;
	}

	public void setRootRefundNum(int rootRefundNum) {
		this.rootRefundNum = rootRefundNum;
	}

	public double getRootPrice() {
		return rootPrice;
	}

	public void setRootPrice(double rootPrice) {
		this.rootPrice = rootPrice;
	}

	public double getRootRefundAmount() {
		return rootRefundAmount;
	}

	public void setRootRefundAmount(double rootRefundAmount) {
		this.rootRefundAmount = rootRefundAmount;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	public int getRootTotalNum() {
		return rootTotalNum;
	}

	public void setRootTotalNum(int rootTotalNum) {
		this.rootTotalNum = rootTotalNum;
	}

	public int getIsCleaned() {
		return isCleaned;
	}

	public void setIsCleaned(int isCleaned) {
		this.isCleaned = isCleaned;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(int merchState) {
		this.merchState = merchState;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}

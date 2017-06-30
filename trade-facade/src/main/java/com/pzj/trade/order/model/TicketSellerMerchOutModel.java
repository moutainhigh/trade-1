package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 售票员订单列表中商品模型.
 * @author YRJ
 *
 */
public class TicketSellerMerchOutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商品ID */
	private String merchId;

	/** 商品状态 */
	private int merchState;

	/** 商品名称 */
	private String merchName;

	/** 规格名称 */
	private String skuName;

	/** 产品ID、skuId */
	private long productId;

	/** 商品总数量 */
	private int totalNum;

	/** 商品类型 */
	private int category;

	/**商品总金额*/
	private double totalAmount;

	/** 出游日期 yyyy-MM-dd*/
	private String travelTime;

	/** 凭证ID */
	private long voucherId;

	/**
	 * 团散标识.
	 * 1. 团
	 * 0. 散
	 */
	private int varie;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(int merchState) {
		this.merchState = merchState;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public int getVarie() {
		return varie;
	}

	public void setVarie(int varie) {
		this.varie = varie;
	}
}

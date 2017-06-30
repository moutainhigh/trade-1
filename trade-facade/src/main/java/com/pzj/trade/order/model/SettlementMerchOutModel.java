package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 清结算订单商品明细列表中商品模型.
 * @author GLG
 *
 */
public class SettlementMerchOutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merchId;

	private String merchName;

	private String skuName;

	private long productId;

	private int totalNum;

	private double salePrice;

	private double purchPrice;

	private double rebate;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(final String merchName) {
		this.merchName = merchName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(final String skuName) {
		this.skuName = skuName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(final long productId) {
		this.productId = productId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(final double salePrice) {
		this.salePrice = salePrice;
	}

	public double getPurchPrice() {
		return purchPrice;
	}

	public void setPurchPrice(final double purchPrice) {
		this.purchPrice = purchPrice;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(final double rebate) {
		this.rebate = rebate;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettlementMerchOutModel [merchId=");
		builder.append(merchId);
		builder.append(", merchName=");
		builder.append(merchName);
		builder.append(", skuName=");
		builder.append(skuName);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", totalNum=");
		builder.append(totalNum);
		builder.append(", salePrice=");
		builder.append(salePrice);
		builder.append(", purchPrice=");
		builder.append(purchPrice);
		builder.append(", rebate=");
		builder.append(rebate);
		builder.append("]");
		return builder.toString();
	}

}

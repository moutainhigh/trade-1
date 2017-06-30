/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 商品返利信息
 *
 * @author DongChunfu
 * @version $Id: RebateMerchEntity.java, v 0.1 2017年5月24日 上午10:10:24 DongChunfu Exp $
 */
public class RebateMerchEntity implements Serializable {
	private static final long serialVersionUID = -9070890033814600342L;

	/**当前用户对应的订单*/
	private String orderId;
	/**当前分销商*/
	private long resellerId;
	/**交易方*/
	private long supplierId;
	/**商品ID*/
	private String merchId;
	/**产品ID*/
	private Long skuId;
	/**检票数量*/
	private int checkedNum;
	/**商品总数量*/
	private int totalNum;

	/**退款总数量*/
	private int refundNum;

	public RebateMerchEntity() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
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

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(final Long skuId) {
		this.skuId = skuId;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(final int checkedNum) {
		this.checkedNum = checkedNum;
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RebateMerchEntity [orderId=");
		builder.append(orderId);
		builder.append(", resellerId=");
		builder.append(resellerId);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", merchId=");
		builder.append(merchId);
		builder.append(", skuId=");
		builder.append(skuId);
		builder.append(", checkedNum=");
		builder.append(checkedNum);
		builder.append(", totalNum=");
		builder.append(totalNum);
		builder.append(", refundNum=");
		builder.append(refundNum);
		builder.append("]");
		return builder.toString();
	}

}

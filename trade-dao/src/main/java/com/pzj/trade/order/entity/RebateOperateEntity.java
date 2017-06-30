/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;

/**
 * 订单返利操作实体
 *
 * @author DongChunfu
 * @version $Id: RebateOperateEntity.java, v 0.1 2017年5月23日 下午6:08:14 DongChunfu Exp $
 */
public class RebateOperateEntity implements Serializable {
	private static final long serialVersionUID = -9070890033814600342L;

	private Long resellerId; // 订单交易主体id，产生返利的钱给谁
	private Long supplierId; // 订单交易对手id，产生返利的钱谁出
	private Long skuId; // 哪个规格的返利
	private int skuNumber; // 订单规格购买数量
	private Long strategyId; // 政策id

	public Long getResellerId() {
		return resellerId;
	}

	public void setResellerId(final Long resellerId) {
		this.resellerId = resellerId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(final Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(final Long skuId) {
		this.skuId = skuId;
	}

	public int getSkuNumber() {
		return skuNumber;
	}

	public void setSkuNumber(final int skuNumber) {
		this.skuNumber = skuNumber;
	}

	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(final Long strategyId) {
		this.strategyId = strategyId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RebateOperateEntity [resellerId=");
		builder.append(resellerId);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", skuId=");
		builder.append(skuId);
		builder.append(", skuNumber=");
		builder.append(skuNumber);
		builder.append(", strategyId=");
		builder.append(strategyId);
		builder.append("]");
		return builder.toString();
	}

}

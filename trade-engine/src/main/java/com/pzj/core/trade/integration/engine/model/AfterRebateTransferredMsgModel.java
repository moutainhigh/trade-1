package com.pzj.core.trade.integration.engine.model;

import java.util.Date;

/**
 * 后返到账消息体模型
 * @author kangzl
 *
 */
public class AfterRebateTransferredMsgModel {
	private String transactionId; // 主订单id
	private Long skuId; // 规格id
	private Long resellerId; // 分销商id（交易主体id）
	private Long supplierId; // 供应商id（交易对手id）
	private Long inAccountTime; // 入账时间

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getResellerId() {
		return resellerId;
	}

	public void setResellerId(Long resellerId) {
		this.resellerId = resellerId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getInAccountTime() {
		return inAccountTime;
	}

	public void setInAccountTime(Long inAccountTime) {
		this.inAccountTime = inAccountTime;
	}

	public Date getTransferredTime() {
		return new Date(this.inAccountTime);
	}
}

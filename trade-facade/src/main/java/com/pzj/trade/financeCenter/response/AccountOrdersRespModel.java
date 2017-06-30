/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author GLG
 * @version $Id: AccountOrdersRespModel.java, v 0.1 2017年3月10日 下午6:16:10 Administrator Exp $
 */
public class AccountOrdersRespModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 2368148970690542904L;
	/**订单id*/
	private String orderId;
	private String transactionId;

	/**订单状态*/
	private int orderStatus;
	/**订单数量*/
	private int totalNum;
	/**订单金额*/
	private BigDecimal totalAmount;
	/**创建时间*/
	private Date createTime;
	/**分销商id*/
	private long resellerId;
	/**供应商id*/
	private long supplierId;

	/**如果有第三方支付*/
	private int thirdPayType;

	private int orderLevel;

	private List<AccountMerchRespModel> merchs = new ArrayList<AccountMerchRespModel>();

	public int getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(int orderLevel) {
		this.orderLevel = orderLevel;
	}

	public int getThirdPayType() {
		return thirdPayType;
	}

	public void setThirdPayType(int thirdPayType) {
		this.thirdPayType = thirdPayType;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public List<AccountMerchRespModel> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<AccountMerchRespModel> merchs) {
		this.merchs = merchs;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

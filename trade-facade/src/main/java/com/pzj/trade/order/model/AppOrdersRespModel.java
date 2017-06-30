/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author GLG
 * @version $Id: AppOrdersRespModel.java, v 0.1 2017年3月10日 下午6:16:10 Administrator Exp $
 */
public class AppOrdersRespModel implements Serializable {

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

	private List<AppRebateMerchRespModel> merchs = new ArrayList<AppRebateMerchRespModel>();

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setMerchs(List<AppRebateMerchRespModel> merchs) {
		this.merchs = merchs;
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

	public List<AppRebateMerchRespModel> getMerchs() {
		return merchs;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

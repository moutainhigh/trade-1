package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * app返利列表输出模型.
 * @author GLG
 *
 */
public class AppRebateOrdersOutModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 3275646611699904075L;

	/**订单编号*/
	private String orderId;

	/**返利金额*/
	private double rebatePrice;

	/**预计到账时间*/
	private String rebateTimeMark;

	/**订单状态*/
	private int orderState;
	/**下单时间*/
	private Date orderCreateTime;

	/**分销商id*/
	private long resellerId;

	/**订单总金额  */
	private double totalAmount;

	private List<AppRebateMerchRespModel> appRebateMerchRespModels = new ArrayList<AppRebateMerchRespModel>();

	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getRebatePrice() {
		return rebatePrice;
	}

	public void setRebatePrice(double rebatePrice) {
		this.rebatePrice = rebatePrice;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public List<AppRebateMerchRespModel> getAppRebateMerchRespModels() {
		return appRebateMerchRespModels;
	}

	public void setAppRebateMerchRespModels(List<AppRebateMerchRespModel> appRebateMerchRespModels) {
		this.appRebateMerchRespModels = appRebateMerchRespModels;
	}

	public String getRebateTimeMark() {
		return rebateTimeMark;
	}

	public void setRebateTimeMark(String rebateTimeMark) {
		this.rebateTimeMark = rebateTimeMark;
	}
}

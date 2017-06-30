package com.pzj.trade.order.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 售票员订单列表输出模型.
 * @author YRJ
 *
 */
public class TicketSellerOrdersOutModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -1999662297770666706L;

	/** 订单ID */
	private String orderId;

	private String transactionId;
	/** 订单状态 */
	private int orderStatus;

	/** 分销商ID */
	private long resellerId;

	/** 分销商名称 */
	private String resellerName;

	/** 联系人 */
	private String contactee;

	/** 联系人电话 */
	private String contactMobile;

	/** 下单时间 */
	private Date createTime;

	/** 订单金额 */
	private double totalAmount;

	/** 消费金额 */
	private double consumeAmount;

	/** 退款金额 */
	private double refundAmount;

	/** 商品模型 */
	List<TicketSellerMerchOutModel> merchs = new ArrayList<TicketSellerMerchOutModel>();

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

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public String getResellerName() {
		return resellerName;
	}

	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = new Date(createTime.getTime());
	}

	public List<TicketSellerMerchOutModel> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<TicketSellerMerchOutModel> merchs) {
		this.merchs = merchs;
	}

	/**
	 * Getter method for property <tt>transactionId</tt>.
	 * 
	 * @return property value of transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Setter method for property <tt>transactionId</tt>.
	 * 
	 * @param transactionId value to be assigned to property transactionId
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Getter method for property <tt>totalAmount</tt>.
	 * 
	 * @return property value of totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Setter method for property <tt>totalAmount</tt>.
	 * 
	 * @param totalAmount value to be assigned to property totalAmount
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Getter method for property <tt>consumeAmount</tt>.
	 * 
	 * @return property value of consumeAmount
	 */
	public double getConsumeAmount() {
		return consumeAmount;
	}

	/**
	 * Setter method for property <tt>consumeAmount</tt>.
	 * 
	 * @param consumeAmount value to be assigned to property consumeAmount
	 */
	public void setConsumeAmount(double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	/**
	 * Getter method for property <tt>refundAmount</tt>.
	 * 
	 * @return property value of refundAmount
	 */
	public double getRefundAmount() {
		return refundAmount;
	}

	/**
	 * Setter method for property <tt>refundAmount</tt>.
	 * 
	 * @param refundAmount value to be assigned to property refundAmount
	 */
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * Setter method for property <tt>createTime</tt>.
	 * 
	 * @param createTime value to be assigned to property createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

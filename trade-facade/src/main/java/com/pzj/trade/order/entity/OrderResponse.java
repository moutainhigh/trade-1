package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.pzj.trade.book.model.StockEInfoModel;

public class OrderResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 订单ID*/
	private String orderId;

	/** 订单总金额*/
	private double totalAmount;

	/** 订单总数量*/
	private int totalNum;

	/** 订单支付方式*/
	private int pay_way;

	/** 订单支付锁：0: 未支付; 1: 已锁定,2:支付成功*/
	private int pay_state;

	/** 订单来源*/
	private int orderSource;

	/** 订单状态*/
	private int orderStatus;
	/** 分销商ID*/
	private long reseller_id;
	/** 供应商ID*/
	private long supplier_id;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，0否，默认1*/
	private int online_pay;

	/**
	 * 是否需要确认.
	 * 1. 不需要确认
	 * 2. 需要确认
	 * 3. 已确认. 
	 */
	private int needConfirm;

	/** 销售端口*/
	private int sale_port;

	/**预期取消时间*/
	private Date cancelTime;

	/**库存错误信息*/
	final private StockEInfoModel stockException;

	public OrderResponse() {
		this.stockException = null;
	}

	public OrderResponse(final StockEInfoModel stockException) {
		this.stockException = stockException;
	}

	/**
	 * 订单ID
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 订单ID
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 订单总价
	 * @return
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 订单总价
	 * @param totalAmount
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	/**
	 * Getter method for property <tt>contactee</tt>.
	 * 
	 * @return property value of contactee
	 */
	public String getContactee() {
		return contactee;
	}

	/**
	 * Setter method for property <tt>contactee</tt>.
	 * 
	 * @param contactee value to be assigned to property contactee
	 */
	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	/**
	 * Getter method for property <tt>contact_mobile</tt>.
	 * 
	 * @return property value of contact_mobile
	 */
	public String getContact_mobile() {
		return contact_mobile;
	}

	/**
	 * Setter method for property <tt>contact_mobile</tt>.
	 * 
	 * @param contact_mobile value to be assigned to property contact_mobile
	 */
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	/**
	 * Getter method for property <tt>is_direct</tt>.
	 * 
	 * @return property value of is_direct
	 */
	public int getIs_direct() {
		return is_direct;
	}

	/**
	 * Setter method for property <tt>is_direct</tt>.
	 * 
	 * @param is_direct value to be assigned to property is_direct
	 */
	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	/**
	 * Getter method for property <tt>online_pay</tt>.
	 * 
	 * @return property value of online_pay
	 */
	public int getOnline_pay() {
		return online_pay;
	}

	/**
	 * Setter method for property <tt>online_pay</tt>.
	 * 
	 * @param online_pay value to be assigned to property online_pay
	 */
	public void setOnline_pay(int online_pay) {
		this.online_pay = online_pay;
	}

	/**
	 * Getter method for property <tt>needConfirm</tt>.
	 * 
	 * @return property value of needConfirm
	 */
	public int getNeedConfirm() {
		return needConfirm;
	}

	/**
	 * Setter method for property <tt>needConfirm</tt>.
	 * 
	 * @param needConfirm value to be assigned to property needConfirm
	 */
	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}

	/**
	 * Getter method for property <tt>sale_port</tt>.
	 * 
	 * @return property value of sale_port
	 */
	public int getSale_port() {
		return sale_port;
	}

	/**
	 * Setter method for property <tt>sale_port</tt>.
	 * 
	 * @param sale_port value to be assigned to property sale_port
	 */
	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	/**
	 * Getter method for property <tt>serialversionuid</tt>.
	 * 
	 * @return property value of serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Getter method for property <tt>totalNum</tt>.
	 * 
	 * @return property value of totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * Setter method for property <tt>totalNum</tt>.
	 * 
	 * @param totalNum value to be assigned to property totalNum
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * Getter method for property <tt>pay_way</tt>.
	 * 
	 * @return property value of pay_way
	 */
	public int getPay_way() {
		return pay_way;
	}

	/**
	 * Setter method for property <tt>pay_way</tt>.
	 * 
	 * @param pay_way value to be assigned to property pay_way
	 */
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	/**
	 * Getter method for property <tt>pay_state</tt>.
	 * 
	 * @return property value of pay_state
	 */
	public int getPay_state() {
		return pay_state;
	}

	/**
	 * Setter method for property <tt>pay_state</tt>.
	 * 
	 * @param pay_state value to be assigned to property pay_state
	 */
	public void setPay_state(int pay_state) {
		this.pay_state = pay_state;
	}

	/**
	 * Getter method for property <tt>orderSource</tt>.
	 * 
	 * @return property value of orderSource
	 */
	public int getOrderSource() {
		return orderSource;
	}

	/**
	 * Setter method for property <tt>orderSource</tt>.
	 * 
	 * @param orderSource value to be assigned to property orderSource
	 */
	public void setOrderSource(int orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * Getter method for property <tt>orderStatus</tt>.
	 * 
	 * @return property value of orderStatus
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Setter method for property <tt>orderStatus</tt>.
	 * 
	 * @param orderStatus value to be assigned to property orderStatus
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public StockEInfoModel getStockException() {
		return stockException;
	}

}

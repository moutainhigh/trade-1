/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * app返利订单入参模型
 * @author Administrator
 * @version $Id: AppRebateOrdersReqModel.java, v 0.1 2017年3月7日 下午6:21:27 Administrator Exp $
 */
public class AppRebateOrdersReqModel extends PageableRequestBean {

	/**  */
	private static final long serialVersionUID = -4514489175097925233L;
	/**订单id*/
	private String orderId;
	/**返利来源：1微店返利、2卖油翁返利*/
	private int rebateFormtype;
	/**分销商id*/
	private long resellerId;
	/**订单创建开始时间*/
	private Date createStartTime;
	/**订单创建结束时间*/
	private Date createEndTime;
	/**订单id集合*/
	private List<String> orderIds;
	/** 产品名称*/
	private String supName;
	/**订单状态*/
	private int orderStatus;
	/**订单状态集合*/
	private List<Integer> orderStatusList;

	public List<Integer> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<Integer> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getRebateFormtype() {
		return rebateFormtype;
	}

	public void setRebateFormtype(int rebateFormtype) {
		this.rebateFormtype = rebateFormtype;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

}

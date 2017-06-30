/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.entity;

import java.io.Serializable;

import com.pzj.trade.order.model.DeliveryWay;

/**
 * 配送明细信息
 * @author fanggang
 * @version $Id: DeliveryDetail.java, v 0.1 2016年11月1日 上午10:52:46 fanggang Exp $
 */
public class DeliveryDetailEntity implements Serializable {

	private static final long serialVersionUID = -6903999371812009855L;

	/** 主键 */
	private Long id;

	/** 采购订单ID */
	private String orderID;

	/**
	 * 配送方式(1:上门自提, 2:快递配送)
	 * <p>取值参考：{@link DeliveryWay}</p>
	 */
	private Integer deliveryWay;

	/** 快递公司 */
	private String expressCompany;

	/** 快递单号 */
	private String expressNO;

	/** 创建时间 */
	private Long createTime;

	/**
	 * Getter method for property <tt>订单ID</tt>.
	 *
	 * @return property value of 订单ID
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * Setter method for property <tt>订单ID</tt>.
	 *
	 * @param orderID value to be assigned to property 订单ID
	 */
	public void setOrderID(final String orderID) {
		this.orderID = orderID;
	}

	/**
	 * Getter method for property <tt>配送方式(1:上门自提, 2:快递配送)</tt>.
	 * <p>取值参考：{@link DeliveryWay}</p>
	 * @return property value of 配送方式
	 */
	public Integer getDeliveryWay() {
		return deliveryWay;
	}

	/**
	 * Setter method for property <tt>配送方式(1:上门自提, 2:快递配送)</tt>.
	 * <p>取值参考：{@link DeliveryWay}</p>
	 * @param deliveryWay value to be assigned to property 配送方式
	 */
	public void setDeliveryWay(final Integer deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	/**
	 * Getter method for property <tt>快递公司</tt>.
	 *
	 * @return property value of 快递公司
	 */
	public String getExpressCompany() {
		return expressCompany;
	}

	/**
	 * Setter method for property <tt>快递公司</tt>.
	 *
	 * @param expressCompany value to be assigned to property 快递公司
	 */
	public void setExpressCompany(final String expressCompany) {
		this.expressCompany = expressCompany;
	}

	/**
	 * Getter method for property <tt>快递单号</tt>.
	 *
	 * @return property value of 快递单号
	 */
	public String getExpressNO() {
		return expressNO;
	}

	/**
	 * Setter method for property <tt>快递单号</tt>.
	 *
	 * @param expressNO value to be assigned to property 快递单号
	 */
	public void setExpressNO(final String expressNO) {
		this.expressNO = expressNO;
	}

	/**
	 * Getter method for property <tt>id</tt>.
	 *
	 * @return property value of id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 *
	 * @param id value to be assigned to property id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Getter method for property <tt>创建时间</tt>.
	 *
	 * @return property value of 创建时间
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * Setter method for property <tt>创建时间</tt>.
	 *
	 * @param createTime value to be assigned to property 创建时间
	 */
	public void setCreateTime(final Long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("DeliveryDetailEntity [id=");
		builder.append(id);
		builder.append(", orderID=");
		builder.append(orderID);
		builder.append(", deliveryWay=");
		builder.append(deliveryWay);
		builder.append(", expressCompany=");
		builder.append(expressCompany);
		builder.append(", expressNO=");
		builder.append(expressNO);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append("]");
		return builder.toString();
	}

}

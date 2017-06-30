/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.confirm.model;

import java.io.Serializable;

import com.pzj.trade.order.model.DeliveryDetailModel;

/**
 * 核销请求参数
 *
 * @author DongChunfu
 * @version $Id: ConfirmModel.java, v 0.1 2017年3月3日 下午3:48:25 Administrator Exp $
 */
public class ConfirmModel implements Serializable {

	private static final long serialVersionUID = -8289105958921363583L;

	/**凭证ID*/
	private long voucherId;

	private boolean acttingTicket;

	/**检票点. 当且仅当为演艺门票时, 传检票点信息(必填项), 通用产品不用传(选填项)*/
	private long ticketPoint;

	/**
	 * 核销方式. 只是用于记录商品采用何种方式进行的核销.
	 * <ul>
	 * <li>1. 手动核销, 平台点击触发.</li>
	 * <li>2. 系统核销, 系统定时触发的.</li>
	 *
	 * </ul>
	 */
	private int type = 1;

	/**
	 * 发货配送信息
	 * <p>在特产发货时是必须传入属性</p>
	 */
	private DeliveryDetailModel deliveryDetail;

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final long voucherId) {
		this.voucherId = voucherId;
	}

	/**
	 * Getter method for property <tt>acttingTicket</tt>.
	 *
	 * @return property value of acttingTicket
	 */
	public boolean isActtingTicket() {
		return acttingTicket;
	}

	/**
	 * Setter method for property <tt>acttingTicket</tt>.
	 *
	 * @param acttingTicket value to be assigned to property acttingTicket
	 */
	public void setActtingTicket(final boolean acttingTicket) {
		this.acttingTicket = acttingTicket;
	}

	public long getTicketPoint() {
		return ticketPoint;
	}

	public void setTicketPoint(final long ticketPoint) {
		this.ticketPoint = ticketPoint;
	}

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}

	/**
	 * Getter method for property <tt>发货配送信息</tt>.
	 *
	 * @return property value of 发货配送信息
	 */
	public DeliveryDetailModel getDeliveryDetail() {
		return deliveryDetail;
	}

	/**
	 * Setter method for property <tt>发货配送信息</tt>.
	 *
	 * @param deliveryDetail value to be assigned to property 发货配送信息
	 */
	public void setDeliveryDetail(final DeliveryDetailModel deliveryDetail) {
		this.deliveryDetail = deliveryDetail;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ConfirmModel [voucherId=");
		builder.append(voucherId);
		builder.append(", acttingTicket=");
		builder.append(acttingTicket);
		builder.append(", ticketPoint=");
		builder.append(ticketPoint);
		builder.append(", type=");
		builder.append(type);
		builder.append(", deliveryDetail=");
		builder.append(deliveryDetail);
		builder.append("]");
		return builder.toString();
	}

}

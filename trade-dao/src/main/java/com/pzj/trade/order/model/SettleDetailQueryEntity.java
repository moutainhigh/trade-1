/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

import com.pzj.framework.toolkit.Check;

/**
 * 计算明细查询交易参数
 *
 * @author DongChunfu
 * @version $Id: SettleDetailQueryEntity.java, v 0.1 2017年5月15日 下午4:28:50 DongChunfu Exp $
 */
public class SettleDetailQueryEntity implements Serializable {
	private static final long serialVersionUID = -3750606157110214840L;

	private boolean pageable = false;

	private int limit;

	private int offset;

	/**SAAS用户ID*/
	private long userId;
	/**SAAS用户角色，默认为1；
	 * <li>1,供应商</li>
	 * <li>2,分销商</li>
	 */
	private long userRole = 1;
	/**
	 * 结算状态，默认为1；
	 * <li>0,待结算</li>
	 * <li>1,已结算</li>
	 */
	private int settleState = 1;
	/**
	 * 线上线下，默认为1；
	 *
	 * <li>0,线下</li>
	 * <li>1,线上</li>
	 */
	private int online = 1;

	/**交易ID*/
	private String transactionId;

	/**
	 * 订单状态
	 * <li>1: 待付款</li>
	 * <li>10: 已支付</li>
	 * <li>20: 已取消</li>
	 * <li>30: 已退款</li>
	 * <li>40: 已完成</li>
	 */
	private int tradeState;

	/**产品名称*/
	private String productName;

	/**相对用户ID
	 * <li>若为<code>null</code>则查询所有</li>
	 * <li>当前用户角色为供应商，相对用户角色为对应的分销商ID</li>
	 * <li>当前用户角色为分销商，相对用户角色为对应的供应商ID</li>
	 */
	private Long relativeUserId;

	/**开始日期*/
	private Date beginDate;
	/**结束日期*/
	private Date endDate;

	public SettleDetailQueryEntity() {
		super();
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(final int online) {
		this.online = online;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(final String transactionId) {
		this.transactionId = transactionId;
	}

	public int getTradeState() {
		return tradeState;
	}

	public void setTradeState(final int tradeState) {
		this.tradeState = tradeState;
	}

	public String getProductName() {
		return Check.NuNString(productName) ? null : "%" + productName + "%";
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public int getSettleState() {
		return settleState;
	}

	public void setSettleState(final int settleState) {
		this.settleState = settleState;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(final long userId) {
		this.userId = userId;
	}

	public long getUserRole() {
		return userRole;
	}

	public void setUserRole(final long userRole) {
		this.userRole = userRole;
	}

	public Long getRelativeUserId() {
		return relativeUserId;
	}

	public void setRelativeUserId(final Long relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(final Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(final int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(final int offset) {
		this.offset = offset;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(final boolean pageable) {
		this.pageable = pageable;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleDetailQueryEntity [pageable=");
		builder.append(pageable);
		builder.append(", limit=");
		builder.append(limit);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userRole=");
		builder.append(userRole);
		builder.append(", settleState=");
		builder.append(settleState);
		builder.append(", online=");
		builder.append(online);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", tradeState=");
		builder.append(tradeState);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", relativeUserId=");
		builder.append(relativeUserId);
		builder.append(", beginDate=");
		builder.append(beginDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}

}

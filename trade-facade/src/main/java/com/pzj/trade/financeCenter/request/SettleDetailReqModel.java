/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.request;

import java.io.Serializable;

/**
 * 财务中心，SAAS用户结算明细信息请求参数
 *
 * @author DongChunfu
 * @version $Id: SettleDetailReqModel.java, v 0.1 2017年5月15日 上午10:27:09 DongChunfu Exp $
 */
public class SettleDetailReqModel extends SettleGatherReqModel implements Serializable {
	private static final long serialVersionUID = 3599576863410388198L;

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
	 * <li>10: 已支付</li>
	 * <li>30: 已退款</li>
	 * <li>40: 已完成</li>
	 */
	private int tradeState;

	/**相对用户ID
	 * <li>若为<code>null</code>则查询所有</li>
	 * <li>当前用户角色为供应商，相对用户角色为对应的分销商ID</li>
	 * <li>当前用户角色为分销商，相对用户角色为对应的供应商ID</li>
	 */
	private Long relativeUserId;

	/**产品名称*/
	private String productName;

	public SettleDetailReqModel() {
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
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public Long getRelativeUserId() {
		return relativeUserId;
	}

	public void setRelativeUserId(final Long relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SaasSettleGatherReqModel [settleState=");
		builder.append(getSettleState());
		builder.append(", userId=");
		builder.append(getUserId());
		builder.append(", userRole=");
		builder.append(getUserRole());
		builder.append(", relativeUserIds=");
		builder.append(getRelativeUserIds());
		builder.append(", beginDate=");
		builder.append(getBeginDate());
		builder.append(", endDate=");
		builder.append(getEndDate());
		builder.append("]");

		builder.append("SettleDetailReqModel [online=");
		builder.append(online);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", tradeState=");
		builder.append(tradeState);
		builder.append(", relativeUserId=");
		builder.append(relativeUserId);
		builder.append(", productName=");
		builder.append(productName);
		builder.append("]");

		return builder.toString();
	}

}

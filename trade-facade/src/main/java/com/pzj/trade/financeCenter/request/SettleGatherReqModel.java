/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 财务中心，SAAS用户结算汇总信息请求参数
 *
 * @author DongChunfu
 * @version $Id: SettleGatherReqModel.java, v 0.1 2017年5月15日 上午10:27:00 DongChunfu Exp $
 */
public class SettleGatherReqModel extends PageableRequestBean implements Serializable {
	private static final long serialVersionUID = -2598947660537898340L;

	/**
	 * 结算状态，默认为1；
	 * <li>0,待结算</li>
	 * <li>1,已结算</li>
	 */
	private int settleState = 1;

	/**SAAS用户ID*/
	private long userId;

	/**SAAS用户角色，默认为1；
	 * <li>1,供应商</li>
	 * <li>2,分销商</li>
	 */
	private int userRole = 1;

	/**相对用户ID
	 * <li>若为<code>null</code>则查询所有</li>
	 * <li>当前用户角色为供应商，相对用户角色为对应的分销商ID</li>
	 * <li>当前用户角色为分销商，相对用户角色为对应的供应商ID</li>
	 */
	private List<Long> relativeUserIds;

	/**开始日期*/
	private Date beginDate;
	/**结束日期*/
	private Date endDate;

	public SettleGatherReqModel() {
		super();
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

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(final int userRole) {
		this.userRole = userRole;
	}

	public List<Long> getRelativeUserIds() {
		return relativeUserIds;
	}

	public void setRelativeUserIds(final List<Long> relativeUserIds) {
		this.relativeUserIds = relativeUserIds;
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SaasSettleGatherReqModel [settleState=");
		builder.append(settleState);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userRole=");
		builder.append(userRole);
		builder.append(", relativeUserIds=");
		builder.append(relativeUserIds);
		builder.append(", beginDate=");
		builder.append(beginDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}

}

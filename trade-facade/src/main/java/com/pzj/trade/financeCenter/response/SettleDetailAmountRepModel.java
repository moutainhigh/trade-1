/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.pzj.framework.toolkit.Check;

/**
 * 相对当前用户结算汇总信息
 *
 * @author DongChunfu
 * @version $Id: SettleGatherAmountRepModel.java, v 0.1 2017年5月15日 下午1:45:56 DongChunfu Exp $
 */
public class SettleDetailAmountRepModel implements Serializable {
	private static final long serialVersionUID = 6771290951177579404L;

	/**
	 * 已收/应收
	 * 我与供应商-返利金额，我与分销商-货款支付金额
	 * */
	private double rebateAmount;
	/**
	 * 已付 /应付
	 * 我与供应商-货款支付金额，我与分销商-返利金额
	 * */
	private double accountAmount;

	/**查询时间范围内，与当前用户发生交易行为的相对用户ID集合*/
	private List<Long> relativeUserIds;

	public SettleDetailAmountRepModel() {
		super();
	}

	public double getRebateAmount() {
		return precisionHandle(rebateAmount);
	}

	public void setRebateAmount(final double rebateAmount) {
		this.rebateAmount = precisionHandle(rebateAmount);
	}

	public double getAccountAmount() {
		return precisionHandle(accountAmount);
	}

	public void setAccountAmount(final double accountAmount) {
		this.accountAmount = precisionHandle(accountAmount);
	}

	public List<Long> getRelativeUserIds() {
		return relativeUserIds;
	}

	public void setRelativeUserIds(final List<Long> relativeUserIds) {
		this.relativeUserIds = relativeUserIds;
	}

	private double precisionHandle(final Double d) {
		return Check.NuNObject(d) ? 0D : new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleDetailAmountRepModel [rebateAmount=");
		builder.append(rebateAmount);
		builder.append(", accountAmount=");
		builder.append(accountAmount);
		builder.append(", relativeUserIds=");
		builder.append(relativeUserIds);
		builder.append("]");
		return builder.toString();
	}

}

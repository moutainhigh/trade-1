/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;

import com.pzj.framework.entity.QueryResult;

/**
 * 结算明细模型（按规格进行拆分---只需要取后返的规格）
 *
 * @author DongChunfu
 * @version $Id: SettleDetailRepModel.java, v 0.1 2017年5月15日 下午1:55:08 DongChunfu Exp $
 */
public class SettleDetailRepModel implements Serializable {
	private static final long serialVersionUID = -1238915706285040160L;

	/**结算汇总金额*/
	private SettleDetailAmountRepModel settleAmount;

	/**结算商品详细信息*/
	private QueryResult<SettleMerchRepModel> settleMerches;

	public SettleDetailRepModel() {
		super();
	}

	public SettleDetailAmountRepModel getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(final SettleDetailAmountRepModel settleAmount) {
		this.settleAmount = settleAmount;
	}

	public QueryResult<SettleMerchRepModel> getSettleMerches() {
		return settleMerches;
	}

	public void setSettleMerches(final QueryResult<SettleMerchRepModel> settleMerches) {
		this.settleMerches = settleMerches;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleDetailRepModel [settleAmount=");
		builder.append(settleAmount);
		builder.append(", settleMerches=");
		builder.append(settleMerches.getRecords());
		builder.append("]");
		return builder.toString();
	}

}

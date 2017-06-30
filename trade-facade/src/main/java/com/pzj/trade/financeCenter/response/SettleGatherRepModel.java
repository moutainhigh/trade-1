/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;

import com.pzj.framework.entity.QueryResult;

/**
 * 结算中心模型
 *
 * @author DongChunfu
 * @version $Id: SettleRepModel.java, v 0.1 2017年5月12日 下午6:10:03 DongChunfu Exp $
 */
public class SettleGatherRepModel implements Serializable {

	private static final long serialVersionUID = -1079013817048933503L;

	/**结算汇总金额*/
	private SettleGatherAmountRepModel gatherModel;

	/**结算方详细信息*/
	private QueryResult<SettlePartyRepModel> settleParties;

	public SettleGatherRepModel() {
		super();
	}

	public SettleGatherAmountRepModel getGatherModel() {
		return gatherModel;
	}

	public void setGatherModel(final SettleGatherAmountRepModel gatherModel) {
		this.gatherModel = gatherModel;
	}

	public QueryResult<SettlePartyRepModel> getSettleParties() {
		return settleParties;
	}

	public void setSettleParties(final QueryResult<SettlePartyRepModel> settleParties) {
		this.settleParties = settleParties;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleGatherRepModel [gatherModel=");
		builder.append(gatherModel);
		builder.append(", settleParties=");
		builder.append(settleParties);
		builder.append("]");
		return builder.toString();
	}

}

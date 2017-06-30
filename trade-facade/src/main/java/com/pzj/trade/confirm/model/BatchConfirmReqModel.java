/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.confirm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量核销请求参数
 *
 * @author DongChunfu
 * @version $Id: BatchConfirmReqModel.java, v 0.1 2017年2月24日 下午6:51:40 DongChunfu Exp $
 */
public class BatchConfirmReqModel implements Serializable {

	private static final long serialVersionUID = 4927943477604348623L;

	/**销售订单ID集合*/
	private List<String> sellOrderIds = new ArrayList<>(1);

	public BatchConfirmReqModel() {
		super();
	}

	public List<String> getSellOrderIds() {
		return sellOrderIds;
	}

	public void setSellOrderIds(final List<String> sellOrderIds) {
		this.sellOrderIds = sellOrderIds;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BatchConfirmReqModel [sellOrderIds=");
		builder.append(sellOrderIds);
		builder.append("]");
		return builder.toString();
	}

}

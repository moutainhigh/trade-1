/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * app返利订单响应模型
 * @author GLG
 * @version $Id: AppRebateOrdersRespModel.java, v 0.1 2017年3月7日 下午6:18:52 Administrator Exp $
 */
public class AppRebateOrdersRespModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -6114981139143674763L;

	/** 统计值: 总记录数 */
	private int totalNum;

	/** 当前页码 */
	private int currentPage;

	/** 最大页 */
	private int maxPage;

	private List<AppRebateOrdersOutModel> orders = new ArrayList<AppRebateOrdersOutModel>();

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public List<AppRebateOrdersOutModel> getOrders() {
		return orders;
	}

	public void setOrders(List<AppRebateOrdersOutModel> orders) {
		this.orders = orders;
	}

}

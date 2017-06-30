package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 售票员订单列表返回参数
 * @author GLG 
 */
public class OperatorOrdersRespModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 统计值: 总记录数 */
	private int totalNum;

	/** 统计值: 购买的总金额. */
	private double totalAmount;

	/** 统计值: 购买的总规格数. */
	private int totalMerchNum;

	/** 当前页码 */
	private int currentPage;

	/** 最大页 */
	private int maxPage;

	private List<OperatorOrdersOutModel> orders = new ArrayList<OperatorOrdersOutModel>();

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalMerchNum() {
		return totalMerchNum;
	}

	public void setTotalMerchNum(int totalMerchNum) {
		this.totalMerchNum = totalMerchNum;
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

	public List<OperatorOrdersOutModel> getOrders() {
		return orders;
	}

	public void setOrders(List<OperatorOrdersOutModel> orders) {
		this.orders = orders;
	}

}

package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商订单列表响应模型.
 * @author YRJ
 *
 */
public class SupplierOrdersRespModel implements Serializable {

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

	private List<SupplierOrdersOutModel> orders = new ArrayList<SupplierOrdersOutModel>();

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

	public List<SupplierOrdersOutModel> getOrders() {
		return orders;
	}

	public void setOrders(List<SupplierOrdersOutModel> orders) {
		this.orders = orders;
	}
}

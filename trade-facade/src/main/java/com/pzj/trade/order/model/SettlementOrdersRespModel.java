package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 清结算订单商品明细列表中商品模型.
 * @author GLG
 *
 */
public class SettlementOrdersRespModel implements Serializable {

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

	private List<SettlementOrdersOutModel> orders = new ArrayList<SettlementOrdersOutModel>();

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(final double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalMerchNum() {
		return totalMerchNum;
	}

	public void setTotalMerchNum(final int totalMerchNum) {
		this.totalMerchNum = totalMerchNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(final int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(final int maxPage) {
		this.maxPage = maxPage;
	}

	public List<SettlementOrdersOutModel> getOrders() {
		return orders;
	}

	public void setOrders(final List<SettlementOrdersOutModel> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettlementOrdersRespModel [totalNum=");
		builder.append(totalNum);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", totalMerchNum=");
		builder.append(totalMerchNum);
		builder.append(", currentPage=");
		builder.append(currentPage);
		builder.append(", maxPage=");
		builder.append(maxPage);
		builder.append(", orders=");
		builder.append(orders);
		builder.append("]");
		return builder.toString();
	}

}

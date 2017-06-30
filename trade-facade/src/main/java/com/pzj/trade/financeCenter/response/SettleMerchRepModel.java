/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.vo.OrderPayVO;

/**
 * 结算明细模型（按规格进行拆分---只需要取后返的规格）
 *
 * @author DongChunfu
 * @version $Id: SettleDetailRepModel.java, v 0.1 2017年5月12日 下午6:00:36 DongChunfu Exp $
 */
public class SettleMerchRepModel implements Serializable {
	private static final long serialVersionUID = -1238915706285040160L;

	/**交易ID*/
	private String tradeId;
	/**核销时间*/
	private Date checkTime;
	/**核销时间*/
	private Date createTime;
	/**总金额（取订单金额）*/
	private double orderAmount;
	/**
	 * 已（应）收
	 *
	 * 若为返利相关，取对应规格数据
	 */
	private double income;
	/**
	 * 利润
	 *
	 * 分销商给saas用户的商品结算金额
	 * 取本级商品结算金额
	 */
	private double profit;
	/**
	 * 已（应）付
	 *
	 * 若为返利相关，取对应规格数据
	 */
	private double expense;
	/**供应商/分销商ID*/
	private long relativeUserId;
	/**
	 * 订单等级
	 */
	private int orderLevel;
	/**
	 * 供应商ID
	 */
	private long supplierId;
	/**
	 * 订单ID
	 */
	private long orderId;
	/**
	 * 订单状态
	 * <li>10: 已支付</li>
	 * <li>30: 已退款</li>
	 * <li>40: 已完成</li>
	 */
	private int orderStatus;
	/**
	 * 订单支付方式
	 * 0:纯余额;
	 * 1:支付宝;
	 * 2:微信;
	 * 4:混合支付;
	 * 5:现金;
	 * 6:后付;
	 * 7:货款支付.
	 */
	private List<OrderPayVO> orderPay;
	/**产品名*/
	private String productName;
	/**规格名*/
	private String skuName;
	/**购买数量（取规格数量） */
	private int buyNum;
	/**退款金额 （取规格退款金额）*/
	private double refundAmount;
	/**退款数量（取规格）*/
	private int refundNum;

	public SettleMerchRepModel() {
		super();
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(final String tradeId) {
		this.tradeId = tradeId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(final Date checkTime) {
		this.checkTime = checkTime;
	}

	public double getOrderAmount() {
		return precisionHandle(orderAmount);
	}

	public void setOrderAmount(final double orderAmount) {
		this.orderAmount = precisionHandle(orderAmount);
	}

	public double getIncome() {
		return precisionHandle(income);
	}

	public void setIncome(final double income) {
		this.income = precisionHandle(income);
	}

	public double getExpense() {
		return precisionHandle(expense);
	}

	public void setExpense(final double expense) {
		this.expense = precisionHandle(expense);
	}

	public long getRelativeUserId() {
		return relativeUserId;
	}

	public void setRelativeUserId(final long relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(final int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProductName() {
		return productName;
	}

	public List<OrderPayVO> getOrderPay() {
		return orderPay;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setOrderPay(final List<OrderPayVO> orderPay) {
		this.orderPay = orderPay;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(final String skuName) {
		this.skuName = skuName;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(final int buyNum) {
		this.buyNum = buyNum;
	}

	public double getRefundAmount() {
		return precisionHandle(refundAmount);
	}

	public void setRefundAmount(final double refundAmount) {
		this.refundAmount = precisionHandle(refundAmount);
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	private double precisionHandle(final Double d) {
		return Check.NuNObject(d) ? 0D : new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public double getProfit() {
		return precisionHandle(profit);
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public int getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(int orderLevel) {
		this.orderLevel = orderLevel;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SettleMerchRepModel [tradeId=");
		builder.append(tradeId);
		builder.append(", checkTime=");
		builder.append(checkTime);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", orderAmount=");
		builder.append(orderAmount);
		builder.append(", income=");
		builder.append(income);
		builder.append(", profit=");
		builder.append(profit);
		builder.append(", expense=");
		builder.append(expense);
		builder.append(", relativeUserId=");
		builder.append(relativeUserId);
		builder.append(", orderLevel=");
		builder.append(orderLevel);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", orderPay=");
		builder.append(orderPay);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", skuName=");
		builder.append(skuName);
		builder.append(", buyNum=");
		builder.append(buyNum);
		builder.append(", refundAmount=");
		builder.append(refundAmount);
		builder.append(", refundNum=");
		builder.append(refundNum);
		builder.append("]");
		return builder.toString();
	}

}

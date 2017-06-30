/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.model;

import java.io.Serializable;

import com.pzj.core.strategy.common.global.StrategyGlobal;
import com.pzj.core.strategy.common.global.StrategyGlobal.ISWeShopEnum;
import com.pzj.trade.order.common.SalePortEnum;

/**
 *
 * @author Administrator
 * @version $Id: StrategyModel.java, v 0.1 2016年12月5日 下午5:32:02 Administrator Exp $
 */
public class StrategyBaseModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -5326036142308567776L;

	/**政策ID*/
	private Long strategyId;

	/** 是否是微店政策（1：是，0：否） */
	private ISWeShopEnum isWeshopStrategy;

	private long skuSupplierId;

	private long supplierId;

	private long resellerId;

	/** 结算价 */
	private int settlementPrice;

	/** 建议零售价 */
	private int advicePrice;

	/** 是否需要支付  */
	private int needPay;

	/** 支付方式 :现金、签单、余额 */
	private int pay_type;

	/** 每单最少购买份数  */
	private Integer leastPerdueNumber;

	/** 每单最多购买份数  */
	private Integer mostPerdueNumber;

	/** 提前预定时间限制 */
	private BookModel bookModel = new BookModel();

	/**
	 * 返利方式.
	 * <p>1. 前返</p>
	 * <p>2. 后返</p>
	 */
	private int rebateType;

	/** 返利周期 （1即时返/2周期返）*/
	private int rebateCycle;

	/**解冻间隔天数（周期解冻默认-1；次日解冻1）*/
	private Integer intervalDays = -1;

	/**返利金额 */
	private double rebate_amount;

	/**
	 * 渠道ID.
	 */
	private long channelId;

	/**
	 * 销售端口
	 */
	private int sale_port;

	/**
	 * Getter method for property <tt>supplierId</tt>.
	 * 
	 * @return property value of supplierId
	 */
	public long getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for property <tt>supplierId</tt>.
	 * 
	 * @param supplierId value to be assigned to property supplierId
	 */
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Getter method for property <tt>resellerId</tt>.
	 * 
	 * @return property value of resellerId
	 */
	public long getResellerId() {
		return resellerId;
	}

	/**
	 * Setter method for property <tt>resellerId</tt>.
	 * 
	 * @param resellerId value to be assigned to property resellerId
	 */
	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}

	public ISWeShopEnum getIsWeshopStrategy() {
		return isWeshopStrategy;
	}

	public void setIsWeshopStrategy(ISWeShopEnum isWeshopStrategy) {
		this.isWeshopStrategy = isWeshopStrategy;
	}

	public int getRebateType() {
		return rebateType;
	}

	public void setRebateType(int rebateType) {
		this.rebateType = rebateType;
	}

	public double getSettlementPrice() {
		return AmountConverter.unitDivideToYuan(settlementPrice);
	}

	public void setSettlementPrice(int settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public double getAdvicePrice() {
		return AmountConverter.unitDivideToYuan(advicePrice);
	}

	public void setAdvicePrice(int advicePrice) {
		this.advicePrice = advicePrice;
	}

	public Integer getLeastPerdueNumber() {
		return leastPerdueNumber;
	}

	public void setLeastPerdueNumber(Integer leastPerdueNumber) {
		this.leastPerdueNumber = leastPerdueNumber;
	}

	public Integer getMostPerdueNumber() {
		return mostPerdueNumber;
	}

	public void setMostPerdueNumber(Integer mostPerdueNumber) {
		this.mostPerdueNumber = mostPerdueNumber;
	}

	public double getRebate_amount() {
		return AmountConverter.unitDivideToYuan(rebate_amount);
	}

	public void setRebate_amount(double rebate_amount) {
		this.rebate_amount = rebate_amount;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	/**
	 * Getter method for property <tt>sale_port</tt>.
	 * 
	 * @return property value of sale_port
	 */
	public int getSale_port() {
		return sale_port;
	}

	/**
	 * Setter method for property <tt>sale_port</tt>.
	 * 
	 * @param sale_port value to be assigned to property sale_port
	 */
	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	/**
	 * Getter method for property <tt>needPay</tt>.
	 * 
	 * @return property value of needPay
	 */
	public int getNeedPay() {
		return needPay;
	}

	/**
	 * Setter method for property <tt>needPay</tt>.
	 * 
	 * @param needPay value to be assigned to property needPay
	 */
	public void setNeedPay(int needPay) {
		this.needPay = needPay;
	}

	/**
	 * Getter method for property <tt>bookModel</tt>.
	 * 
	 * @return property value of bookModel
	 */
	public BookModel getBookModel() {
		return bookModel;
	}

	/**
	 * Setter method for property <tt>bookModel</tt>.
	 * 
	 * @param bookModel value to be assigned to property bookModel
	 */
	public void setBookModel(BookModel bookModel) {
		this.bookModel = bookModel;
	}

	/**
	 * Getter method for property <tt>pay_type</tt>.
	 * 
	 * @return property value of pay_type
	 */
	public int getPay_type() {
		return pay_type;
	}

	/**
	 * Setter method for property <tt>pay_type</tt>.
	 * 
	 * @param pay_type value to be assigned to property pay_type
	 */
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}

	/**
	 * Getter method for property <tt>rebateCycle</tt>.
	 * 
	 * @return property value of rebateCycle
	 */
	public int getRebateCycle() {
		return rebateCycle;
	}

	/**
	 * Setter method for property <tt>rebateCycle</tt>.
	 * 
	 * @param rebateCycle value to be assigned to property rebateCycle
	 */
	public void setRebateCycle(int rebateCycle) {
		this.rebateCycle = rebateCycle;
	}

	public Integer getIntervalDays() {
		return intervalDays;
	}

	public void setIntervalDays(Integer intervalDays) {
		this.intervalDays = intervalDays;
	}

	public double getPrice(int salePort, boolean isReseller) {
		if (SalePortEnum.isMicShop(salePort) && isReseller) {
			return this.getAdvicePrice();
		}
		//后返取建议零售价
		if (this.getRebateType() == StrategyGlobal.RebateMethodEnum.End.getId()) {
			return this.getAdvicePrice();
		}
		//其他取结算价
		return this.getSettlementPrice();

	}

	/**
	 * Getter method for property <tt>skuSupplierId</tt>.
	 * 
	 * @return property value of skuSupplierId
	 */
	public long getSkuSupplierId() {
		return skuSupplierId;
	}

	/**
	 * Setter method for property <tt>skuSupplierId</tt>.
	 * 
	 * @param skuSupplierId value to be assigned to property skuSupplierId
	 */
	public void setSkuSupplierId(long skuSupplierId) {
		this.skuSupplierId = skuSupplierId;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StrategyBaseModel [strategyId=");
		builder.append(strategyId);
		builder.append(", isWeshopStrategy=");
		builder.append(isWeshopStrategy);
		builder.append(", settlementPrice=");
		builder.append(settlementPrice);
		builder.append(", advicePrice=");
		builder.append(advicePrice);
		builder.append(", needPay=");
		builder.append(needPay);
		builder.append(", pay_type=");
		builder.append(pay_type);
		builder.append(", leastPerdueNumber=");
		builder.append(leastPerdueNumber);
		builder.append(", mostPerdueNumber=");
		builder.append(mostPerdueNumber);
		builder.append(", rebateType=");
		builder.append(rebateType);
		builder.append(", rebateCycle=");
		builder.append(rebateCycle);
		builder.append(", rebate_amount=");
		builder.append(rebate_amount);
		builder.append(", channelId=");
		builder.append(channelId);
		builder.append(", sale_port=");
		builder.append(sale_port);
		builder.append("]");
		return builder.toString();
	}

}
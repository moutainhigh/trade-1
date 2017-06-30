/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.model;

import java.io.Serializable;

import com.pzj.core.strategy.common.global.StrategyGlobal.ISWeShopEnum;

/**
 *
 * @author Administrator
 * @version $Id: StrategyModel.java, v 0.1 2016年12月5日 下午5:32:02 Administrator Exp $
 */
public class StrategyModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -5326036142308567776L;

	/**政策ID*/
	private Long strategyId;

	/** 政策类型 */
	private int strategyType;

	/** 是否是微店政策（1：是，0：否） */
	private ISWeShopEnum isWeshopStrategy;

	/** 结算价 */
	private int settlementPrice;

	/** 建议零售价 */
	private int advicePrice;

	/** 是否需要支付  */
	private int payable;

	/** 每单最少购买份数  */
	private Integer leastPerdueNumber;

	/** 每单最多购买份数  */
	private Integer mostPerdueNumber;

	/** 预定模型 */
	private BookModel book;

	/**
	 * 返利方式.
	 * <p>1. 前返</p>
	 * <p>2. 后返</p>
	 */
	private int rebateType;

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

	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}

	public int getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(int strategyType) {
		this.strategyType = strategyType;
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

	public int isPayable() {
		return payable;
	}

	public void setPayable(int payable) {
		this.payable = payable;
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

	public BookModel getBook() {
		return book;
	}

	public void setBook(BookModel book) {
		this.book = book;
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

}
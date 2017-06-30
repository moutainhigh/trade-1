package com.pzj.core.trade.book.engine.model;

import java.util.List;

public class ProductJsonEModel {
	private long productId;

	private long scenicId;

	private int buyNum;

	private long strategyId;

	private long strategyRelationId;

	private long parentUserId;

	private long subUserId;

	private int price;

	private Long stockRuleId;

	private long screeningsId;

	private long areaId;

	private String productName;

	private List<Long> seats;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public long getStrategyRelationId() {
		return strategyRelationId;
	}

	public void setStrategyRelationId(long strategyRelationId) {
		this.strategyRelationId = strategyRelationId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getScreeningsId() {
		return screeningsId;
	}

	public void setScreeningsId(long screeningsId) {
		this.screeningsId = screeningsId;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public List<Long> getSeats() {
		return seats;
	}

	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

	public int getTotalAmount() {
		return this.getBuyNum() * this.getPrice();
	}

	public Long getStockRuleId() {
		return stockRuleId;
	}

	public void setStockRuleId(Long stockRuleId) {
		this.stockRuleId = stockRuleId;
	}

	public boolean isHaveStockRule() {
		return this.getStockRuleId() != null && this.getStockRuleId() != 0;
	}

	public boolean noHaveStockRule() {
		return this.getStockRuleId() == null || this.getStockRuleId() == 0;
	}

	public long getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(long parentUserId) {
		this.parentUserId = parentUserId;
	}

	public long getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(long subUserId) {
		this.subUserId = subUserId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	public long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(long strategyId) {
		this.strategyId = strategyId;
	}

}

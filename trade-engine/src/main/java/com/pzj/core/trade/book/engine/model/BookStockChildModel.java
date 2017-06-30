package com.pzj.core.trade.book.engine.model;

import java.util.ArrayList;
import java.util.List;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;

public class BookStockChildModel {

	private Long productId;
	private Long stockRuleId;
	private long screeningsId;
	private long areaId;

	private int oldNum;
	private int newNum;
	private int preNum;

	private List<Long> oldSeats = new ArrayList<Long>();

	private List<Long> newSeats = new ArrayList<Long>();

	private List<Long> preSeats = new ArrayList<Long>();

	private Boolean isNeedSeat = false;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getStockRuleId() {
		return stockRuleId;
	}

	public void setStockRuleId(Long stockRuleId) {
		this.stockRuleId = stockRuleId;
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

	public int getOldNum() {
		return oldNum;
	}

	public int getNewNum() {
		return newNum;
	}

	public int getPreNum() {
		return preNum;
	}

	public List<Long> getOldSeats() {
		return oldSeats;
	}

	public List<Long> getNewSeats() {
		return newSeats;
	}

	public List<Long> getPreSeats() {
		return preSeats;
	}

	public Boolean getIsNeedSeat() {
		return isNeedSeat;
	}

	public void setIsNeedSeat(Boolean isNeedSeat) {
		this.isNeedSeat = isNeedSeat;
	}

	public void setNewInfo(List<Long> newSeats, int newNum) {
		if (!Check.NuNObject(newSeats))
			this.newSeats = newSeats;
		this.newNum = newNum;
	}

	public void setOldInfo(List<Long> oldSeats, int oldNum) {
		if (!Check.NuNObject(oldSeats))
			this.oldSeats = oldSeats;
		this.oldNum = oldNum;
	}

	public void setPreInfo(List<Long> preSeats, int preNum) {
		if (!Check.NuNObject(preSeats))
			this.preSeats = preSeats;
		this.preNum = preNum;
	}

	@Override
	public String toString() {
		return JSONConverter.toJson(this);
	}

}

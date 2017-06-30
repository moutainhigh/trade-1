package com.pzj.core.trade.book.engine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;

public class BookStockModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Long, BookStockChildModel> bookMap = null;

	private String transationId;

	private long travelDate;

	private long operator;

	private long spuId;

	public String getTransationId() {
		return transationId;
	}

	public void setTransationId(final String transationId) {
		this.transationId = transationId;
	}

	public long getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(final long travelDate) {
		this.travelDate = travelDate;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(final long operator) {
		this.operator = operator;
	}

	public long getSpuId() {
		return spuId;
	}

	public void setSpuId(final long spuId) {
		this.spuId = spuId;
	}

	public BookStockChildModel findBookStock(final Long pid) {
		if (Check.NuNMap(bookMap)) {
			return new BookStockChildModel();
		}
		return bookMap.get(pid);
	}

	public Map<Long, BookStockChildModel> getBookMap() {
		if (bookMap == null) {
			bookMap = new HashMap<Long, BookStockChildModel>();
		}
		return bookMap;
	}

	@Override
	public String toString() {
		return JSONConverter.toJson(this);
	}
}

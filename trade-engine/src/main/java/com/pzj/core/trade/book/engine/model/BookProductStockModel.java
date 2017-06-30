package com.pzj.core.trade.book.engine.model;

import java.io.Serializable;
import java.util.List;

public class BookProductStockModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 3419694524708974454L;

	private long productId;

	private List<Long> seats;

	private int num;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public List<Long> getSeats() {
		return seats;
	}

	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}

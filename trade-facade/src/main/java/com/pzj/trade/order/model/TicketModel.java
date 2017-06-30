package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 景区演艺属性对象.
 * @author CHJ
 *
 */
public class TicketModel implements Serializable {

	private static final long serialVersionUID = 6744493950046882719L;

	/** 景区名称 */
	public String scenic;

	/** 景区Id  */
	public String scenicId;

	/** 演艺产品座位 */
	public String seatNumbers;

	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}

	public String getScenicId() {
		return scenicId;
	}

	public void setScenicId(String scenicId) {
		this.scenicId = scenicId;
	}

	public String getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(String seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

}

package com.pzj.core.trade.order.engine.model;

/**
 * 产品预定规则模型.
 * @author YRJ
 *
 */
public class BookModel {

	/** 提前预订时间是否限制 */
	@Deprecated
	private boolean limitable = false;

	/** 提前预订时间是否限制,0:不限制，1：绝对时间限制，2：相对时间限制 */
	private int limiType = 0;

	/** 日期前天数可预订 */
	private int dueDay;

	/** 日期前时刻-（时）可预订 */
	private int dueHour;

	/** 日期前时刻-（分）可预订 */
	private int dueMin;

	/**
	 * Getter method for property <tt>limitable</tt>.
	 * 
	 * @return property value of limitable
	 */
	public boolean isLimitable() {
		return limitable;
	}

	/**
	 * Setter method for property <tt>limitable</tt>.
	 * 
	 * @param limitable value to be assigned to property limitable
	 */
	public void setLimitable(boolean limitable) {
		this.limitable = limitable;
	}

	/**
	 * Getter method for property <tt>limiType</tt>.
	 * 
	 * @return property value of limiType
	 */
	public int getLimiType() {
		return limiType;
	}

	/**
	 * Setter method for property <tt>limiType</tt>.
	 * 
	 * @param limiType value to be assigned to property limiType
	 */
	public void setLimiType(int limiType) {
		this.limiType = limiType;
	}

	public int getDueDay() {
		return dueDay;
	}

	public void setDueDay(int dueDay) {
		this.dueDay = dueDay;
	}

	public int getDueHour() {
		return dueHour;
	}

	public void setDueHour(int dueHour) {
		this.dueHour = dueHour;
	}

	public int getDueMin() {
		return dueMin;
	}

	public void setDueMin(int dueMin) {
		this.dueMin = dueMin;
	}

}

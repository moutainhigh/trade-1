package com.pzj.trade.merch.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品数量实体对象.
 * @author YRJ
 *
 */
public class MerchNumEntity {

	/**
	 * 商品ID.
	 */
	private String merchId;

	/**
	 * 商品总数量.
	 */
	private int totalNum;

	/**
	 * 检票数量.
	 */
	private int checkNum;

	/**
	 * 退款数量.
	 */
	private int refundNum;

	/**
	 * 商品状态.
	 */
	private int merchState;

	/**
	 * 凭证ID
	 */
	private long voucherId;
	/**
	 * 价格
	 */
	private double price;

	private Date checkTime;

	public String getMerchId() {
		return merchId;
	}

	public static Map<String, MerchNumEntity> convertToMerchNumMap(final List<MerchNumEntity> merches) {

		final Map<String, MerchNumEntity> convertMap = new HashMap<String, MerchNumEntity>();

		for (final MerchNumEntity merchNum : merches) {
			convertMap.put(merchNum.getMerchId(), merchNum);
		}

		return convertMap;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(final int checkNum) {
		this.checkNum = checkNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(final int merchState) {
		this.merchState = merchState;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final long voucherId) {
		this.voucherId = voucherId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(final Date checkTime) {
		this.checkTime = checkTime;
	}

}

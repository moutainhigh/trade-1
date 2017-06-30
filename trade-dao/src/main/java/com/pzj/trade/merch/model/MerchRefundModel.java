package com.pzj.trade.merch.model;

/**
 * 订单商品退款操作model
 * 
 * @author kangzl
 * @version $Id: MerchRefundModel.java, v 0.1 2016年8月25日 下午5:13:13 kangzl Exp $
 */
public class MerchRefundModel {
	private String merchId;
	private int refundNum;
	private double refundAmount;
	private int merchState;
	private int isRefunding;
	private int refundingNum;
	private int checkNum;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(final double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(final int merchState) {
		this.merchState = merchState;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(final int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(final int checkNum) {
		this.checkNum = checkNum;
	}

	public int getRefundingNum() {
		return refundingNum;
	}

	public void setRefundingNum(int refundingNum) {
		this.refundingNum = refundingNum;
	}

}

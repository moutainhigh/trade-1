package com.pzj.core.trade.refund.entity;

import java.util.Date;

/**
 * 构建商品退款必要的信息.
 *
 * @author YRJ
 *
 */
public class RefundMerchRequiredEntity {

	/**
	 * 商品ID.
	 */
	private String merchId;
	/**
	 * 跟商品ID
	 */
	private String rootMerchId;

	private String merchName;
	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 产品id.
	 */
	private long productId;

	/**
	 * 商品状态.
	 */
	private int merchState;

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
	 * 退款金额
	 */
	private double refundAmount;
	/**
	 * 结算价格
	 */
	private double price;
	/**
	 * 凭证ID
	 */
	private long voucherId;
	/**
	 * 退款状态
	 */
	private int isRefunding;

	/**
	 * 退款中的数量
	 */
	private int refundingNum;
	/**
	 * 出游开始时间
	 */
	private Date startTime;

	/**
	 * 游玩结束时间
	 */
	private Date expireTime;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(final String merchName) {
		this.merchName = merchName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(final long productId) {
		this.productId = productId;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(final int merchState) {
		this.merchState = merchState;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final long voucherId) {
		this.voucherId = voucherId;
	}

	public String getRootMerchId() {
		return rootMerchId;
	}

	public void setRootMerchId(final String rootMerchId) {
		this.rootMerchId = rootMerchId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(final double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(final int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public int getRefundingNum() {
		return refundingNum;
	}

	public void setRefundingNum(int refundingNum) {
		this.refundingNum = refundingNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(final Date expireTime) {
		this.expireTime = expireTime;
	}

}

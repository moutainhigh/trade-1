package com.pzj.core.trade.refund.engine.model;

import java.util.Date;

/**
 * 退款申请商品模型.
 * <ul>
 * <li>根据退款申请商品列表及订单对应的商品信息组装而来</li>
 * <li>贯穿整个退款申请流程, 将外部参数及内部元数据整合为符合业务逻辑的数据模型.</li>
 * </ul>
 * @author YRJ
 *
 */
public class RefundApplyMerchModel {

	/**
	 * 订单ID.
	 */
	private String orderId;

	/**
	 * 商品ID.
	 */
	private String merchId;

	/**
	 * 商品名称.
	 */
	private String merchName;

	/**
	 * 产品id.
	 */
	private long productId;

	/**
	 * 商品状态.
	 */
	private int merchState;

	/**
	 * 申请退的数量.
	 */
	private int applyNum;

	/**
	 * 商品总数量.
	 */
	private int totalNum;

	/**
	 * 商品已核销数量.
	 */
	private int checkedNum;

	/**
	 * 商品已退数量.
	 */
	private int refundNum;

	/**
	 * 是否处于退款中
	 */
	private int isRefunding;

	/**
	 * 退款中的商品数量
	 */
	private int refundingNum;

	/**
	 * 退款金额
	 */
	private double refundAmount;
	/**
	 * 商品结算价.
	 */
	private double price;

	/**
	 * 出游日期.
	 */
	private Date travelTime;

	/**
	 * 游玩有效期.
	 */
	private Date expireTime;

	private MerchCalculateModel merchCalculateModel;

	public RefundApplyMerchModel(final String orderId, final String merchId) {
		this.orderId = orderId;
		this.merchId = merchId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getMerchId() {
		return merchId;
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

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(final int applyNum) {
		this.applyNum = applyNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(final int checkedNum) {
		this.checkedNum = checkedNum;
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

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public int getRefundingNum() {
		return refundingNum;
	}

	public void setRefundingNum(int refundingNum) {
		this.refundingNum = refundingNum;
	}

	public Date getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(final Date travelTime) {
		this.travelTime = travelTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(final Date expireTime) {
		this.expireTime = expireTime;
	}

	public MerchCalculateModel getMerchCalculateModel() {
		return merchCalculateModel;
	}

	public void setMerchCalculateModel(MerchCalculateModel merchCalculateModel) {
		this.merchCalculateModel = merchCalculateModel;
	}

}

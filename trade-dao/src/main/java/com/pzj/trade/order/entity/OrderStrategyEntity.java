package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单政策表
 * 票之家公司
 * ----------------------*
 * 2016-05-18created
 */
public class OrderStrategyEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *主键
	 */
	private Long orderStrategyId;
	/**
	 *订单ID
	 */
	private String orderId;
	/**
	 *商品ID
	 */
	private String merchId;
	/**
	 *渠道ID
	 */
	private Long channelId;
	/**
	 *政策ID
	 */
	private Long strategyId;
	/**
	 *优惠金额
	 */
	@Deprecated
	private double discountAmount;
	/**
	 *1. 前返; 2: 代金券; 3: 满减码; 4: 红包; 5: 通用抵用券.
	 */
	@Deprecated
	private int discountType;
	/**
	 *优惠状态. 1: 生效; 2: 失效
	 */
	@Deprecated
	private int discountState = 1;
	/**
	 *优惠的说明，例如使用的满减码
	 */
	@Deprecated
	private String discountRemark;
	/* 政策建议零售价*/
	private BigDecimal advice_price;
	/* 政策结算价*/
	private BigDecimal settlement_price;
	/* 返利方式（1：前返   2：后返）*/
	private int rebate_method;
	/* 结算方式（1即时返/2周期返*/
	private int rebate_settlement;
	/**解冻间隔天数（周期解冻默认-1；次日解冻1）*/
	private Integer interval_day;

	/* 支付单价 前返取政策结算价，后返取政策建议零售价*/
	private BigDecimal price;
	/* 后返金额*/
	private BigDecimal after_rebate_amount;

	private Date createTime;

	private long skuSupplierId;

	private long supplierId;

	private long resellerId;

	public OrderStrategyEntity() {
	}

	public OrderStrategyEntity(final String orderId, final String merchId) {
		this.orderId = orderId;
		this.merchId = merchId;
	}

	/**
	 * Getter method for property <tt>orderStrategyId</tt>.
	 * 
	 * @return property value of orderStrategyId
	 */
	public Long getOrderStrategyId() {
		return orderStrategyId;
	}

	/**
	 * Setter method for property <tt>orderStrategyId</tt>.
	 * 
	 * @param orderStrategyId value to be assigned to property orderStrategyId
	 */
	public void setOrderStrategyId(final Long orderStrategyId) {
		this.orderStrategyId = orderStrategyId;
	}

	/**
	 * Getter method for property <tt>orderId</tt>.
	 * 
	 * @return property value of orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Setter method for property <tt>orderId</tt>.
	 * 
	 * @param orderId value to be assigned to property orderId
	 */
	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	/**
	 * Getter method for property <tt>merchId</tt>.
	 * 
	 * @return property value of merchId
	 */
	public String getMerchId() {
		return merchId;
	}

	/**
	 * Setter method for property <tt>merchId</tt>.
	 * 
	 * @param merchId value to be assigned to property merchId
	 */
	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	/**
	 * Getter method for property <tt>channelId</tt>.
	 * 
	 * @return property value of channelId
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * Setter method for property <tt>channelId</tt>.
	 * 
	 * @param channelId value to be assigned to property channelId
	 */
	public void setChannelId(final Long channelId) {
		this.channelId = channelId;
	}

	/**
	 * Getter method for property <tt>strategyId</tt>.
	 * 
	 * @return property value of strategyId
	 */
	public Long getStrategyId() {
		return strategyId;
	}

	/**
	 * Setter method for property <tt>strategyId</tt>.
	 * 
	 * @param strategyId value to be assigned to property strategyId
	 */
	public void setStrategyId(final Long strategyId) {
		this.strategyId = strategyId;
	}

	/**
	 * Getter method for property <tt>discountAmount</tt>.
	 * 
	 * @return property value of discountAmount
	 */
	public double getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * Setter method for property <tt>discountAmount</tt>.
	 * 
	 * @param discountAmount value to be assigned to property discountAmount
	 */
	public void setDiscountAmount(final double discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * Getter method for property <tt>discountType</tt>.
	 * 
	 * @return property value of discountType
	 */
	public int getDiscountType() {
		return discountType;
	}

	/**
	 * Setter method for property <tt>discountType</tt>.
	 * 
	 * @param discountType value to be assigned to property discountType
	 */
	public void setDiscountType(final int discountType) {
		this.discountType = discountType;
	}

	/**
	 * Getter method for property <tt>discountState</tt>.
	 * 
	 * @return property value of discountState
	 */
	public int getDiscountState() {
		return discountState;
	}

	/**
	 * Setter method for property <tt>discountState</tt>.
	 * 
	 * @param discountState value to be assigned to property discountState
	 */
	public void setDiscountState(final int discountState) {
		this.discountState = discountState;
	}

	/**
	 * Getter method for property <tt>discountRemark</tt>.
	 * 
	 * @return property value of discountRemark
	 */
	public String getDiscountRemark() {
		return discountRemark;
	}

	/**
	 * Setter method for property <tt>discountRemark</tt>.
	 * 
	 * @param discountRemark value to be assigned to property discountRemark
	 */
	public void setDiscountRemark(final String discountRemark) {
		this.discountRemark = discountRemark;
	}

	/**
	 * Getter method for property <tt>advice_price</tt>.
	 * 
	 * @return property value of advice_price
	 */
	public BigDecimal getAdvice_price() {
		if (advice_price == null) {
			return BigDecimal.valueOf(0.0d);
		}
		return advice_price;
	}

	/**
	 * Setter method for property <tt>advice_price</tt>.
	 * 
	 * @param advice_price value to be assigned to property advice_price
	 */
	public void setAdvice_price(final BigDecimal advice_price) {
		this.advice_price = advice_price;
	}

	/**
	 * Getter method for property <tt>settlement_price</tt>.
	 * 
	 * @return property value of settlement_price
	 */
	public BigDecimal getSettlement_price() {
		if (settlement_price == null) {
			return BigDecimal.valueOf(0.0d);
		}
		return settlement_price;
	}

	/**
	 * Setter method for property <tt>settlement_price</tt>.
	 * 
	 * @param settlement_price value to be assigned to property settlement_price
	 */
	public void setSettlement_price(final BigDecimal settlement_price) {
		this.settlement_price = settlement_price;
	}

	/**
	 * Getter method for property <tt>rebate_method</tt>.
	 * 
	 * @return property value of rebate_method
	 */
	public int getRebate_method() {
		return rebate_method;
	}

	/**
	 * Setter method for property <tt>rebate_method</tt>.
	 * 
	 * @param rebate_method value to be assigned to property rebate_method
	 */
	public void setRebate_method(final int rebate_method) {
		this.rebate_method = rebate_method;
	}

	/**
	 * Getter method for property <tt>rebate_settlement</tt>.
	 * 
	 * @return property value of rebate_settlement
	 */
	public int getRebate_settlement() {
		return rebate_settlement;
	}

	/**
	 * Setter method for property <tt>rebate_settlement</tt>.
	 * 
	 * @param rebate_settlement value to be assigned to property rebate_settlement
	 */
	public void setRebate_settlement(final int rebate_settlement) {
		this.rebate_settlement = rebate_settlement;
	}

	public Integer getInterval_day() {
		return interval_day;
	}

	public void setInterval_day(Integer interval_day) {
		this.interval_day = interval_day;
	}

	/**
	 * Getter method for property <tt>price</tt>.
	 * 
	 * @return property value of price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Setter method for property <tt>price</tt>.
	 * 
	 * @param price value to be assigned to property price
	 */
	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	/**
	 * Getter method for property <tt>after_rebate_amount</tt>.
	 * 
	 * @return property value of after_rebate_amount
	 */
	public BigDecimal getAfter_rebate_amount() {
		return after_rebate_amount;
	}

	/**
	 * Setter method for property <tt>after_rebate_amount</tt>.
	 * 
	 * @param after_rebate_amount value to be assigned to property after_rebate_amount
	 */
	public void setAfter_rebate_amount(final BigDecimal after_rebate_amount) {
		this.after_rebate_amount = after_rebate_amount;
	}

	/**
	 * Getter method for property <tt>createTime</tt>.
	 * 
	 * @return property value of createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Setter method for property <tt>createTime</tt>.
	 * 
	 * @param createTime value to be assigned to property createTime
	 */
	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Getter method for property <tt>skuSupplierId</tt>.
	 * 
	 * @return property value of skuSupplierId
	 */
	public long getSkuSupplierId() {
		return skuSupplierId;
	}

	/**
	 * Setter method for property <tt>skuSupplierId</tt>.
	 * 
	 * @param skuSupplierId value to be assigned to property skuSupplierId
	 */
	public void setSkuSupplierId(final long skuSupplierId) {
		this.skuSupplierId = skuSupplierId;
	}

	/**
	 * Getter method for property <tt>supplierId</tt>.
	 * 
	 * @return property value of supplierId
	 */
	public long getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for property <tt>supplierId</tt>.
	 * 
	 * @param supplierId value to be assigned to property supplierId
	 */
	public void setSupplierId(final long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Getter method for property <tt>resellerId</tt>.
	 * 
	 * @return property value of resellerId
	 */
	public long getResellerId() {
		return resellerId;
	}

	/**
	 * Setter method for property <tt>resellerId</tt>.
	 * 
	 * @param resellerId value to be assigned to property resellerId
	 */
	public void setResellerId(final long resellerId) {
		this.resellerId = resellerId;
	}

}
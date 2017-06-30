package com.pzj.trade.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *售票员商品详情模型
 */
public class OperatorMerchOutModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**商品id */
	private String merchId;
	/** 商品状态*/
	private int merchState;
	/** 商品名称*/
	private String merchName;
	/** 产品id*/
	private long productId;
	/** 商品数量*/
	private int totalNum;

	/**创建时间 */
	private Date createTime;
	/**商品状态 */
	private int merchType;

	/**商品总金额*/
	private double totalAmount;

	/** 是否退款中. 0是1否 */
	private int isRefunding;

	/** 出游/入住开始时间 */
	private Date start_time;
	/** 出游/入住结束时间 */
	private Date expire_time;
	/** 已消费金额 */
	private BigDecimal check_amount;
	/**已退金额*/
	private BigDecimal refund_amount;

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}

	public BigDecimal getCheck_amount() {
		return check_amount;
	}

	public void setCheck_amount(BigDecimal check_amount) {
		this.check_amount = check_amount;
	}

	public BigDecimal getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(BigDecimal refund_amount) {
		this.refund_amount = refund_amount;
	}

	/** 凭证ID */
	private long voucherId;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(int merchState) {
		this.merchState = merchState;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMerchType() {
		return merchType;
	}

	public void setMerchType(int merchType) {
		this.merchType = merchType;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}
}

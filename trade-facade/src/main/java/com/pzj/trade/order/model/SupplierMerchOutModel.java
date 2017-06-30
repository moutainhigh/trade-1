package com.pzj.trade.order.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 供应商商品明细列表中商品模型.
 * @author GLG
 *
 */
public class SupplierMerchOutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merchId;

	private int merchState;

	private String merchName;

	private long productId;

	private int totalNum;

	/*private int refundNum;

	private int checkNum;*/

	private String orderId;

	private String pOrderId;

	private int orderStatus;

	private Timestamp createTime;

	private int merchType;

	/**订单总金额*/
	private double totalAmount;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contactMobile;

	private int needConfirm;

	//是否退款中. 0是1否,
	private int isRefunding;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int direct;

	/**直签是否需要线上支付，1是，2否，默认1*/
	private int onlinePay;

	/** 出游/入住时间 */
	private Date startTime;

	/** 凭证ID */
	private long voucherId;

	/** 游客身份证号 */
	private String voucherContent;

	public String getVoucherContent() {
		return voucherContent;
	}

	public void setVoucherContent(String voucherContent) {
		this.voucherContent = voucherContent;
	}

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

	//	public int getRefundNum() {
	//		return refundNum;
	//	}
	//
	//	public void setRefundNum(int refundNum) {
	//		this.refundNum = refundNum;
	//	}

	//	public int getCheckNum() {
	//		return checkNum;
	//	}
	//
	//	public void setCheckNum(int checkNum) {
	//		this.checkNum = checkNum;
	//	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getpOrderId() {
		return pOrderId;
	}

	public void setpOrderId(String pOrderId) {
		this.pOrderId = pOrderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
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

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public int getNeedConfirm() {
		return needConfirm;
	}

	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(int isRefunding) {
		this.isRefunding = isRefunding;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(int onlinePay) {
		this.onlinePay = onlinePay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}
}

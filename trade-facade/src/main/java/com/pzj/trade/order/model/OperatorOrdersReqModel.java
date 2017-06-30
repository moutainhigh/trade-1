package com.pzj.trade.order.model;

import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

/**
*售票员订单列表查询参数
*@author GLG
 */
public class OperatorOrdersReqModel extends PageableRequestBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 操作者ID（销售员）.
	 * <p>代表当前登录者, 只查询该销售员操作生成的订单.</p>
	 */
	private long operatorId;

	private String transactionId;

	/**
	 * 订单状态.
	 * <ul>
	 * <li>1. 下单中</li>
	 * <li>10. 已支付</li>
	 * <li>20. 已取消</li>
	 * <li>30. 已退款</li>
	 * <li>40. 已完成</li>
	 * </ul>
	 */
	private int orderStatus;

	/**
	 * 商品状态.
	 * <ul>
	 * <li>-1. 不可用</li>
	 * <li>0. 待消费</li>
	 * <li>2. 待退款</li>
	 * <li>3. 已退款</li>
	 * <li>4. 待确认</li>
	 * <li>5. 已完成</li>
	 * </ul>
	 */
	private Integer merchState;

	/** 
	 * 产品类型. 不对该值做合法性判断.
	 */
	private int category;

	/**
	 * 产品名称, 支持模糊查询.
	 */
	private String merchName;

	/** 分销商ID列表*/
	private List<Long> resellerIds;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contactMobile;

	/** 联系人身份证号 */
	private String idcard;

	/** 出游开始日期 */
	private Date travelStartTime;

	/** 出游结束日期 */
	private Date travelEndTime;

	/** 下单开始日期 */
	private Date startDate;

	/** 下单结束日期 */
	private Date endDate;

	/**
	 * 团散.
	 * <ul>
	 * <li>-1. 全部</li>
	 * <li>1. 团票</li>
	 * <li>0. 散票</li>
	 * </ul>
	 */
	private String productVarie;

	/**
	 * 支付方式, 该查询条件只支持现金和后付方式的查询. 若该值为0, 则支付方式限定在现金和后付方式.
	 * <ul>
	 * <li>0. 全部</li>
	 * <li>5. 现金</li>
	 * <li>6. 后付（签单）</li>
	 * </ul>
	 */
	private int payWay;

	/** 导游ID列表 */
	private List<Long> guideIds;

	/** 二维码  */
	private String qrCode;

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getMerchState() {
		return merchState;
	}

	public void setMerchState(Integer merchState) {
		this.merchState = merchState;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProductVarie() {
		return productVarie;
	}

	public void setProductVarie(String productVarie) {
		this.productVarie = productVarie;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	/**
	 * Getter method for property <tt>resellerIds</tt>.
	 * 
	 * @return property value of resellerIds
	 */
	public List<Long> getResellerIds() {
		return resellerIds;
	}

	/**
	 * Setter method for property <tt>resellerIds</tt>.
	 * 
	 * @param resellerIds value to be assigned to property resellerIds
	 */
	public void setResellerIds(List<Long> resellerIds) {
		this.resellerIds = resellerIds;
	}

	/**
	 * Getter method for property <tt>payWay</tt>.
	 * 
	 * @return property value of payWay
	 */
	public int getPayWay() {
		return payWay;
	}

	/**
	 * Setter method for property <tt>payWay</tt>.
	 * 
	 * @param payWay value to be assigned to property payWay
	 */
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}

	/**
	 * Getter method for property <tt>guideIds</tt>.
	 * 
	 * @return property value of guideIds
	 */
	public List<Long> getGuideIds() {
		return guideIds;
	}

	/**
	 * Setter method for property <tt>guideIds</tt>.
	 * 
	 * @param guideIds value to be assigned to property guideIds
	 */
	public void setGuideIds(List<Long> guideIds) {
		this.guideIds = guideIds;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTravelStartTime() {
		return travelStartTime;
	}

	public void setTravelStartTime(Date travelStartTime) {
		this.travelStartTime = travelStartTime;
	}

	public Date getTravelEndTime() {
		return travelEndTime;
	}

	public void setTravelEndTime(Date travelEndTime) {
		this.travelEndTime = travelEndTime;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorOrdersReqModel [operatorId=");
		builder.append(operatorId);
		builder.append(", orderId=");
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", merchState=");
		builder.append(merchState);
		builder.append(", category=");
		builder.append(category);
		builder.append(", merchName=");
		builder.append(merchName);
		builder.append(", resellerIds=");
		builder.append(resellerIds);
		builder.append(", contactee=");
		builder.append(contactee);
		builder.append(", contactMobile=");
		builder.append(contactMobile);
		builder.append(", idcard=");
		builder.append(idcard);
		builder.append(", travelStartTime=");
		builder.append(travelStartTime);
		builder.append(", travelEndTime=");
		builder.append(travelEndTime);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", productVarie=");
		builder.append(productVarie);
		builder.append(", payWay=");
		builder.append(payWay);
		builder.append(", guideIds=");
		builder.append(this.guideIds);
		builder.append(", qrCode=");
		builder.append(qrCode);
		builder.append("]");
		return builder.toString();
	}

}

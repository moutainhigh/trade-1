package com.pzj.trade.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.entity.RemarkResponse;

/**
 * 订单详情 - 售票员 - 出參模型.
 * @author YRJ
 *
 */
public class TicketSellerOrderDetailRespModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 订单号	*/
	private String orderId;

	/** 订单状态 */
	private int orderStatus;

	/**订单金额 */
	private BigDecimal totalAmount;

	/** 已消费金额 */
	private BigDecimal checkAmount;

	/** 已退金额 */
	private BigDecimal refundAmount;

	/** 分销商Id*/
	private long resellerId;

	/**分销商手机 */
	private String resellerMobile;

	/** 旅行社ID*/
	private long travel;

	/** 旅行社名称*/
	private String travelName;

	/**旅行社手机 */
	private String travelMobile;

	/**旅行社部门 */
	private String departName;

	/**旅行社部门手机 */
	private String departMobile;

	/**导游id */
	private String guider;

	/**导游手机	 */
	private String guiderMobile;

	/** 下单时间 */
	private Date createTime;

	/** 支付时间*/
	private Date payTime;

	/**支付方式 */
	private String payType;

	/** 商品类型 */
	private int category;

	/** 出游日期 */
	private String travelDate;

	/** 团散 */
	private String varie;

	/** 零售端订单备注 */
	private List<RemarkResponse> remark;

	/** 联系人模型 */
	private ContacteeOutModel contact;

	private List<FilledModel> filleds;

	/** 配送信息*/
	private DeliveryDetailModel delivery;

	//private final List<OperatorMerchOutModel> operatorMerchOutModels = new ArrayList<OperatorMerchOutModel>();
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(BigDecimal checkAmount) {
		this.checkAmount = checkAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public String getResellerMobile() {
		return resellerMobile;
	}

	public void setResellerMobile(String resellerMobile) {
		this.resellerMobile = resellerMobile;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(long travel) {
		this.travel = travel;
	}

	public String getTravelName() {
		return travelName;
	}

	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}

	public String getTravelMobile() {
		return travelMobile;
	}

	public void setTravelMobile(String travelMobile) {
		this.travelMobile = travelMobile;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getDepartMobile() {
		return departMobile;
	}

	public void setDepartMobile(String departMobile) {
		this.departMobile = departMobile;
	}

	public String getGuider() {
		return guider;
	}

	public void setGuider(String guider) {
		this.guider = guider;
	}

	public String getGuiderMobile() {
		return guiderMobile;
	}

	public void setGuiderMobile(String guiderMobile) {
		this.guiderMobile = guiderMobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getVarie() {
		return varie;
	}

	public void setVarie(String varie) {
		this.varie = varie;
	}

	public List<RemarkResponse> getRemark() {
		return remark;
	}

	public void setRemark(List<RemarkResponse> remark) {
		this.remark = remark;
	}

	public ContacteeOutModel getContact() {
		return contact;
	}

	public void setContact(ContacteeOutModel contact) {
		this.contact = contact;
	}

	public List<FilledModel> getFilleds() {
		return filleds;
	}

	public void setFilleds(List<FilledModel> filleds) {
		this.filleds = filleds;
	}

	public DeliveryDetailModel getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryDetailModel delivery) {
		this.delivery = delivery;
	}
}

package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderListResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orderId;

	private String porderId;
	private String transactionId;

	private int orderStatus;

	private int totalNum;

	private int checkedNum;

	private int refundNum;

	private long payer_id;

	private long operator_id;

	private long supplier_id;

	private BigDecimal totalAmount;

	private BigDecimal refundAmount;

	private Timestamp createTime;

	/**预期取消时间*/
	private Date cancelTime;

	private Timestamp payTime;

	private Timestamp confirmTime;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;
	/**  订单类型. 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游'
	 * 此类型改为取MerchResponse 中merchType字段*/
	@Deprecated
	private int category;

	private int salesPort;
	private long resellerId;
	/** 联系人身份证号 */
	private String idcard_no;

	/**旅行社*/
	private long travel;

	/**旅行社部门*/
	private long travel_depart_id;

	/**导游ID*/
	private long guide_id;

	/**是否需要二次确认 1: 不需要; 2: 需要*/
	private long need_confirm;

	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;
	/**渠道类型. 1: 直签; 2: 直销; 3: 魔方分销*/
	@Deprecated
	private int channel_type;
	/**代下单标志：1:不需要代下单；2：需要代下单；3已经代下单*/
	private int agent_flag;

	/**本次查询所有订单的总商品数量，为了减少程序改动，放到查询结果的第一个记录里*/
	private int all_merch_num;
	/**本次查询所有订单的总金额，为了减少程序改动，放到查询结果的第一个记录里*/
	private BigDecimal all_amount;

	private int order_type;

	/**是否已清算 0：未清算，1：已清算*/
	private int is_cleaned;
	private List<MerchResponse> merchs = new ArrayList<MerchResponse>();

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，0否*/
	private Integer online_pay;

	/**强制退款状态(1，退款中 2，成功 3，失败)*/
	private int refund_state;

	/**'是否对接商品0: 否; 1: 是'*/
	private int is_dock;

	/**付款锁(支付状态). 0: 未支付; 1: 已锁定,2:支付成功*/
	private Integer payState;

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getIs_dock() {
		return is_dock;
	}

	public void setIs_dock(int is_dock) {
		this.is_dock = is_dock;
	}

	public int getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(int refund_state) {
		this.refund_state = refund_state;
	}

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	public Integer getOnline_pay() {
		return online_pay;
	}

	public void setOnline_pay(Integer online_pay) {
		this.online_pay = online_pay;
	}

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

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(long payer_id) {
		this.payer_id = payer_id;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(int checkedNum) {
		this.checkedNum = checkedNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Timestamp getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public List<MerchResponse> getMerchs() {
		return merchs;
	}

	public void setMerchs(List<MerchResponse> merchs) {
		this.merchs = merchs;
	}

	public int getSalesPort() {
		return salesPort;
	}

	public void setSalesPort(int salesPort) {
		this.salesPort = salesPort;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(long travel) {
		this.travel = travel;
	}

	public long getTravel_depart_id() {
		return travel_depart_id;
	}

	public void setTravel_depart_id(long travel_depart_id) {
		this.travel_depart_id = travel_depart_id;
	}

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(long guide_id) {
		this.guide_id = guide_id;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public long getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(long need_confirm) {
		this.need_confirm = need_confirm;
	}

	public int getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(int channel_type) {
		this.channel_type = channel_type;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public int getAll_merch_num() {
		return all_merch_num;
	}

	public void setAll_merch_num(int all_merch_num) {
		this.all_merch_num = all_merch_num;
	}

	public BigDecimal getAll_amount() {
		return all_amount;
	}

	public void setAll_amount(BigDecimal all_amount) {
		this.all_amount = all_amount;
	}

	public String getPorderId() {
		return porderId;
	}

	public void setPorderId(String porderId) {
		this.porderId = porderId;
	}

	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}

	public int getIs_cleaned() {
		return is_cleaned;
	}

	public void setIs_cleaned(int is_cleaned) {
		this.is_cleaned = is_cleaned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderListResponse other = (OrderListResponse) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

}

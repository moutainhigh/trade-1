package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderEntity implements Serializable {

	private static final long serialVersionUID = 4420770586224680188L;
	/**订单ID*/
	private String order_id;

	/**交易ID，此次级联订单所用的整个交易ID，多个交易相同*/
	private String transaction_id;

	/**父订单ID*/
	private String p_order_id;

	/**订单等级，供应端为1*/
	private int order_level;

	/**订单来源 0. 普通订单 1. 预约单 2. 免票 3. 特价票*/
	private int order_source;

	/**售票点*/
	private long ticket_office_id;

	/**订单支付者的资金帐号*/
	private long payer_id;

	/**操作者*/
	private long operator_id;

	/**供应商ID*/
	private long supplier_id;

	/**分销商ID(旅行社)*/
	private long reseller_id;

	/**旅行社*/
	private long travel;

	/**旅行社部门ID*/
	private long travel_depart_id;

	/**旅行社部门*/
	private String depart;

	/**导游ID*/
	private long guide_id;

	/** 导游名称 */
	private String guider;

	/**订单状态*/
	private int order_status = 1;

	/** 是否需要确认 */
	private int need_confirm;

	/**订单总金额*/
	private double total_amount;

	/**已退款总金额*/
	private double refund_amount;

	/**订单包含的商品总数量*/
	private int total_num;

	/**已确认的商品数量*/
	private int checked_num;

	/**已退款的商品数量*/
	private int refund_num;

	/**1: 魔方向供应商采购订单; 2: 分销商向魔方购买订单; 3: 直签的单产品*/
	private int order_type;

	/**
	 * 直签是否需要线上支付，1是，0否，默认1'
	 */
	private int online_pay;

	/**销售端口（APP, OTA, 微店）*/
	private int sale_port;

	/**app版本*/
	private String version;

	/**付款锁. 0: 未支付; 1: 已锁定,2:支付成功 */
	private int pay_state;
	/**创建时间*/
	private Date create_time;
	/**预期取消时间*/
	private Date cancel_time;

	/**支付时间*/
	private Date pay_time;

	/**第三方支付订单号*/
	private String third_code;

	/**第三方支付类型*/
	private int third_pay_type;

	/**
	 * 支付方式.
	 * <ul>
	 * <li>0: 纯余额;</li>
	 * <li>1. 支付宝; </li>
	 * <li>2. 微信; </li>
	 * <li>4: 混合支付;</li>
	 * <li>5: 现金;</li>
	 * <li>6: 后付</li>
	 * </ul>
	 */
	private int pay_way;

	/**订单关闭时间（取消、已退款、已完成）*/
	private Date confirm_time;

	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;
	/** 联系人身份证号 */
	private String idcard_no;

	/**代下单标志：1:不需要代下单；2：需要代下单；3已经代下单 */
	private int agent_flag;

	/** 渠道ID */
	private long channel_id;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**'是否对接商品 0: 否; 1: 是'*/
	private int is_dock;

	public int getIs_dock() {
		return is_dock;
	}

	public void setIs_dock(final int is_dock) {
		this.is_dock = is_dock;
	}

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(final int is_direct) {
		this.is_direct = is_direct;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(final String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getP_order_id() {
		return p_order_id;
	}

	public void setP_order_id(final String p_order_id) {
		this.p_order_id = p_order_id;
	}

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(final long payer_id) {
		this.payer_id = payer_id;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(final long operator_id) {
		this.operator_id = operator_id;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(final long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(final long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(final long travel) {
		this.travel = travel;
	}

	public long getTravel_depart_id() {
		return travel_depart_id;
	}

	public void setTravel_depart_id(final long travel_depart_id) {
		this.travel_depart_id = travel_depart_id;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(final String depart) {
		this.depart = depart;
	}

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(final long guide_id) {
		this.guide_id = guide_id;
	}

	public String getGuider() {
		return guider;
	}

	public void setGuider(final String guider) {
		this.guider = guider;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(final int order_status) {
		this.order_status = order_status;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(final double total_amount) {
		this.total_amount = total_amount;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(final double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(final int total_num) {
		this.total_num = total_num;
	}

	public int getChecked_num() {
		return checked_num;
	}

	public void setChecked_num(final int checked_num) {
		this.checked_num = checked_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(final int refund_num) {
		this.refund_num = refund_num;
	}

	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(final int order_type) {
		this.order_type = order_type;
	}

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(final int sale_port) {
		this.sale_port = sale_port;
	}

	/**
	 * Getter method for property <tt>order_level</tt>.
	 * 
	 * @return property value of order_level
	 */
	public int getOrder_level() {
		return order_level;
	}

	/**
	 * Setter method for property <tt>order_level</tt>.
	 * 
	 * @param order_level value to be assigned to property order_level
	 */
	public void setOrder_level(final int order_level) {
		this.order_level = order_level;
	}

	/**
	 * Getter method for property <tt>order_source</tt>.
	 * 
	 * @return property value of order_source
	 */
	public int getOrder_source() {
		return order_source;
	}

	/**
	 * Setter method for property <tt>order_source</tt>.
	 * 
	 * @param order_source value to be assigned to property order_source
	 */
	public void setOrder_source(final int order_source) {
		this.order_source = order_source;
	}

	/**
	 * Getter method for property <tt>ticket_office_id</tt>.
	 * 
	 * @return property value of ticket_office_id
	 */
	public long getTicket_office_id() {
		return ticket_office_id;
	}

	/**
	 * Setter method for property <tt>ticket_office_id</tt>.
	 * 
	 * @param ticket_office_id value to be assigned to property ticket_office_id
	 */
	public void setTicket_office_id(final long ticket_office_id) {
		this.ticket_office_id = ticket_office_id;
	}

	/**
	 * Getter method for property <tt>version</tt>.
	 * 
	 * @return property value of version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Setter method for property <tt>version</tt>.
	 * 
	 * @param version value to be assigned to property version
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * Getter method for property <tt>pay_state</tt>.
	 * 
	 * @return property value of pay_state
	 */
	public int getPay_state() {
		return pay_state;
	}

	/**
	 * Setter method for property <tt>pay_state</tt>.
	 * 
	 * @param pay_state value to be assigned to property pay_state
	 */
	public void setPay_state(final int pay_state) {
		this.pay_state = pay_state;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Date create_time) {
		this.create_time = create_time;
	}

	public Date getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(final Date cancel_time) {
		this.cancel_time = cancel_time;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(final Date pay_time) {
		this.pay_time = pay_time;
	}

	public String getThird_code() {
		return third_code;
	}

	public void setThird_code(final String third_code) {
		this.third_code = third_code;
	}

	public int getThird_pay_type() {
		return third_pay_type;
	}

	public void setThird_pay_type(final int third_pay_type) {
		this.third_pay_type = third_pay_type;
	}

	public int getPay_way() {
		return pay_way;
	}

	public void setPay_way(final int pay_way) {
		this.pay_way = pay_way;
	}

	public Date getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(final Date confirm_time) {
		this.confirm_time = confirm_time;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(final String contactee) {
		this.contactee = contactee;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(final String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(final String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(final double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(final int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(final int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(final long channel_id) {
		this.channel_id = channel_id;
	}

	public int getOnline_pay() {
		return online_pay;
	}

	public void setOnline_pay(final int online_pay) {
		this.online_pay = online_pay;
	}

}

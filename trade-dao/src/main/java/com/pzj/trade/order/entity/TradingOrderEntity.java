package com.pzj.trade.order.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.core.trade.voucher.entity.VoucherBriefEntity;
import com.pzj.trade.merch.entity.MerchEntity;

public class TradingOrderEntity {

	/** 订单商品 */
	private final List<MerchEntity> products = new ArrayList<MerchEntity>();

	/** 订单商品策略 */
	private final List<OrderStrategyEntity> strategies = new ArrayList<OrderStrategyEntity>();

	/**订单ID*/
	private String order_id;

	/**交易ID，此次级联订单所用的整个交易ID，多个交易相同*/
	private String transaction_id;

	/**上级交易ID*/
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

	/**旅行社部门*/
	private long travel_depart_id;

	/**旅行社部门*/
	private String depart = "";

	/**导游ID*/
	private long guide_id;

	/** 导游名称 */
	private String guider = "";

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，2否，默认1*/
	private int online_pay;

	/** 是否对接商品. 0: 否; 1: 是 */
	private int is_dock = 0;

	/**代下单标志：0:不需要代下单；1：需要代下单；2已经代下单*/
	@Deprecated
	private int agent_flag;

	/** 分销商代理商 */
	//private long reseller_agent_id;

	/**订单状态*/
	private int order_status = 1;

	/** 是否需要确认. 1: 不需要; 2: 需要;3:已确认 */
	private int confirm = 1;

	/** 二次确认时间*/
	private Date twice_confirm_time;
	/**订单总金额*/
	private BigDecimal total_amount = new BigDecimal(0);

	/**已退款总金额*/
	private BigDecimal refund_amount = new BigDecimal(0);

	/**订单包含的商品总数量*/
	private int total_num;

	/**已确认的商品数量*/
	private int checked_num;

	/**已退款的商品数量*/
	private int refund_num;

	/**1: 魔方向供应商采购订单; 2: 分销商向魔方购买订单; 3: 直签的单产品*/
	private int order_type;

	/**销售端口（APP, OTA, 微店）*/
	private int sale_port;

	/** 联系人 */
	private String contactee;

	/** 联系人电话 */
	private String contact_mobile;

	/** 联系人电话 */
	private String idcard_no;

	/**创建时间*/
	private Date create_time;

	/**支付时间*/
	private Date pay_time;

	/**第三方支付订单号*/
	private String third_code;

	/**支付类型*/
	private int third_pay_type;

	/**订单关闭时间（取消、已退款、已完成）*/
	private Date confirm_time;
	/**结算价,非政策结算价，是购买价-后返之和*/
	private BigDecimal settlement_price = new BigDecimal(0.0);

	/**付款锁. 0: 未支付; 1: 已锁定,2:支付成功*/
	private int pay_state;

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

	/**版本*/
	private int version;

	/**订单取消间隔时间*/
	private int cancelMinute = 30;

	/** 订单备注信息 */
	private RemarkEntity remark;

	/** 魔方码 */
	private MftourCodeEntity mfCode;

	private List<MftourCodeEntity> mfCodes;

	/** 填单项 */
	private List<OrderExtendAttrEntity> filleds;

	/** voucher */
	private List<VoucherBriefEntity> vouchers;

	public List<MftourCodeEntity> getMfCodes() {
		return mfCodes;
	}

	public void setMfCodes(List<MftourCodeEntity> mfCodes) {
		this.mfCodes = mfCodes;
	}

	public TradingOrderEntity() {
	}

	public TradingOrderEntity(final String orderId) {
		this.order_id = orderId;
		this.transaction_id = order_id;
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

	//	public long getSupplier_agent_id() {
	//		return supplier_agent_id;
	//	}
	//
	//	public void setSupplier_agent_id(long supplier_agent_id) {
	//		this.supplier_agent_id = supplier_agent_id;
	//	}

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

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(final long guide_id) {
		this.guide_id = guide_id;
	}

	//	public long getReseller_agent_id() {
	//		return reseller_agent_id;
	//	}
	//
	//	public void setReseller_agent_id(long reseller_agent_id) {
	//		this.reseller_agent_id = reseller_agent_id;
	//	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(final int order_status) {
		this.order_status = order_status;
	}

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(final int confirm) {
		this.confirm = confirm;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(final BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public BigDecimal getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(final BigDecimal refund_amount) {
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

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(final int is_direct) {
		this.is_direct = is_direct;
	}

	public int getOnline_pay() {
		return online_pay;
	}

	public void setOnline_pay(final int online_pay) {
		this.online_pay = online_pay;
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Date create_time) {
		this.create_time = create_time;
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

	public Date getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(final Date confirm_time) {
		this.confirm_time = confirm_time;
	}

	public List<MerchEntity> getProducts() {
		return products;
	}

	public List<OrderStrategyEntity> getStrategies() {
		return strategies;
	}

	public BigDecimal getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(final BigDecimal settlement_price) {
		this.settlement_price = settlement_price;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(final int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public Date getTwice_confirm_time() {
		return twice_confirm_time;
	}

	public void setTwice_confirm_time(final Date twice_confirm_time) {
		this.twice_confirm_time = twice_confirm_time;
	}

	public int getIs_dock() {
		return is_dock;
	}

	public void setIs_dock(final int is_dock) {
		this.is_dock = is_dock;
	}

	public RemarkEntity getRemark() {
		return remark;
	}

	public void setRemark(final RemarkEntity remark) {
		this.remark = remark;
	}

	public MftourCodeEntity getMfCode() {
		return mfCode;
	}

	public void setMfCode(final MftourCodeEntity mfCode) {
		this.mfCode = mfCode;
	}

	public List<OrderExtendAttrEntity> getFilleds() {
		return filleds;
	}

	public void setFilleds(final List<OrderExtendAttrEntity> filleds) {
		this.filleds = filleds;
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
	 * Getter method for property <tt>third_pay_type</tt>.
	 *
	 * @return property value of third_pay_type
	 */
	public int getThird_pay_type() {
		return third_pay_type;
	}

	/**
	 * Setter method for property <tt>third_pay_type</tt>.
	 *
	 * @param third_pay_type value to be assigned to property third_pay_type
	 */
	public void setThird_pay_type(final int third_pay_type) {
		this.third_pay_type = third_pay_type;
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
	 * Getter method for property <tt>depart</tt>.
	 *
	 * @return property value of depart
	 */
	public String getDepart() {
		return depart;
	}

	/**
	 * Setter method for property <tt>depart</tt>.
	 *
	 * @param depart value to be assigned to property depart
	 */
	public void setDepart(final String depart) {
		this.depart = depart == null ? "" : depart;
	}

	/**
	 * Getter method for property <tt>guider</tt>.
	 *
	 * @return property value of guider
	 */
	public String getGuider() {
		return guider;
	}

	/**
	 * Setter method for property <tt>guider</tt>.
	 *
	 * @param guider value to be assigned to property guider
	 */
	public void setGuider(final String guider) {
		this.guider = guider == null ? "" : guider;
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

	/**
	 * Getter method for property <tt>pay_way</tt>.
	 *
	 * @return property value of pay_way
	 */
	public int getPay_way() {
		return pay_way;
	}

	/**
	 * Setter method for property <tt>pay_way</tt>.
	 *
	 * @param pay_way value to be assigned to property pay_way
	 */
	public void setPay_way(final int pay_way) {
		this.pay_way = pay_way;
	}

	/**
	 * Getter method for property <tt>version</tt>.
	 *
	 * @return property value of version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Setter method for property <tt>version</tt>.
	 *
	 * @param version value to be assigned to property version
	 */
	public void setVersion(final int version) {
		this.version = version;
	}

	public int getCancelMinute() {
		return cancelMinute;
	}

	public void setCancelMinute(final int cancelMinute) {
		this.cancelMinute = cancelMinute;
	}

	/**
	 * Getter method for property <tt>p_order_id</tt>.
	 * 
	 * @return property value of p_order_id
	 */
	public String getP_order_id() {
		return p_order_id;
	}

	/**
	 * Setter method for property <tt>p_order_id</tt>.
	 * 
	 * @param p_order_id value to be assigned to property p_order_id
	 */
	public void setP_order_id(final String p_order_id) {
		this.p_order_id = p_order_id;
	}

	/**
	 * Getter method for property <tt>vouchers</tt>.
	 *
	 * @return property value of vouchers
	 */
	public List<VoucherBriefEntity> getVouchers() {
		return vouchers;
	}

	/**
	 * Setter method for property <tt>vouchers</tt>.
	 *
	 * @param vouchers value to be assigned to property vouchers
	 */
	public void setVouchers(final List<VoucherBriefEntity> vouchers) {
		this.vouchers = vouchers;
	}

}

package com.pzj.trade.merch.entity;

import java.sql.Timestamp;
import java.util.Date;

public class MerchListEntity {

	private String merch_id;
	private int merch_state;
	private String merch_name;
	private String sku_name;
	private long product_id;
	private long channel_id;
	private long strategy_id;
	private long voucher_id;
	private int total_num;
	private int merch_total_num;
	private int check_num;
	private int order_refund_num;
	private int refund_num;
	private int refunding_num;
	private double price;
	private double order_refund_amount;
	private double refund_amount;
	private String order_id;
	private String transaction_id;
	private String p_order_id;
	private int order_status;
	private Timestamp create_time;
	private Timestamp cancel_time;
	private Timestamp pay_time;
	private Timestamp confirm_time;
	private String product_varie; //团散
	private int merch_type;
	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;

	private double order_settlement_price;

	private Date update_time;

	//以下字段为商品对应的订单信息
	/**订单支付者的资金帐号*/
	private long payer_id;

	/**操作者*/
	private long operator_id;

	/**供应商ID*/
	private long supplier_id;

	/**订单总金额*/
	private double total_amount;

	/**已确认的商品数量*/
	private int checked_num;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;
	/** 联系人身份证号 */
	private String idcard_no;

	/**分销商ID(旅行社)*/
	private long reseller_id;

	/**销售端口（APP, OTA, 微店）*/
	private int sale_port;

	/**旅行社*/
	private long travel;

	/**旅行社部门*/
	private long travel_depart_id;

	/**导游ID*/
	private long guide_id;

	//是否需要确认. 1: 不需要; 2: 需要',
	private int need_confirm;

	/**代下单标志：1:不需要代下单；2：需要代下单；3已经代下单*/
	private int agent_flag;

	/**凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码 */
	private int vour_type;

	private int order_type;
	//是否退款中. 0是1否,
	private int is_refunding;
	//检票时间
	private Date check_time;

	/**是否已清算 0：未清算，1：已清算*/
	private int is_cleaned;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，2否，默认1*/
	private int online_pay;

	/** 出游/入住时间 */
	private Date start_time;
	/** 出游/入住结束时间 */
	private Date expire_time;

	/**'是否对接商品 0: 否; 1: 是'*/
	private int is_dock;
	/**退款状态(1，退款中 2，成功 3，失败)*/
	private int refund_state;
	/**退款流水id*/
	private String refund_id;

	private String root_merch_id;
	private int version;
	private int pay_state;

	public int getRefunding_num() {
		return refunding_num;
	}

	public void setRefunding_num(int refunding_num) {
		this.refunding_num = refunding_num;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public int getPay_state() {
		return pay_state;
	}

	public void setPay_state(int pay_state) {
		this.pay_state = pay_state;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRoot_merch_id() {
		return root_merch_id;
	}

	public void setRoot_merch_id(String root_merch_id) {
		this.root_merch_id = root_merch_id;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(final int refund_state) {
		this.refund_state = refund_state;
	}

	public int getIs_dock() {
		return is_dock;
	}

	public void setIs_dock(final int is_dock) {
		this.is_dock = is_dock;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(final Date start_time) {
		this.start_time = start_time;
	}

	public Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(final Date expire_time) {
		this.expire_time = expire_time;
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

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(final String merch_id) {
		this.merch_id = merch_id;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(final int merch_state) {
		this.merch_state = merch_state;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(final String merch_name) {
		this.merch_name = merch_name;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(final long product_id) {
		this.product_id = product_id;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(final long channel_id) {
		this.channel_id = channel_id;
	}

	public long getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(final long strategy_id) {
		this.strategy_id = strategy_id;
	}

	public long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(final long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(final int total_num) {
		this.total_num = total_num;
	}

	public int getCheck_num() {
		return check_num;
	}

	public void setCheck_num(final int check_num) {
		this.check_num = check_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(final int refund_num) {
		this.refund_num = refund_num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(final double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(final int order_status) {
		this.order_status = order_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(Timestamp cancel_time) {
		this.cancel_time = cancel_time;
	}

	public Timestamp getPay_time() {
		return pay_time;
	}

	public void setPay_time(final Timestamp pay_time) {
		this.pay_time = pay_time;
	}

	public Timestamp getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(final Timestamp confirm_time) {
		this.confirm_time = confirm_time;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(final int merch_type) {
		this.merch_type = merch_type;
	}

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(final String product_varie) {
		this.product_varie = product_varie;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(final double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(final Date update_time) {
		this.update_time = update_time;
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

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(final double total_amount) {
		this.total_amount = total_amount;
	}

	public int getChecked_num() {
		return checked_num;
	}

	public void setChecked_num(final int checked_num) {
		this.checked_num = checked_num;
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

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(final long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(final int sale_port) {
		this.sale_port = sale_port;
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

	public int getMerch_total_num() {
		return merch_total_num;
	}

	public void setMerch_total_num(final int merch_total_num) {
		this.merch_total_num = merch_total_num;
	}

	public int getVour_type() {
		return vour_type;
	}

	public void setVour_type(final int vour_type) {
		this.vour_type = vour_type;
	}

	public String getP_order_id() {
		return p_order_id;
	}

	public void setP_order_id(final String p_order_id) {
		this.p_order_id = p_order_id;
	}

	public double getOrder_settlement_price() {
		return order_settlement_price;
	}

	public void setOrder_settlement_price(final double order_settlement_price) {
		this.order_settlement_price = order_settlement_price;
	}

	public int getOrder_refund_num() {
		return order_refund_num;
	}

	public void setOrder_refund_num(final int order_refund_num) {
		this.order_refund_num = order_refund_num;
	}

	public double getOrder_refund_amount() {
		return order_refund_amount;
	}

	public void setOrder_refund_amount(final double order_refund_amount) {
		this.order_refund_amount = order_refund_amount;
	}

	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(final int order_type) {
		this.order_type = order_type;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(final int is_refunding) {
		this.is_refunding = is_refunding;
	}

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(final Date check_time) {
		this.check_time = check_time;
	}

	public int getIs_cleaned() {
		return is_cleaned;
	}

	public void setIs_cleaned(final int is_cleaned) {
		this.is_cleaned = is_cleaned;
	}

}

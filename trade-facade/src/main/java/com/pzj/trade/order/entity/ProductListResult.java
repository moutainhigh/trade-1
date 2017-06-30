package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ProductListResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String merch_id;
	private int merch_state;
	private String merch_name;
	private long product_id;
	private long channel_id;
	private long strategy_id;
	private long voucher_id;
	private int total_num;
	private int merch_total_num;
	private int check_num;
	private int refund_num;
	private double price;
	private double refund_amount;
	private String order_id;
	private int order_status;
	private Timestamp create_time;
	private Timestamp pay_time;
	private Timestamp confirm_time;
	private String product_varie; //团散
	private int merch_type;
	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;
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

	/**  订单类型 */
	@Deprecated
	private int category;

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

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public void setMerch_state(int merch_state) {
		this.merch_state = merch_state;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}

	public long getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(long strategy_id) {
		this.strategy_id = strategy_id;
	}

	public long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public int getCheck_num() {
		return check_num;
	}

	public void setCheck_num(int check_num) {
		this.check_num = check_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getPay_time() {
		return pay_time;
	}

	public void setPay_time(Timestamp pay_time) {
		this.pay_time = pay_time;
	}

	public Timestamp getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(Timestamp confirm_time) {
		this.confirm_time = confirm_time;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public int getChecked_num() {
		return checked_num;
	}

	public void setChecked_num(int checked_num) {
		this.checked_num = checked_num;
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

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
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

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public int getAgent_flag() {
		return agent_flag;
	}

	public void setAgent_flag(int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public int getMerch_total_num() {
		return merch_total_num;
	}

	public void setMerch_total_num(int merch_total_num) {
		this.merch_total_num = merch_total_num;
	}

	public int getVour_type() {
		return vour_type;
	}

	public void setVour_type(int vour_type) {
		this.vour_type = vour_type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (product_id ^ (product_id >>> 32));
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
		ProductListResult other = (ProductListResult) obj;
		if (product_id != other.product_id)
			return false;
		return true;
	}
}

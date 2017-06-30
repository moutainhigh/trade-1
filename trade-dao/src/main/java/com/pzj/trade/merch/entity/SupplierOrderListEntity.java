package com.pzj.trade.merch.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 供应商订单列表实体.
 * @author YRJ
 *
 */
public class SupplierOrderListEntity {

	private String merch_id;

	private int merch_state;

	private String merch_name;

	private long product_id;

	private int total_num;

	private int refund_num;

	private int check_num;

	private String order_id;

	private String p_order_id;

	private int order_status;

	private Timestamp create_time;

	private int merch_type;

	/**订单总金额*/
	private double total_amount;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contact_mobile;

	private int need_confirm;

	//是否退款中. 0是1否,
	private int is_refunding;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;

	/**直签是否需要线上支付，1是，2否，默认1*/
	private int online_pay;

	/** 出游/入住时间 */
	private Date start_time;

	/** 凭证ID */
	private long voucher_id;

	/**分销商id*/
	private long reseller_id;

	/**渠道id*/
	private long channel_id;

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	public int getOnline_pay() {
		return online_pay;
	}

	public void setOnline_pay(int online_pay) {
		this.online_pay = online_pay;
	}

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

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	public int getCheck_num() {
		return check_num;
	}

	public void setCheck_num(int check_num) {
		this.check_num = check_num;
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

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
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

	public int getNeed_confirm() {
		return need_confirm;
	}

	public void setNeed_confirm(int need_confirm) {
		this.need_confirm = need_confirm;
	}

	public String getP_order_id() {
		return p_order_id;
	}

	public void setP_order_id(String p_order_id) {
		this.p_order_id = p_order_id;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(int is_refunding) {
		this.is_refunding = is_refunding;
	}

	public long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(long voucher_id) {
		this.voucher_id = voucher_id;
	}
}

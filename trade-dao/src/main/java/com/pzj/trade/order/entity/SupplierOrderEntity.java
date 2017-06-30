package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商订单和商品信息
 *
 * @author Administrator
 * @version $Id: SupplierOrderEntity.java, v 0.1 2016年7月29日 下午3:09:59 Administrator Exp $
 */
public class SupplierOrderEntity implements Serializable {

	private static final long serialVersionUID = 4420770586224680188L;
	/**供应商订单ID*/
	private String supplier_order_id;

	/**分销商订单ID*/
	private String reseller_order_id;
	/**transaction_id*/
	private String transaction_id;

	/**订单版本*/
	private int version;

	/**订单状态*/
	private int order_status;

	/**创建时间*/
	private Date create_time;

	/**供应商ID*/
	private long supplier_id;

	/**产品ID*/
	private long product_id;

	/**支付时间*/
	private Date pay_time;

	/**订单支付者的资金帐号*/
	private long payer_id;

	/**第三方支付订单号*/
	private String third_code;

	/**第三方支付类型:0-余额，1-支付宝，2-微信*/
	private int third_pay_type;

	/**订单总金额*/
	private double total_amount;

	/**已退款总金额*/
	private double refund_amount;

	/**已确认的商品数量*/
	private int checked_num;

	/**已退款的商品数量*/
	private int refund_num;

	private String merch_id;
	private String root_merch_id;
	private String merch_name;
	private int merch_state;
	private long strategy_id;

	private int merch_total_num;
	private int merch_check_num;
	private int merch_refund_num;

	private double price;
	private double amount;

	private String product_varie;
	private int merch_type;

	private int is_refunding;

	private int pay_way;

	private int pay_state;

	public int getPay_state() {
		return pay_state;
	}

	public void setPay_state(int pay_state) {
		this.pay_state = pay_state;
	}

	public int getPay_way() {
		return pay_way;
	}

	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getIs_refunding() {
		return is_refunding;
	}

	public void setIs_refunding(int is_refunding) {
		this.is_refunding = is_refunding;
	}

	public String getSupplier_order_id() {
		return supplier_order_id;
	}

	public void setSupplier_order_id(String supplier_order_id) {
		this.supplier_order_id = supplier_order_id;
	}

	public String getReseller_order_id() {
		return reseller_order_id;
	}

	public void setReseller_order_id(String reseller_order_id) {
		this.reseller_order_id = reseller_order_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(long payer_id) {
		this.payer_id = payer_id;
	}

	public String getThird_code() {
		return third_code;
	}

	public void setThird_code(String third_code) {
		this.third_code = third_code;
	}

	public int getThird_pay_type() {
		return third_pay_type;
	}

	public void setThird_pay_type(int third_pay_type) {
		this.third_pay_type = third_pay_type;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public int getChecked_num() {
		return checked_num;
	}

	public void setChecked_num(int checked_num) {
		this.checked_num = checked_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	public int getMerch_total_num() {
		return merch_total_num;
	}

	public void setMerch_total_num(int merch_total_num) {
		this.merch_total_num = merch_total_num;
	}

	public int getMerch_check_num() {
		return merch_check_num;
	}

	public void setMerch_check_num(int merch_check_num) {
		this.merch_check_num = merch_check_num;
	}

	public int getMerch_refund_num() {
		return merch_refund_num;
	}

	public void setMerch_refund_num(int merch_refund_num) {
		this.merch_refund_num = merch_refund_num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(String merch_id) {
		this.merch_id = merch_id;
	}

	public String getRoot_merch_id() {
		return root_merch_id;
	}

	public void setRoot_merch_id(String root_merch_id) {
		this.root_merch_id = root_merch_id;
	}

	public String getMerch_name() {
		return merch_name;
	}

	public void setMerch_name(String merch_name) {
		this.merch_name = merch_name;
	}

	public long getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(long strategy_id) {
		this.strategy_id = strategy_id;
	}

	public int getMerch_state() {
		return merch_state;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public String getProduct_varie() {
		return product_varie;
	}

	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	public int getMerch_type() {
		return merch_type;
	}

	public void setMerch_type(int merch_type) {
		this.merch_type = merch_type;
	}

	public void setMerch_state(int merch_state) {
		this.merch_state = merch_state;
	}

}

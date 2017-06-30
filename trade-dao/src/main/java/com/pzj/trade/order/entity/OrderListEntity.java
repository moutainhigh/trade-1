package com.pzj.trade.order.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderListEntity {

	private String order_id;

	private String transaction_id;

	private int order_status;

	private int total_num;

	private int checked_num;

	private int refund_num;

	private BigDecimal total_amount;

	private BigDecimal refund_amount;

	private Timestamp create_time;
	
	private Timestamp confirm_time;

	//private List<ProductListResult> merchs = new ArrayList<ProductListResult>();

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
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

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public BigDecimal getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(BigDecimal refund_amount) {
		this.refund_amount = refund_amount;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(Timestamp confirm_time) {
		this.confirm_time = confirm_time;
	}

	//	public List<ProductListResult> getMerchs() {
	//		return merchs;
	//	}
	//
	//	public void setMerchs(List<ProductListResult> merchs) {
	//		this.merchs = merchs;
	//	}
}

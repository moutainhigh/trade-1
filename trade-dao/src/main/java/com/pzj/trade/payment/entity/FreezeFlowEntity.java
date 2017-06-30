package com.pzj.trade.payment.entity;

import java.io.Serializable;

public class FreezeFlowEntity implements Serializable {

	private static final long serialVersionUID = -8818767240570350213L;

	private long freeze_id;

	/** 订单id */
	private String order_id;

	private long payer_id;

	/** 支付类型 */
	private int receive_type;

	/** 支付冻结唯一流水号 */
	private String sign_id;

	/** 冻结状态 1：支付完毕 0：待支付 2 支付取消 */
	private int freeze_state;

	/** 余额支付金额 */
	private double balance_amount;

	/** 第三方支付金额 */
	private double third_amount;
	
	/**
	 * 第三方支付返回数据的详细信息
	 */
	private String third_content;

	public long getFreeze_id() {
		return freeze_id;
	}

	public void setFreeze_id(final long freeze_id) {
		this.freeze_id = freeze_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getSign_id() {
		return sign_id;
	}

	public void setSign_id(final String sign_id) {
		this.sign_id = sign_id;
	}

	public int getFreeze_state() {
		return freeze_state;
	}

	public void setFreeze_state(final int freeze_state) {
		this.freeze_state = freeze_state;
	}

	public double getBalance_amount() {
		return balance_amount;
	}

	public void setBalance_amount(final double balance_amount) {
		this.balance_amount = balance_amount;
	}

	public double getThird_amount() {
		return third_amount;
	}

	public int getReceive_type() {
		return receive_type;
	}

	public void setReceive_type(final int receive_type) {
		this.receive_type = receive_type;
	}

	public void setThird_amount(final double third_amount) {
		this.third_amount = third_amount;
	}

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(final long payer_id) {
		this.payer_id = payer_id;
	}

	public String getThird_content() {
		return third_content;
	}

	public void setThird_content(String third_content) {
		this.third_content = third_content;
	}

}

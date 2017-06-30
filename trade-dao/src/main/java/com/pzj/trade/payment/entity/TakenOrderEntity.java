package com.pzj.trade.payment.entity;

import java.io.Serializable;

public class TakenOrderEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4490704347171546371L;
	private long taken_id;
	private long account_id;
	private String order_id;
	private int taken_status;
	private String deal_id;
	private int pay_type;
	private double can_postal_money;
	private double postal_money;

	public long getTaken_id() {
		return taken_id;
	}

	public void setTaken_id(final long taken_id) {
		this.taken_id = taken_id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(final long account_id) {
		this.account_id = account_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public double getCan_postal_money() {
		return can_postal_money;
	}

	public int getPay_type() {
		return pay_type;
	}

	public void setPay_type(final int pay_type) {
		this.pay_type = pay_type;
	}

	public void setCan_postal_money(final double can_postal_money) {
		this.can_postal_money = can_postal_money;
	}

	public double getPostal_money() {
		return postal_money;
	}

	public String getDeal_id() {
		return deal_id;
	}

	public void setDeal_id(final String deal_id) {
		this.deal_id = deal_id;
	}

	public void setPostal_money(final double postal_money) {
		this.postal_money = postal_money;
	}

	public int getTaken_status() {
		return taken_status;
	}

	public void setTaken_status(final int taken_status) {
		this.taken_status = taken_status;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TakenOrderEntity [taken_id=");
		builder.append(taken_id);
		builder.append(", account_id=");
		builder.append(account_id);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", taken_status=");
		builder.append(taken_status);
		builder.append(", deal_id=");
		builder.append(deal_id);
		builder.append(", pay_type=");
		builder.append(pay_type);
		builder.append(", can_postal_money=");
		builder.append(can_postal_money);
		builder.append(", postal_money=");
		builder.append(postal_money);
		builder.append("]");
		return builder.toString();
	}

}

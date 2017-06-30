package com.pzj.trade.payment.vo;

import java.io.Serializable;

/**
 * 支付回调参数.
 * @author YRJ
 *
 */
public class PayCallbackVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**订单ID*/
	private String orderId;

	/**是否成功 1：success是确认；2：false是取消*/
	private boolean success;

	/**支付渠道*/
	private int payType;

	/**付款金额*/
	private double money;

	/**第三方交易流水号*/
	private String dealId;

	/**银行手续费*/
	private double bankCharges;

	/**支付宝账号*/
	private String seller_email;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return success;
	}
	/**支付渠道*/
	public int getPayType() {
		return payType;
	}
	/**支付渠道*/
	public void setPayType(final int payType) {
		this.payType = payType;
	}
	/**付款金额*/
	public double getMoney() {
		return money;
	}
	/**付款金额*/
	public void setMoney(final double money) {
		this.money = money;
	}
	/**第三方交易流水号*/
	public String getDealId() {
		return dealId;
	}
	/**第三方交易流水号*/
	public void setDealId(final String dealId) {
		this.dealId = dealId;
	}
	/**银行手续费*/
	public double getBankCharges() {
		return bankCharges;
	}
	/**银行手续费*/
	public void setBankCharges(final double bankCharges) {
		this.bankCharges = bankCharges;
	}
	/**支付宝账号*/
	public String getSeller_email() {
		return seller_email;
	}
	/**支付宝账号*/
	public void setSeller_email(final String seller_email) {
		this.seller_email = seller_email;
	}

	@Override
	public String toString() {
		return "PayCallbackVO [orderId=" + orderId + ", success=" + success + ", payType=" + payType + ", money="
				+ money + ", dealId=" + dealId + ", bankCharges=" + bankCharges + ", seller_email=" + seller_email
				+ "]";
	}
 

}

package com.pzj.core.trade.payment.engine.handler;

class AmountModel {

	private final double goodAmount;//货款支付金额
	private final double accountAmount;//余额支付金额

	AmountModel(final double goodAmount, final double accountAmount) {
		this.goodAmount = goodAmount;
		this.accountAmount = accountAmount;
	}

	public double getGoodAmount() {
		return goodAmount;
	}

	public double getAccountAmount() {
		return accountAmount;
	}
}

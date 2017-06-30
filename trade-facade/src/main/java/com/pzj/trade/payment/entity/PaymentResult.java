package com.pzj.trade.payment.entity;

import java.io.Serializable;

/**
 * 订单付款响应结果.
 * @author YRJ
 *
 */
public class PaymentResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**支付方式
	 * <li>1:支付宝</li>
	 * <li>2:微信</li>
	 */
	private int payType;

	/**
	 * 三方支付金额
	 */
	private double thirdPayMoney;

	/**
	 * 余额支付金额
	 */
	private double balancePayMoney;

	/**
	 * 支付宝支付<code>form</code>表单
	 */
	private String html;

	/**
	 * 微信支付相关参数
	 */
	private WeChatResult weChatResult;

	/**
	 * 支付宝APP支付加密数据
	 */
	private String aliPaySign;

	public int getPayType() {
		return payType;
	}

	public void setPayType(final int payType) {
		this.payType = payType;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(final String html) {
		this.html = html;
	}

	public WeChatResult getWeChatResult() {
		return weChatResult;
	}

	public void setWeChatResult(final WeChatResult weChatResult) {
		this.weChatResult = weChatResult;
	}

	public double getThirdPayMoney() {
		return thirdPayMoney;
	}

	public void setThirdPayMoney(final double thirdPayMoney) {
		this.thirdPayMoney = thirdPayMoney;
	}

	public double getBalancePayMoney() {
		return balancePayMoney;
	}

	public void setBalancePayMoney(final double balancePayMoney) {
		this.balancePayMoney = balancePayMoney;
	}

	public String getAliPaySign() {
		return aliPaySign;
	}

	public void setAliPaySign(final String aliPaySign) {
		this.aliPaySign = aliPaySign;
	}

}

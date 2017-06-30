package com.pzj.core.trade.order.engine.common;

import com.pzj.core.trade.payment.engine.exception.PaymentTypeException;

/**
 * 支付方式枚举.
 * @author YRJ
 *
 */
public class PayWayEnum {

	//0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付
	/**
	 * 纯余额支付.
	 */
	public final static PayWayEnum BALANCE = new PayWayEnum(0, "余额", true);

	/**
	 * 支付宝.
	 */
	public final static PayWayEnum ALIPAY = new PayWayEnum(1, "支付宝", true);

	/**
	 * 微信.
	 */
	public final static PayWayEnum WECHART = new PayWayEnum(2, "微信", true);

	/**
	 * 混合支付.
	 */
	public final static PayWayEnum MIXED = new PayWayEnum(4, "混合支付", true);

	/**
	 * 现金.
	 */
	public final static PayWayEnum CASH = new PayWayEnum(5, "现金", false);

	/**
	 * 后付.
	 */
	public final static PayWayEnum AFTER = new PayWayEnum(6, "签单", false);
	/**
	 * 账款
	 */
	public final static PayWayEnum ACCOUNT = new PayWayEnum(7, "账款", false);

	/**
	 * 支付方式池.
	 */
	private final static PayWayEnum[] PAY_WAY = { BALANCE, ALIPAY, WECHART, MIXED, CASH, AFTER, ACCOUNT };

	private final int payWay;

	private final String message;

	private final boolean online;

	public PayWayEnum(final int payWay, final String message, final boolean online) {
		this.payWay = payWay;
		this.message = message;
		this.online = online;
	}

	public int getPayWay() {
		return payWay;
	}

	public String getMessage() {
		return message;
	}

	public static PayWayEnum getPayWay(final int payWay) {
		PayWayEnum _payWay = null;
		for (final PayWayEnum way : PAY_WAY) {
			if (way.getPayWay() == payWay) {
				_payWay = way;
			}
		}
		if (_payWay == null) {
			throw new PaymentTypeException("支付类型错误, payWay: " + (payWay));
		}
		return _payWay;
	}

	/**
	 * 是否需要线上支付.
	 * @return
	 */
	public boolean isOnline() {
		return online;
	}
}

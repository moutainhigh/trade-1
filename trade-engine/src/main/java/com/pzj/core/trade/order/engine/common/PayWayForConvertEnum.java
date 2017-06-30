package com.pzj.core.trade.order.engine.common;

import com.pzj.core.trade.payment.engine.exception.PaymentTypeException;

/**
 * 支付方式给前端和后端转换的枚举.
 * 订单查询出参入参专门使用  请注意勿用错 
 * @author GLG
 *
 */
public class PayWayForConvertEnum {

	//支付方式. 1：第三方/余额 2：签单 3：现金

	//0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付
	/**
	 * 第三方/余额.
	 */
	public final static PayWayForConvertEnum BALANCEORTHIRDPAY = new PayWayForConvertEnum(1, "第三方/余额", "0,1,2,4");

	/**
	 * 现金.
	 */
	public final static PayWayForConvertEnum CASH = new PayWayForConvertEnum(3, "现金", "5");

	/**
	 * 后付.
	 */
	public final static PayWayForConvertEnum AFTER = new PayWayForConvertEnum(2, "签单", "6");

	/**
	 * 支付方式池.
	 */
	private final static PayWayForConvertEnum[] PAY_WAY = { BALANCEORTHIRDPAY, CASH, AFTER };

	private final int convertPayWay;

	private final String message;
	private final String payWay;

	public PayWayForConvertEnum(int convertPayWay, String message, String payWay) {
		this.convertPayWay = convertPayWay;
		this.message = message;
		this.payWay = payWay;
	}

	public int getConvertPayWay() {
		return convertPayWay;
	}

	public String getPayWay() {
		return payWay;
	}

	public String getMessage() {
		return message;
	}

	public static PayWayForConvertEnum getPayWay(int convertPayWay) {
		PayWayForConvertEnum _payWay = null;
		for (PayWayForConvertEnum way : PAY_WAY) {
			if (way.getConvertPayWay() == convertPayWay) {
				_payWay = way;
			}
		}
		if (_payWay == null) {
			throw new PaymentTypeException("转换支付类型错误, convertPayWay: " + (convertPayWay));
		}
		return _payWay;
	}

	public static PayWayForConvertEnum getConvertPayWay(String payWay) {
		PayWayForConvertEnum _payWay = null;
		for (PayWayForConvertEnum way : PAY_WAY) {
			if (way.getPayWay().indexOf(payWay) != -1) {
				_payWay = way;
			}
		}
		if (_payWay == null) {
			throw new PaymentTypeException("支付类型错误, payWay: " + (payWay));
		}
		return _payWay;
	}
}

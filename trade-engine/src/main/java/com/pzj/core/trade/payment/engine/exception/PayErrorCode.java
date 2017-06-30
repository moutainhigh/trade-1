package com.pzj.core.trade.payment.engine.exception;

public class PayErrorCode {

	/**支付请求参数错误*/
	public static final int REQ_PARAM_ERROR_CODE = 10101;

	/**支付订单不存在*/
	public static final int PAY_ORDER_IS_NONE_ERROR_CODE = 10102;

	/**支付订单不是销售订单*/
	public static final int PAY_ORDER_IS_PRUCHED_ERROR_CODE = 10103;

	/**订单状态不可支付*/
	public static final int UNABLE_PAY_ORDER_ERROR_CODE = 10104;

	/**支付账户冻结异常*/
	public static final int PAY_ACCOUND_FROZEN_ERROR_CODE = 10105;

	/**支付系统支付异常*/
	public static final int PAYMENT_PAY_ERROR_CODE = 10108;

	/**支付账号确认异常*/
	public static final int PAY_ACCOUNT_CONFIRM_ERROR_CODE = 10106;

	/**支付回调请求参数错误*/
	public static final int PAY_CALLBACK_REQ_PARAM_ERROR_CODE = 10107;

	/**订单支付记录不存在错误*/
	public static final int ORDER_PAY_FLOW_IS_NONE_ERROR_CODE = 10109;

	/**订单支付金额不足错误*/
	public static final int ORDER_PAY_AMOUND_NOT_ENOUGH_ERROR_CODE = 10110;

	/**账户余额不足错误*/
	public static final int BALANCE_NOT_ENOUGH_ERROR_CODE = 10108;
	
	/**支付取消业务异常*/
	public static final int PAYMENT_CANCEL_ERROR_CODE = 10109;

}

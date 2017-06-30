package com.pzj.core.trade.confirm.common;

/**
 * 核销异常码定义
 *
 * @author DongChunfu
 * @version $Id: ConfirmErrorCode.java, v 0.1 2017年2月27日 下午5:11:21 DongChunfu Exp $
 */
public class ConfirmErrorCode {

	/** 核销服务错误 */
	public static final int CONFIRM_ERROR = 10606;

	/** 请求参数错误 */
	public static final int REQ_PARAM_ERROR_CODE = 10601;

	/** 核销订单不存在 */
	public static final int CONFIRM_ORDER_NOT_EXIST_ERROR_CODE = 10602;

	/** 核销凭证不存在 */
	public static final int CONFIRM_VOUCHER_NOT_EXIST_ERROR_CODE = 10603;

	/** 退款中商品不允许核销 */
	public static final int REFUNDING_CAN_NOT_CONFIRM_ERROR_CODE = 10604;

	/** 退款的商品不允许核销 */
	public static final int REFUNDED_CAN_NOT_CONFIRM_ERROR_CODE = 10605;

	/** 待确认商品不允许核销 */
	public static final int WAITING_CAN_NOT_CONFIRM_ERROR_CODE = 10606;

	/** 逾期的凭证不允许正常核销 */
	public static final int NORMAL_CONFIRM_ERROR_CODE = 10607;

	/** 未逾期的凭证不允许自动核销 */
	public static final int AUTO_CONFIRM_ERROR_CODE = 10608;

	/** 检票点异常 */
	public static final int CHECK_TICKET_POINT_ERROR_CODE = 10609;

	/** 检票点异常 */
	public static final int CONFIRM_CODE_POINT_ERROR_CODE = 10610;

	/** 分账异常 */
	public static final int SPLIT_ACCOUNT_ERROR_CODE = 10611;

	/** 凭证状态异常 */
	public static final int VOUCHER_STATE_ERROR_CODE = 10612;

}

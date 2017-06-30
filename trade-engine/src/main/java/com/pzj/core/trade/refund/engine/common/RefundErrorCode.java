package com.pzj.core.trade.refund.engine.common;

/**
 * 退款异常码定义
 *
 * @author DongChunfu
 * @date 2016年12月2日
 */
public class RefundErrorCode {

	/** 退款服务错误 */
	public static final int REFUND_ERROR = 10503;

	/** 请求参数错误 */
	public static final int REQ_PARAM_ERROR_CODE = 10301;

	/** 退款商品不存在 */
	public static final int SELLORDER_MERCHES_IS_NONE_ERROR_CODE = 10302;

	/** 重复的退款商品 */
	public static final int REPETITIVE_REFUND_MERCH_ERROR_CODE = 10303;

	/** 退款商品不属于当前订单 */
	public static final int ORDER_NOT_CONTAIN_MERCH_ERROR_CODE = 10304;

	/** 退款申请订单不存在 */
	public static final int ORDER_NOT_FOUND_ERROR_CODE = 10305;

	/** 退款订单不是销售订单 */
	public static final int REFUND_IS_NOT_SELLER_ORDER_ERROR_CODE = 10306;

	/** 退款订单状态未支付 */
	public static final int REFUND_ORDER_STATE_ERROR_CODE = 10307;

	/** 退款操作间隔不足1分钟 */
	public static final int REFUND_OPERATION_INTERVAL_ERROR_CODE = 10308;

	/** 商品状态退款触发方错误 */
	public static final int MERCH_STATE_REFUND_TRIGGE_ERROR_CODE = 10309;

	/** 商品状态不可退 */
	public static final int MERCH_STATE_UNREFUNDABLE_ERROR_CODE = 10310;

	/** 退款数量错误 */
	public static final int REFUND_NUM_ERROR_CODE = 10311;

	/** 查询退款规则失败 */
	public static final int QUERY_REFUND_RULE_ERROR_CODE = 10312;

	/** 未找到退款规则 */
	public static final int REFUND_RULE_NOT_FOUND_ERROR_CODE = 10313;

	/** 商品核销凭证为空 */
	public static final int VOUCHER_NOT_FOUND_ERROR_CODE = 10315;

	/** 当前时间点不在退款范围内 */
	public static final int REFUND_DATE_NOT_INRANGE_ERROR_CODE = 10316;

	/** 当前时间点前不可退 */
	public static final int CAN_NOT_REFUND_BEFORE_DATE_ERROR_CODE = 10317;

	/** 当前不可以进行部分退款 */
	public static final int PART_REFUND_ERROR_CODE = 10318;

	/** 当前时间点后不可退 */
	public static final int CAN_NOT_REFUND_AFTER_DATE_ERROR_CODE = 10319;

	/** 清结算强制退款验证失败 */
	public static final int SETTLEMENT_FORCE_REFUND_VALIDATE_ERROR_CODE = 10320;

	/** 清结算账号服务异常码 */
	public static final int SETTLEMENT_ACCOUNT_BUSSINESS_ERROR_CODE = 10321;

	/** 对接确认退款异常 */
	public static final int DOCK_CONFIRM_REFUND_ERROR_CODE = 10314;

	/** 支付系统退款失败 */
	public static final int PAYMENT_REFUND_ERROR_CODE = 10322;

	/** 退款流水为空 */
	public static final int REFUND_FLOWS_IS_NONE_ERROR_CODE = 10323;

	/** 退款唯一标识 */
	public static final int REFUND_SIGN_IS_NONE_ERROR_CODE = 10324;

	/** 退款回滚异常 */
	public static final int REFUND_ROLLBACK_ERROR_CODE = 10325;

	/** 退款的订单不存在 */
	public static final int REFUND_ORDER_NOT_FOUND_ERROR_CODE = 10326;

	/** 对接审核结果异常 */
	public static final int DOCK_AUDIT_RESULT_ERROR_CODE = 10325;

	/** 退款流水状态错误 */
	public static final int REFUND_FLOW_STATE_ERROR_CODE = 10326;

	/** 退款状态审核方错误 */
	public static final int REFUND_STATE_AUDIT_PARTY_ERROR_CODE = 10327;

	/** 退款规则node为空 */
	public static final int REFUND_RULE_NODES_IS_NONE_ERROR_CODE = 10328;

	/** 冻结预清算记录流水异常码 */
	public static final int REFUND_FROZEN_CLEAN_FLOW_ERROR_CODE = 10329;

	/** 商品运价异常 */
	public static final int MERCH_CALCULAT_PRICE_ERROR_CODE = 10330;

	/** 清结算强制退款回调异常 */
	public static final int SETTL_FORCE_REFUND_CALLBACK_ERROR_CODE = 10331;

	/** 清结算退款回调异常 */
	public static final int SETTL_REFUND_CALLBACK_ERROR_CODE = 10332;

	/** 退款商品采购订单不存在 */
	public static final int PURCH_MERCH_NOT_FOUND_ERROR_CODE = 10333;

	/** 退款金额类型错误 */
	public static final int REFUND_FEE_TYPE_ERROR_CODE = 10334;

	/** 平台发起的普通退已消费商品申请状态不一致 */
	public static final int REFUND_MERCH_STATE_INCOMFORMITY_ERROR_CODE = 10335;

}

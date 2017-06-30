package com.pzj.trade.constant;

public class TradeConstant {
	/**
	 * 平台资金帐号（魔方的资金帐号）
	 */
	public static final long PLATFORM_ACCOUNT = 123456789;

	/**
	 * 帐号对应的钱包个数
	 */
	//public static final int  WALLET_SIZE          = 3;

	/**  操作间隔,防止重复提交*/
	public static final int OPERATION_INTERVAL = 60;

	/**  支付二维码过期时间 单位分钟*/
	public static final int QR_CODE_EXPIRED_TIME = 30;

	public static final long WSHOP_PAYER = 967470;

	/**
	 * 订单默认的取消时间（分钟）
	 */
	public static final int ORDER_CANCEL_MINUTES = 30;
}

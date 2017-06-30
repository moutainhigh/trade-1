package com.pzj.core.trade.order.engine.common;

public class PayStateEnum {
		//0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付
		/**
		 * 纯余额支付.
		 */
		public final static PayStateEnum UNAPY = new PayStateEnum(0, "未支付");

		/**
		 * 支付宝.
		 */
		public final static PayStateEnum ONLOCK = new PayStateEnum(1, "已锁定");

		/**
		 * 微信.
		 */
		public final static PayStateEnum PAYFINISH = new PayStateEnum(2, "支付成功");
		private PayStateEnum(final int payState, final String message) {
			this.payState = payState;
			this.message = message;
		}
		private final int payState;
		private final String message;
		public int getPayState() {
			return payState;
		}
		public String getMessage() {
			return message;
		}
}

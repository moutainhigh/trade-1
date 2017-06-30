package com.pzj.core.trade.context.common;

import com.pzj.core.trade.context.exe.MerchCleanedExecutor;
import com.pzj.core.trade.context.exe.OrderBaseExecutor;
import com.pzj.core.trade.context.exe.OrderPaymentedExecutor;
import com.pzj.core.trade.context.exe.OrderRefundedExecutor;
import com.pzj.core.trade.context.exe.VoucherSpendedExecutor;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;
import com.pzj.core.trade.context.exe.base.TradeExecutor;

public enum TradeTaskEnum {

	orderCreated(1, "订单相关任务", OrderBaseExecutor.class), orderPaymented(2, "订单支付的相关任务", OrderPaymentedExecutor.class), ordeRefunded(
			3, "订单退款的相关任务", OrderRefundedExecutor.class), voucherSpended(4, "凭证核销的相关任务", VoucherSpendedExecutor.class), merchClened(
			5, "商品清算的相关任务", MerchCleanedExecutor.class);
	@SuppressWarnings("unchecked")
	private TradeTaskEnum(final int key, final String msg,
			final Class<? extends TradeExecutor<? extends ExecuteBaseModel>> targetClass) {
		this.targetClass = (Class<? extends TradeExecutor<ExecuteBaseModel>>) targetClass;
		this.key = key;
		this.msg = msg;
	}

	private int key;
	private String msg;
	private Class<? extends TradeExecutor<ExecuteBaseModel>> targetClass;

	public int getKey() {
		return key;
	}

	public Class<? extends TradeExecutor<ExecuteBaseModel>> getTargetClass() {
		return targetClass;
	}

	public String getMsg() {
		return msg;
	}
}

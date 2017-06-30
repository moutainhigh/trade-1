package com.pzj.core.trade.context.model;

import com.pzj.core.trade.context.common.TradeTaskEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;

public class PaymentModel extends ExecuteBaseModel {

	public PaymentModel() {
		super.setTask(TradeTaskEnum.orderPaymented);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7438027543687857532L;

	/**
	 * 1 支付申请,2支付成功确认,3支付失败
	 */
	private int paymentSceneType = 1;

	public int getPaymentSceneType() {
		return paymentSceneType;
	}

	public void setPaymentSceneType(int paymentSceneType) {
		this.paymentSceneType = paymentSceneType;
	}

}

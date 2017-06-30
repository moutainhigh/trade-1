package com.pzj.core.trade.clean.engine.model;

/**
 * 生成商品的待清算信息的辅助参数
 * @author kangzl
 *
 */
public class MerchCleanAssistModel {
	/**
	 * 订单是否需要线上支付，1是，0否
	 */
	private int isOrderPaied = 1;

	/**
	 * 退款强制退款标识（0普通退款；1强制退款'）
	 */
	private int isForceRefund = 0;

	/**
	 * 是否是整单退款的标识   0 部分退款 1
	 */
	private int isNottotalRefund = 0;

	/**
	 * 退款的发起方,则要输入对应的退款发起的类型（平台是退款发起方(1,用户发起;2, 平台发起;3, 二次确认拒绝 -1未知类型) 
	 */
	public int refundResouce = -1;

	public int getIsOrderPaied() {
		return isOrderPaied;
	}

	public void setIsOrderPaied(int isOrderPaied) {
		this.isOrderPaied = isOrderPaied;
	}

	public int getIsForceRefund() {
		return isForceRefund;
	}

	public void setIsForceRefund(int isForceRefund) {
		this.isForceRefund = isForceRefund;
	}

	public int getIsNottotalRefund() {
		return isNottotalRefund;
	}

	public void setIsNottotalRefund(int isNottotalRefund) {
		this.isNottotalRefund = isNottotalRefund;
	}

	public int getRefundResouce() {
		return refundResouce;
	}

	public void setRefundResouce(int refundResouce) {
		this.refundResouce = refundResouce;
	}

}

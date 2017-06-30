package com.pzj.core.trade.context.model;

import com.pzj.core.trade.context.common.TradeTaskEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;

public class RefundModel extends ExecuteBaseModel {
	public RefundModel() {
		super.setTask(TradeTaskEnum.ordeRefunded);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3382079898999102175L;
	private String refundId;
	/**
	 * 1 退款申请,2退款成功确认,3退款失败拒绝
	 */
	private int refundSceneType = 1;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public int getRefundSceneType() {
		return refundSceneType;
	}

	public void setRefundSceneType(int refundSceneType) {
		this.refundSceneType = refundSceneType;
	}
}

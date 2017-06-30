package com.pzj.core.trade.context.model;

import com.pzj.core.trade.context.common.TradeTaskEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;

public class ConsumerModel extends ExecuteBaseModel {
	public ConsumerModel() {
		super.setTask(TradeTaskEnum.voucherSpended);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4536321133888049589L;
	private long voucherId;

	/**
	 * 核销场景信息
	 */
	private int consumerSceneType = 1;

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public int getConsumerSceneType() {
		return consumerSceneType;
	}

	public void setConsumerSceneType(int consumerSceneType) {
		this.consumerSceneType = consumerSceneType;
	}
}

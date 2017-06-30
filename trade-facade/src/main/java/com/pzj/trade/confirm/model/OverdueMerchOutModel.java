package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 逾期商品输出模型.
 * @author YRJ
 *
 */
public class OverdueMerchOutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private long voucherId;

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final long voucherId) {
		this.voucherId = voucherId;
	}
}

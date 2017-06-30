package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 订单详情查询参数.
 * @author CHJ
 *
 */
public class OrderMerchConfirmsReqModel implements Serializable {
	private static final long serialVersionUID = 1L;
	/**核销凭证id*/
	private long voucherId;
	/**产品id*/
	private long product_id;

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

}

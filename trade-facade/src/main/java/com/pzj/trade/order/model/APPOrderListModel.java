package com.pzj.trade.order.model;

import com.pzj.framework.entity.PageableRequestBean;

public class APPOrderListModel extends PageableRequestBean {

	private static final long serialVersionUID = 1L;

	/** 分销商Id*/
	private long reseller_id;

	/** 订单状态 */
	private int order_status;

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

}

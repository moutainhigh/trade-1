package com.pzj.trade.financeCenter.request;

import java.io.Serializable;

/**
 * 用户统计数据请求参数.
 * @author GLG
 *
 */
public class OrderStatisticsReqModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -5237976620636213271L;
	/** 分销商Id*/
	private long resellerId;

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

}

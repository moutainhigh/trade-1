package com.pzj.trade.financeCenter.response;

import java.io.Serializable;

/**
 * 供应商订单列表响应模型.
 * @author YRJ
 *
 */
public class OrderStatisticsRespModel implements Serializable {
	/**  */
	private static final long serialVersionUID = 1363266189059727509L;

	/**分销补差总金额*/
	private double differencePayTotalAmount;

	public double getDifferencePayTotalAmount() {
		return differencePayTotalAmount;
	}

	public void setDifferencePayTotalAmount(double differencePayTotalAmount) {
		this.differencePayTotalAmount = differencePayTotalAmount;
	}

}

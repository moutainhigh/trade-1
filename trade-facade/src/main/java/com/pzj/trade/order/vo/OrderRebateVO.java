/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.pzj.framework.toolkit.Check;

/**
 * 订单返利明细表实体
 *
 * @author DongChunfu
 * @version $Id: OrderRebateVO.java, v 0.1 2017年3月24日 下午3:52:12 DongChunfu Exp $
 */
public class OrderRebateVO implements Serializable, Cloneable {

	private static final long serialVersionUID = -1146652362017793422L;

	/**返利金额*/
	private Double rebateAmount;
	/**
	 * 返利方式(后返)
	 * <li>1:立返</li>
	 * <li>2：周返 </li>
	 */
	private int rebateWay;

	public OrderRebateVO() {
		super();
	}

	private Double precisionHandle(final Double d) {
		return Check.NuNObject(d) ? null : new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public Double getRebateAmount() {
		return precisionHandle(rebateAmount);
	}

	public void setRebateAmount(final Double rebateAmount) {
		this.rebateAmount = precisionHandle(rebateAmount);
	}

	public int getRebateWay() {
		return rebateWay;
	}

	public void setRebateWay(final int rebateWay) {
		this.rebateWay = rebateWay;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderRebateVO [rebateAmount=");
		builder.append(rebateAmount);
		builder.append(", rebateWay=");
		builder.append(rebateWay);
		builder.append("]");
		return builder.toString();
	}

}

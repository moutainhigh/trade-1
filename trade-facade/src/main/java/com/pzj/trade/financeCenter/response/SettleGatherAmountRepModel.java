/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.financeCenter.response;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 相对当前用户结算汇总信息
 *
 * @author DongChunfu
 * @version $Id: SettleGatherAmountRepModel.java, v 0.1 2017年5月15日 下午1:45:56 DongChunfu Exp $
 */
public class SettleGatherAmountRepModel implements Serializable {
	private static final long serialVersionUID = 6771290951177579404L;

	/**分销商角色支付总额*/
	private double resellerRolePayAmount;
	/**供应商角色支付总额*/
	private double supplierRolePayAmount;

	/**分销商角色收入总额*/
	private double resellerRoleReceiptAmount;
	/**供应商角色支付总额*/
	private double supplierRoleReceiptAmount;

	/**查询时间范围内，与当前用户发生交易行为的相对用户ID集合*/
	private List<Long> relativeUserIds;

	public SettleGatherAmountRepModel() {
		super();
	}

	public double getResellerRolePayAmount() {
		return amountFormat(resellerRolePayAmount);
	}

	public void setResellerRolePayAmount(final double resellerRolePayAmount) {
		this.resellerRolePayAmount = resellerRolePayAmount;
	}

	public double getSupplierRolePayAmount() {
		return amountFormat(supplierRolePayAmount);
	}

	public void setSupplierRolePayAmount(final double supplierRolePayAmount) {
		this.supplierRolePayAmount = supplierRolePayAmount;
	}

	public double getResellerRoleReceiptAmount() {
		return amountFormat(resellerRoleReceiptAmount);
	}

	public void setResellerRoleReceiptAmount(final double resellerRoleReceiptAmount) {
		this.resellerRoleReceiptAmount = resellerRoleReceiptAmount;
	}

	public double getSupplierRoleReceiptAmount() {
		return amountFormat(supplierRoleReceiptAmount);
	}

	public void setSupplierRoleReceiptAmount(final double supplierRoleReceiptAmount) {
		this.supplierRoleReceiptAmount = supplierRoleReceiptAmount;
	}

	public List<Long> getRelativeUserIds() {
		return relativeUserIds;
	}

	public void setRelativeUserIds(final List<Long> relativeUserIds) {
		this.relativeUserIds = relativeUserIds;
	}

	private double amountFormat(double amount) {
		DecimalFormat df = new DecimalFormat("#.00");
		String formatAmount = df.format(amount);
		return Double.valueOf(formatAmount);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SettleGatherRepModel [resellerRolePayAmount=");
		builder.append(resellerRolePayAmount);
		builder.append(", supplierRolePayAmount=");
		builder.append(supplierRolePayAmount);
		builder.append(", resellerRoleReceiptAmount=");
		builder.append(resellerRoleReceiptAmount);
		builder.append(", supplierRoleReceiptAmount=");
		builder.append(supplierRoleReceiptAmount);
		builder.append("]");
		return builder.toString();
	}

}

package com.pzj.core.trade.refund.engine.model;

import java.math.BigDecimal;

/**
 * 产品的退款金额计算模型
 * @author kangzl
 *
 */
public final class MerchCalculateModel {
	/**
	 * 退款金额类型, {@link RefundRuleLimit.prerefundDistributorFeetype}.
	 */
	private final int refundFeetype;

	private final int refundFeevalue;
	/**
	 * 退款规则类型.
	 * <ul>
	 * <li>0: 未使用规则</li>
	 * <li>1: 时间点前规则</li>
	 * <li>2: 时间点后规则</li>
	 * <li>3: 任意时间可退</li>
	 * </ul>
	 * 
	 */
	private int refundRuleType;

	public MerchCalculateModel(final int refundFeevalue, int refundRuleType) {
		this(1, refundFeevalue, refundRuleType);
	}

	public MerchCalculateModel(final int refundFeetype, final int refundFeevalue, int refundRuleType) {
		this.refundFeetype = refundFeetype;
		this.refundFeevalue = refundFeevalue;
		this.refundRuleType = refundRuleType;
	}

	public int getRefundFeetype() {
		return refundFeetype;
	}

	public int getRefundFeevalue() {
		return refundFeevalue;
	}

	/**
	 * 计算退款金额.
	 * @param price
	 * @return
	 */
	public double refundPrice(final double price) {
		if (refundFeetype == 1) {
			final BigDecimal bd = new BigDecimal(refundFeevalue / 10000D * price);
			return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		// 2,固定金额
		if (refundFeetype == 2) {
			return refundFeevalue / 100D;
		}

		// 3,全额退款
		if (refundFeetype == 3) {
			return price;
		}

		// 4,不退款
		if (refundFeetype == 4) {
			return 0.0d;
		}
		return 0.0;
	}

	public int getRefundRuleType() {
		return refundRuleType;
	}

	public void setRefundRuleType(int refundRuleType) {
		this.refundRuleType = refundRuleType;
	}
}

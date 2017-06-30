package com.pzj.core.trade.order.engine.model;

import java.math.BigDecimal;

/**
 * 金额转换器.
 * @author YRJ
 *
 */
public final class AmountConverter {

	/**
	 * 将分为单位的金额转换为以元为单位的金额.
	 * @param divide
	 * @return
	 */
	public final static double unitDivideToYuan(long divide) {
		final int MULTIPLIER = 100;
		BigDecimal yuan = new BigDecimal(String.valueOf(divide)).divide(new BigDecimal(MULTIPLIER)).setScale(2);
		return yuan.doubleValue();
	}

	public final static double unitDivideToYuan(double divide) {
		final int MULTIPLIER = 100;
		BigDecimal yuan = new BigDecimal(String.valueOf(divide)).divide(new BigDecimal(MULTIPLIER)).setScale(2);
		return yuan.doubleValue();
	}

	//	public static void main(String[] args) {
	//		long divide = 1;
	//		System.out.println(unitDivideToYuan(divide));
	//		divide = 10;
	//		System.out.println(unitDivideToYuan(divide));
	//		divide = 100;
	//		System.out.println(unitDivideToYuan(divide));
	//		divide = 1000;
	//		System.out.println(unitDivideToYuan(divide));
	//		divide = 10000;
	//		System.out.println(unitDivideToYuan(divide));
	//		divide = 1201;
	//		System.out.println(unitDivideToYuan(divide));
	//		divide = 201;
	//		System.out.println(unitDivideToYuan(divide));
	//	}

	private AmountConverter() {
		throw new AssertionError("Uninstantiable class.");
	}
}

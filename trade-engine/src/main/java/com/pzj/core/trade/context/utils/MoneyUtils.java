package com.pzj.core.trade.context.utils;

import java.math.BigDecimal;

public class MoneyUtils {

	/**
	 * 将单价进行转化
	 * @param number
	 * @return
	 */
	public static double getMoenyNumber(double number) {
		BigDecimal b = new BigDecimal(number);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.util;

import java.math.BigDecimal;

import com.pzj.framework.toolkit.Check;

/**
 * double 数据处理工具
 *
 * @author DongChunfu
 * @version $Id: DoubleUtil.java, v 0.1 2017年5月24日 下午12:01:42 DongChunfu Exp $
 */
public class DoubleUtil {
	/**
	 * 精度处理工具
	 *
	 * @param d 待处理数据
	 * @param p 进度
	 * @return d 为<code>null</code>返回0；p默认精度为2(四舍五入)；
	 *
	 */
	public static final double precisionHandle(final Double d, int p) {
		p = p < 0 ? 2 : p;
		return Check.NuNObject(d) ? 0D : new BigDecimal(d).setScale(p, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}

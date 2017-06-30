/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.exception;

/**
 * 财务中心错误码
 *
 * @author DongChunfu
 * @version $Id: FinanceErrorCode.java, v 0.1 2017年5月15日 上午11:48:34 DongChunfu Exp $
 */
public class FinanceErrorCode {
	public static final int FINANCE_ERROR = 10901;

	public static final int FINANCE_GATHER_ERROR = 10902;

	public static final int FINANCE_DETAIL_ERROR = 10903;
	/**结算状态错误*/
	public static final int FINANCE_SETTLE_STATE_ERROR_CODE = 10904;
	/**用户角色错误*/
	public static final int FINANCE_SAAS_ROLE_ERROR_CODE = 10905;
	/**结算方式错误*/
	public static final int FINANCE_SETTLE_WAY_ERROR_CODE = 10906;
}

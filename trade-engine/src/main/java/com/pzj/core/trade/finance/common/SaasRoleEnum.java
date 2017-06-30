/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.finance.common;

import com.pzj.core.trade.finance.exception.FinanceErrorCode;
import com.pzj.core.trade.finance.exception.SaasRoleErrorException;

/**
 * 结算状态枚举
 *
 * @author DongChunfu
 * @version $Id: SaasRoleEnum.java, v 0.1 2017年5月18日 上午11:07:56 DongChunfu Exp $
 */
public enum SaasRoleEnum {
	SUPPLIER(1, "供应商"), RESELLER(2, "分销商");

	private int role;
	private String desc;

	private SaasRoleEnum(final int role, final String desc) {
		this.role = role;
		this.desc = desc;
	}

	public int getRole() {
		return role;
	}

	public String getDesc() {
		return desc;
	}

	public static SaasRoleEnum getSaasRoleEnumByRole(final int role) {
		for (final SaasRoleEnum saasRole : SaasRoleEnum.values()) {
			if (saasRole.getRole() == role) {
				return saasRole;
			}
		}
		throw new SaasRoleErrorException(FinanceErrorCode.FINANCE_SAAS_ROLE_ERROR_CODE, "财务中心,SAAS用户角色错误");
	}
}

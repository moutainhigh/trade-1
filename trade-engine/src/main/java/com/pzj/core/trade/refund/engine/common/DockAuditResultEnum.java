/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.DockAuditResultException;

/**
 * 对接系统审核结果枚举
 * 
 * @author DongChunfu
 * @date 2016年12月26日
 */
public enum DockAuditResultEnum {

	AUDITTING(8, "审核中"), PASS(9, "审核通过") {
		@Override
		public Boolean isPass(final Integer result) {
			return Boolean.FALSE;
		}
	},
	REFUSED(7, "对接拒绝");

	private Integer result;
	private String note;

	private DockAuditResultEnum(final Integer result, final String note) {
		this.result = result;
		this.note = note;
	}

	public Integer getResult() {
		return result;
	}

	public String getNote() {
		return note;
	}

	public Boolean isPass(final Integer result) {
		return Boolean.FALSE;
	}

	public static DockAuditResultEnum getDockAuditResultEnum(final Integer result) {
		final DockAuditResultEnum[] values = DockAuditResultEnum.values();
		for (final DockAuditResultEnum dockAuditResultEnum : values) {
			if (dockAuditResultEnum.getResult() == result) {
				return dockAuditResultEnum;
			}
		}
		throw new DockAuditResultException(RefundErrorCode.DOCK_AUDIT_RESULT_ERROR_CODE, "对接审核结果异常.");
	}

}

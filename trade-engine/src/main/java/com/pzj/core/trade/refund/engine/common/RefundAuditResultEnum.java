/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.RefundAuditResultException;

/**
 * 退款审核方枚举
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
public enum RefundAuditResultEnum {
	INIT(1, "初始状态"), PASS(2, "审核通过"), REFUSE(3, "拒绝退款");

	private Integer result;
	private String note;

	private RefundAuditResultEnum(final Integer result, final String note) {
		this.result = result;
		this.note = note;
	}

	public Integer getResult() {
		return result;
	}

	public String getNote() {
		return note;
	}

	/**
	 * 获取退款申请发起方.
	 * 
	 * @param key
	 * @return
	 */
	public static RefundAuditResultEnum getRefundAuditResultEnum(final int result) {
		for (final RefundAuditResultEnum refundAuditResultEnum : RefundAuditResultEnum.values()) {
			if (refundAuditResultEnum.getResult() == result) {
				return refundAuditResultEnum;
			}
		}
		throw new RefundAuditResultException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核结果错误.");
	}
}

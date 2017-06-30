/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.common;

/**
 * 退款审核方枚举
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
public enum RefundLogEventEnum {
	/**退款审核通过*/
	AUDIT_PASS("refund_audit_pass"),
	/**退款审核拒绝*/
	AUDIT_REFUSE("refund_audit_refuse"),
	/**强制退款申请*/
	FORCE_REFUND_APPLY("force_refund_apply"),
	/**强制退款成功*/
	FORCE_REFUND_SUCCESS("force_refund_success"),
	/**强制退款失败*/
	FORCE_REFUND_ERROR("force_refund_error");

	private final String event;

	private RefundLogEventEnum(final String event) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

}

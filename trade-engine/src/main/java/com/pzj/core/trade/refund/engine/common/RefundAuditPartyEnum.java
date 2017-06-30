/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.RefundAuditPartyException;

/**
 * 退款审核方枚举
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
public enum RefundAuditPartyEnum {
	PLATFORM(1, "平台审核"), FINANCE(2, "财务审核"), DOCK(3, "对接审核");

	private Integer party;
	private String note;

	private RefundAuditPartyEnum(final Integer party, final String note) {
		this.party = party;
		this.note = note;
	}

	public Integer getParty() {
		return party;
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
	public static RefundAuditPartyEnum getRefundAuditPartyEnum(final int party) {
		for (final RefundAuditPartyEnum refundAuditPartyEnum : RefundAuditPartyEnum.values()) {
			if (refundAuditPartyEnum.getParty() == party) {
				return refundAuditPartyEnum;
			}
		}
		throw new RefundAuditPartyException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款审核发起方错误.");
	}
}

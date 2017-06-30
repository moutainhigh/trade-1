package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.RefundInitPartyException;

/**
 * 退款发起方枚举
 * 
 * @author DongChunfu
 * @date 2016年12月15日
 */
public enum RefundInitPartyEnum {

	GENERAL(1, "用户发起"), SUPPORT(2, "平台发起"), CONFIRM(3, "确认拒绝");

	/**
	 * 退款发起方整型值
	 */
	private Integer party;

	/**
	 * 退款发起方描述
	 */
	private String note;

	private RefundInitPartyEnum(final Integer party, final String note) {
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
	 * 获取退款申请发起方
	 * 
	 * @param party
	 *            退款发起方整型值
	 * @return RefundInitPartyEnum
	 */
	public static RefundInitPartyEnum getRefundInitPartyEnum(final Integer party) {

		for (final RefundInitPartyEnum refundInitPartyEnum : RefundInitPartyEnum.values()) {
			if (refundInitPartyEnum.getParty() == party) {
				return refundInitPartyEnum;
			}
		}
		throw new RefundInitPartyException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请发起方[" + party + "]错误.");
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder(RefundInitPartyEnum.class.getSimpleName());
		tostr.append("[party=").append(getParty());
		tostr.append(",note=").append(getNote());
		tostr.append("]");
		return tostr.toString();
	}
}

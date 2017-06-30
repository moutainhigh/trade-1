package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.RefundApplyTypeException;

/**
 * 退款申请类型.
 * 
 * @author YRJ
 *
 */
public enum RefundApplyTypeEnum {
	GENERAL(0, "普通退款"), FORCE(1, "强制退款");

	/**
	 * 退款申请类型整型值
	 */
	private Integer type;

	/**
	 * 退款申请类型描述
	 */
	private String note;

	public Integer getType() {
		return type;
	}

	public String getNote() {
		return note;
	}

	private RefundApplyTypeEnum(final Integer type, final String note) {
		this.type = type;
		this.note = note;
	}

	/**
	 * 获取退款申请类型
	 * 
	 * @param type
	 *            退款申请类型整形值
	 * @return RefundApplyTypeEnum
	 */
	public static RefundApplyTypeEnum getRefundApplyType(final Integer type) {
		for (final RefundApplyTypeEnum applyType : RefundApplyTypeEnum.values()) {
			if (applyType.type == type) {
				return applyType;
			}
		}
		throw new RefundApplyTypeException(RefundErrorCode.REQ_PARAM_ERROR_CODE, "退款申请类型[" + type + "]错误.");
	}
}

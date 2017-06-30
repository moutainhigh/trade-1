package com.pzj.core.trade.confirm.common;

import com.pzj.core.trade.confirm.exception.ConfirmReqParamException;

/**
 * 核销方式.
 * @author YRJ
 *
 */
public enum ConfirmTypeEnum {

	/**手动的*/
	MANUAL(1),
	/**自动的*/
	AUTOMATIC(2);

	private int type;

	public int getType() {
		return type;
	}

	private ConfirmTypeEnum(final int confirmType) {
		this.type = confirmType;
	}

	public static final ConfirmTypeEnum getConfirmTypeEnum(final int type) {
		for (final ConfirmTypeEnum confirmTypeEnum : ConfirmTypeEnum.values()) {
			if (confirmTypeEnum.getType() == type) {
				return confirmTypeEnum;
			}
		}
		throw new ConfirmReqParamException(ConfirmErrorCode.REQ_PARAM_ERROR_CODE, "核销类型错误type=[" + type + "]");
	}
}

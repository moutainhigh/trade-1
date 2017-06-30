package com.pzj.core.trade.confirm.common;

import com.pzj.core.trade.confirm.exception.ConfirmCodeException;

public enum MfCodeStateEnum {

	/**
	 * 待验证.
	 */
	CONFIRMABLE(0, "待验证"),
	/**
	 * 已验证.
	 */
	CONFIRMED(1, "已验证"),
	/**
	 * 已过期.
	 */
	EXPIRED(2, "已过期");

	private int state;

	private String msg;

	public int getState() {
		return state;
	}

	public String getMsg() {
		return msg;
	}

	private MfCodeStateEnum(final int state, final String msg) {
		this.state = state;
		this.msg = msg;
	}

	public final static MfCodeStateEnum getMfCodeStatus(final int state) {
		for (final MfCodeStateEnum codeState : MfCodeStateEnum.values()) {
			if (codeState.getState() == state) {
				return codeState;
			}
		}
		throw new ConfirmCodeException(ConfirmErrorCode.CHECK_TICKET_POINT_ERROR_CODE, "魔方码状态错误.");
	}
}

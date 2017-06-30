package com.pzj.core.trade.clean.engine.converter;

/**
 * 清算状态.
 * @author YRJ
 *
 */
public enum CleaningStateEnum {

	/**
	 * 待清算.
	 */
	CLEANABLE(0),
	/**
	 * 已清算.
	 */
	CLEANED(1),
	/**
	 * 清算失败.
	 */
	FAIL(2),
	/**
	 * 已废弃.
	 */
	ABANDONED(3),

	/**
	 * 临时冻结,退款申请后修改（商品状态是已消费）
	 */
	FROZEN(4);
	private int state;

	public int getState() {
		return state;
	}

	private CleaningStateEnum(int state) {
		this.state = state;
	}
}

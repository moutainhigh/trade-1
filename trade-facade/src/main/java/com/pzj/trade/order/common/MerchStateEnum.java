package com.pzj.trade.order.common;

/**
 * 商品状态.
 * @author YRJ
 *
 */
public enum MerchStateEnum {

	/**
	 * 不可用.
	 */
	UNAVAILABLE(-1, "不可用", false, false),

	/**
	 * 待确认.
	 */
	WAIT_CONFIRM(4, "待确认", true, false),

	/**
	 * 待消费.
	 */
	CONSUMABLE(0, "待消费", true, false),

	/**
	 * 已消费.
	 */
	CONSUMED(1, "已消费", false, true),

	/**
	 * 待退款.
	 */
	REFUNDING(2, "待退款", false, true),
	/**
	 * 已退款.
	 */
	REFUNDED(3, "已退款", false, true),

	/**
	 * 已完成.
	 */
	FINISHED(5, "已完成", false, true);

	/**
	 * 商品状态值.
	 */
	private int state;

	/**
	 * 状态描述.
	 */
	private String msg;

	/**
	 * 可核销.
	 */
	private boolean confirmable;

	/**
	 * 是否可以进行清算
	 */
	private boolean canCleaning;

	public int getState() {
		return state;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isConfirmable() {
		return confirmable;
	}

	public boolean isCanCleaning() {
		return canCleaning;
	}

	private MerchStateEnum(int state, String msg, boolean confirmable, boolean canCleaning) {
		this.state = state;
		this.msg = msg;
		this.confirmable = confirmable;
		this.canCleaning = canCleaning;
	}

	/**
	 * 获取商品状态值.
	 * @param state
	 * @return
	 */
	public static MerchStateEnum getMerchState(int state) {
		for (MerchStateEnum ms : MerchStateEnum.values()) {
			if (ms.getState() == state) {
				return ms;
			}
		}
		throw new IllegalStateException("商品状态值错误.");
	}
}

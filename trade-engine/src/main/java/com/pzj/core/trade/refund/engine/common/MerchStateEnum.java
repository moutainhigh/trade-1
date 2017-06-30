/**
 *
 */
package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.MerchStateException;

/**
 * 商品状态枚举
 *
 * @author DongChunfu
 * @date 2016年12月8日
 */
public enum MerchStateEnum {
	UNUSED(-1, "不可用") {
		@Override
		public boolean refundable(final int force) {
			return false;
		}
	},
	CONSUMABLE(0, "待消费") {
		@Override
		public Boolean canTrigger(final int party, final int force) {
			if (RefundInitPartyEnum.CONFIRM.getParty() == party) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

		@Override
		public Boolean doRule(final int force) {

			if (RefundApplyTypeEnum.FORCE.getType() == force) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
	},
	CONSUMED(1, "已消费") {
		@Override
		public Boolean canTrigger(final int party, final int force) {
			if (RefundInitPartyEnum.SUPPORT.getParty() == party) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}

		@Override
		public Boolean doRule(final int force) {

			if (RefundApplyTypeEnum.FORCE.getType() == force) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
	},
	REFUNDED(3, "已退款") {
		@Override
		public boolean refundable(final int force) {
			return false;
		}
	},
	WAIT_CONFIRM(4, "待确认") {
		@Override
		public Boolean canTrigger(final int party, final int force) {
			if (RefundApplyTypeEnum.FORCE.getType() == force) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

		@Override
		public Boolean doRule(final int force) {
			return Boolean.FALSE;
		}
	},
	FINISHED(5, "已完成") {

		@Override
		public Boolean canTrigger(final int party, final int force) {
			if (RefundInitPartyEnum.SUPPORT.getParty() != party) {
				return Boolean.FALSE;
			}
			if (RefundApplyTypeEnum.FORCE.getType() != force) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

		@Override
		public boolean refundable(final int force) {
			return force == RefundApplyTypeEnum.FORCE.getType() ? true : false;// 已完成状态下, 只允许强制退款, 不允许普通退款.
		}

		@Override
		public Boolean doRule(final int force) {
			return Boolean.FALSE;
		}
	};

	private int state;
	private String msg;

	private MerchStateEnum(final int state, final String msg) {
		this.state = state;
		this.msg = msg;
	}

	public static MerchStateEnum getMerchState(final int merchState) {
		for (final MerchStateEnum state : MerchStateEnum.values()) {
			if (state.getState() == merchState) {
				return state;
			}
		}
		throw new MerchStateException(10501, "商品状态在系统中错误, 请您反馈, 谢谢!");
	}

	public int getState() {
		return state;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 当前商品状态下发起方是否可以发起退款操作
	 *
	 * @param party
	 *            退款申请发起方
	 * @param force
	 *            退款申请类型
	 * @return <code>true</code> 可以触发 <code>false</code> 不可触发
	 */
	public Boolean canTrigger(final int party, final int force) {
		return Boolean.FALSE;
	}

	/**
	 * 判断商品是否可退款.
	 *
	 * @param force
	 *            退款类型
	 * @return <code>true</code> 可退 <code>false</code> 不可退
	 */
	public boolean refundable(final int force) {
		return true;
	}

	/**
	 * 判断当前商品状态是否需要执行退款规则
	 *
	 * @param party
	 *            发起方
	 * @param force
	 *            退款类型
	 * @return
	 */
	public Boolean doRule(final int force) {
		return Boolean.TRUE;
	}

	/**
	 * 判断当前商品状态下退款是否需要平台审核
	 *
	 * @param party
	 * @param force
	 * @return
	 */
	public Boolean platformAudit(final int merchState) {
		if (this == MerchStateEnum.WAIT_CONFIRM) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 判断当前商品状态下退款是否需要财务审核
	 *
	 * @param party
	 * @param force
	 * @return
	 */
	public Boolean financeAudit(final int force) {
		if (RefundApplyTypeEnum.FORCE.getType() == force) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}

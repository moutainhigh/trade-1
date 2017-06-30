/**
 * 
 */
package com.pzj.core.trade.refund.engine.common;

import com.pzj.core.trade.refund.engine.exception.RefundFlowStateException;
import com.pzj.core.trade.refund.engine.exception.RefundStateAuditPartyException;

/**
 * 退款流水审核状态
 *
 * @author DongChunfu
 * @date 2016年12月8日
 */
public enum RefundFlowAuditStateEnum {

	PLATFORM_AUDIT(1, "平台审核", 1) {

		@Override
		public void isPartyCanAudit(final int party) {
			if (RefundAuditPartyEnum.PLATFORM.getParty() != party) {
				throw new RefundStateAuditPartyException(RefundErrorCode.REFUND_STATE_AUDIT_PARTY_ERROR_CODE, "当前退款状态,发起方["
						+ RefundAuditPartyEnum.getRefundAuditPartyEnum(party).getNote() + "]不可进行审核.");
			}
		}
	},
	FINANCE_AUDIT(2, "财务审核", 1) {

		@Override
		public void isPartyCanAudit(final int party) {
			if (RefundAuditPartyEnum.FINANCE.getParty() != party) {
				throw new RefundStateAuditPartyException(RefundErrorCode.REFUND_STATE_AUDIT_PARTY_ERROR_CODE, "当前退款状态,发起方["
						+ RefundAuditPartyEnum.getRefundAuditPartyEnum(party).getNote() + "]不可进行审核.");

			}
		}
	},
	DOCK_AUDITING(8, "对接审核中", 1) {
		@Override
		public void isPartyCanAudit(final int party) {
			if (RefundAuditPartyEnum.DOCK.getParty() != party) {
				throw new RefundStateAuditPartyException(RefundErrorCode.REFUND_STATE_AUDIT_PARTY_ERROR_CODE, "当前退款状态,发起方["
						+ RefundAuditPartyEnum.getRefundAuditPartyEnum(party).getNote() + "]不可进行审核.");

			}

		}
	},
	FINISHED(9, "完结状态", 2), //
	REFUSED(7, "拒绝退款", 3);

	/**
	 * 审核状态值.
	 */
	private int state;

	/**
	 * 描述信息.
	 */
	private String note;

	/**
	 * 退款状态.
	 */
	private int refundState;

	private RefundFlowAuditStateEnum(final int state, final String note, final int refundState) {
		this.state = state;
		this.note = note;
		this.refundState = refundState;
	}

	public int getState() {
		return state;
	}

	public String getNote() {
		return note;
	}

	public int getRefundState() {
		return refundState;
	}

	/**
	 * 当前状态，这个审核方是否可审核
	 * 
	 * @param party
	 */
	public void isPartyCanAudit(final int party) {
		throw new RefundStateAuditPartyException(RefundErrorCode.REFUND_STATE_AUDIT_PARTY_ERROR_CODE, "当前退款状态,发起方["
				+ RefundAuditPartyEnum.getRefundAuditPartyEnum(party).getNote() + "]不可进行审核.");
	}

	public static RefundFlowAuditStateEnum getRefundFlowStateEnum(final int state) {
		final RefundFlowAuditStateEnum[] values = RefundFlowAuditStateEnum.values();
		for (final RefundFlowAuditStateEnum refundFlowStateEnum : values) {
			if (refundFlowStateEnum.getState() == state) {
				return refundFlowStateEnum;
			}
		}
		throw new RefundFlowStateException(RefundErrorCode.REFUND_FLOW_STATE_ERROR_CODE, "退款流水状态异常.");
	}

}

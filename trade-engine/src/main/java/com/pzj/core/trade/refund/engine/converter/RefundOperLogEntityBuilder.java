package com.pzj.core.trade.refund.engine.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.handler.RefundApplyAuditStateHandler;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.entity.RefundOperLog;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.model.RefundAuditReqModel;

@Component
public class RefundOperLogEntityBuilder {

	@Autowired
	private RefundApplyAuditStateHandler refundApplyAuditStateHandler;

	/**
	 * 退款申请操作日志内容
	 * @param reqModel
	 * @param refundId
	 * @param refundApply
	 * @param saleOrder
	 * @return
	 */
	public RefundOperLog generateRefundApplyOperLogEntity(final RefundApplyReqModel reqModel, final String refundId,
			final RefundApplyEntity refundApply, final OrderEntity saleOrder) {
		final RefundOperLog refundOperLog = new RefundOperLog();
		refundOperLog.setRefundId(refundId);
		refundOperLog.setAction(reqModel.getInitParty());
		refundOperLog.setOperatorId(reqModel.getInitiatorId());
		refundOperLog.setPrev(refundApply.getRefundAuditState());
		refundOperLog.setLater(refundApplyAuditStateHandler.nextAuditState(refundApply.getRefundAuditState(), saleOrder));
		return refundOperLog;
	}

	/**
	 * 退款审核操作日志内容
	 * @param reqModel
	 * @param refundId
	 * @param refundApply
	 * @param nextAuditState
	 * @param saleOrder
	 * @return
	 */
	public RefundOperLog generateRefundAuditOperLogEntity(final RefundAuditReqModel reqModel, final String refundId,
			final RefundApplyEntity refundApply, int nextAuditState, final OrderEntity saleOrder) {
		final RefundOperLog refundOperLog = new RefundOperLog();
		refundOperLog.setRefundId(refundId);
		refundOperLog.setAction(reqModel.getAuditorParty());
		refundOperLog.setOperatorId(reqModel.getAuditorId());
		refundOperLog.setPrev(refundApply.getRefundAuditState());
		refundOperLog.setLater(nextAuditState);
		return refundOperLog;
	}
}

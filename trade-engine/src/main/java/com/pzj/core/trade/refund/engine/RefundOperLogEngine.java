/**
 *
 */
package com.pzj.core.trade.refund.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.entity.RefundOperLog;
import com.pzj.core.trade.refund.read.RefundOperLogReadMapper;
import com.pzj.core.trade.refund.write.RefundOperLogWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.model.RefundAuditReqModel;

/**
 * 退款日志操作引擎
 *
 * @author DongChunfu
 * @date 2016年12月9日
 */
@Component(value = "refundOperLogEngine")
public class RefundOperLogEngine {

	@Autowired
	private RefundOperLogWriteMapper refundOperLogWriteMapper;

	@Autowired
	private RefundOperLogReadMapper refundOperLogReadMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public void addRefundOperLog(final RefundAuditReqModel reqModel, final int currentState, final int nextState) {
		final RefundOperLog refundOperLog = buildrefundOperLog(reqModel, currentState, nextState);
		refundOperLogWriteMapper.addRefundOperLog(refundOperLog);
	}

	public void addRefundOperLog(final RefundApplyReqModel reqModel, final int currentState, final int nextState,
			final String refundId) {
		final RefundOperLog refundOperLog = buildrefundOperLog(reqModel, currentState, nextState, refundId);
		refundOperLogWriteMapper.addRefundOperLog(refundOperLog);
	}

	private RefundOperLog buildrefundOperLog(final RefundAuditReqModel reqModel, final int currentState, final int nextState) {
		final RefundOperLog refundOperLog = new RefundOperLog();
		refundOperLog.setRefundId(reqModel.getRefundId());
		refundOperLog.setAction(reqModel.getAuditorParty());
		refundOperLog.setOperatorId(reqModel.getAuditorId());
		refundOperLog.setPrev(currentState);
		refundOperLog.setLater(nextState);
		return refundOperLog;
	}

	private RefundOperLog buildrefundOperLog(final RefundApplyReqModel reqModel, final int currentState, final int nextState,
			final String refundId) {
		final RefundOperLog refundOperLog = new RefundOperLog();
		refundOperLog.setRefundId(refundId);
		refundOperLog.setAction(reqModel.getInitParty());
		refundOperLog.setOperatorId(reqModel.getInitiatorId());
		refundOperLog.setPrev(currentState);
		refundOperLog.setLater(nextState);
		return refundOperLog;
	}

	public int queryOpLog(final Integer prev, final Integer later, final String refundId) {
		return refundOperLogReadMapper.queryByStatesNum(prev, later, refundId);
	}

}

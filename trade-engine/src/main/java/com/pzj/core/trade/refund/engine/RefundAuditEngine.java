package com.pzj.core.trade.refund.engine;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditPartyEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditResultEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundLogEventEnum;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.exception.RefundRollbackException;
import com.pzj.core.trade.refund.engine.handler.DockRefundConfirmHandler;
import com.pzj.core.trade.refund.engine.handler.RefundOperLogMQHandler;
import com.pzj.core.trade.refund.engine.handler.RefundRefuseHandler;
import com.pzj.core.trade.refund.engine.model.RefundOperMQLogModel;
import com.pzj.core.trade.refund.engine.validator.RefundAuditLegitimacyValidator;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.mq.RefundMQMSgConverter;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.refund.model.RefundAuditReqModel;

/**
 * 退款审核处理引擎
 *
 * @author DongChunfu
 * @date 2016年12月7日
 */
@Component(value = "refundAuditEngine")
public class RefundAuditEngine {

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Resource(name = "dockRefundConfirmHandler")
	private DockRefundConfirmHandler dockRefundConfirmHandler;

	@Resource(name = "refundAuditLegitimacyValidator")
	private RefundAuditLegitimacyValidator refundAuditLegitimacyValidator;

	@Resource(name = "refundOperLogEngine")
	private RefundOperLogEngine refundOperLogEngine;

	@Resource(name = "refundApplyInfoEngine")
	private RefundApplyInfoEngine refundApplyInfoEngine;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "refundRefuseHandler")
	private RefundRefuseHandler refundRefuseHandler;

	@Resource(name = "refundMQMSgConverter")
	private RefundMQMSgConverter refundMQMSgConverter;

	@Autowired
	private RefundOperLogMQHandler refundOperLogMQHandler;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public int doHandler(final RefundAuditReqModel reqModel) {

		final String refundId = reqModel.getRefundId();

		final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);

		final List<RefundFlowEntity> refundFlows = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundId);
		refundAuditLegitimacyValidator.convert(refundApply, refundFlows, reqModel);

		final Integer auditResult = reqModel.getAuditResult();
		final Integer refundType = refundApply.getIsForce();

		final String orderId = RefundFlowEntity.filterFlows(refundFlows, RefundUserTypeEnum.resellerRefund.getKey()).get(0)
				.getOrder_id();

		final OrderEntity sellOrder = orderWriteMapper.getOrderInfoForRefundAuditLock(orderId);
		if (Check.NuNObject(sellOrder) || sellOrder.getRefund_num() == 0) {
			throw new RefundRollbackException(RefundErrorCode.REFUND_ROLLBACK_ERROR_CODE, "订单不存在,或未进行退款操作");
		}
		RefundOperMQLogModel refundOperModel = new RefundOperMQLogModel();
		refundOperModel.setRefundId(refundId);
		refundOperModel.setRootOrderId(orderId);
		refundOperModel.setOperationId(reqModel.getAuditorId());
		refundOperModel.setOperMsg(reqModel.getRefusedMsg());
		int refundAuditState = RefundFlowAuditStateEnum.FINISHED.getState();

		if (RefundAuditResultEnum.REFUSE.getResult() == auditResult) {
			// 退款拒绝
			refundRefuseHandler.refuse(refundId, refundFlows, sellOrder, refundType);

			// 写入操作日志
			refundOperLogEngine.addRefundOperLog(reqModel, refundApply.getRefundAuditState(),
					RefundFlowAuditStateEnum.REFUSED.getState());
			// 写人退款拒绝原因
			refundApplyInfoEngine.addRefundRefuseInfo(reqModel);
			refundAuditState = RefundFlowAuditStateEnum.REFUSED.getState();
			refundOperModel.setEvent(RefundLogEventEnum.AUDIT_REFUSE);

		} else {

			final int nextAuditState = calculateRefundFlowState(refundApply, reqModel, sellOrder);

			refundApplyWriteMapper.updateRefundApplyRelationStatue(refundId, null, nextAuditState);

			refundOperLogEngine.addRefundOperLog(reqModel, refundApply.getRefundAuditState(), nextAuditState);
			refundOperModel.setEvent(RefundLogEventEnum.AUDIT_PASS);

			if (nextAuditState == RefundFlowAuditStateEnum.FINANCE_AUDIT.getState()) {
				refundAuditState = RefundFlowAuditStateEnum.FINANCE_AUDIT.getState();
			}
			// 调用对接审核
			if (nextAuditState == RefundFlowAuditStateEnum.DOCK_AUDITING.getState()) {
				refundAuditState = RefundFlowAuditStateEnum.DOCK_AUDITING.getState();
			}
		}
		refundOperLogMQHandler.sendOperLog(refundOperModel);
		return refundAuditState;
	}

	/**
	 * 退款状态计算方法：
	 *
	 * @param refundMerche
	 * @return
	 */
	private int calculateRefundFlowState(final RefundApplyEntity rae, final RefundAuditReqModel reqModel,
			final OrderEntity sellOrder) {

		// 若是强制退款
		final Integer auditorParty = reqModel.getAuditorParty();
		if (RefundAuditPartyEnum.PLATFORM.getParty() == auditorParty) {

			if (rae.getIsForce() == RefundApplyTypeEnum.FORCE.getType()) {
				return RefundFlowAuditStateEnum.FINANCE_AUDIT.getState();
			} else {
				if (sellOrder.getIs_dock() == 1) {
					return RefundFlowAuditStateEnum.DOCK_AUDITING.getState();
				}
			}
		}

		if (RefundAuditPartyEnum.FINANCE.getParty() == auditorParty) {
			if (sellOrder.getIs_dock() == 1) {
				return RefundFlowAuditStateEnum.DOCK_AUDITING.getState();
			}
		}

		return RefundFlowAuditStateEnum.FINISHED.getState();
	}

}

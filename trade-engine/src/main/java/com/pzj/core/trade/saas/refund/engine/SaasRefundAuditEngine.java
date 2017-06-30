package com.pzj.core.trade.saas.refund.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundLogEventEnum;
import com.pzj.core.trade.refund.engine.converter.RefundOperInfoEntityBuilder;
import com.pzj.core.trade.refund.engine.filter.RefundAuditParamFilter;
import com.pzj.core.trade.refund.engine.handler.RefundApplyAuditStateHandler;
import com.pzj.core.trade.refund.engine.handler.RefundOperLogMQHandler;
import com.pzj.core.trade.refund.engine.model.RefundAuditResultModel;
import com.pzj.core.trade.refund.engine.model.RefundOperMQLogModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.entity.RefundApplyInfoEntity;
import com.pzj.core.trade.refund.write.RefundApplyInfoWriteMapper;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.core.trade.saas.refund.engine.handler.SaasRefundFinishHandler;
import com.pzj.core.trade.saas.refund.engine.handler.SaasRefundRefusedHandler;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.refund.model.RefundAuditReqModel;

@Component
public class SaasRefundAuditEngine {

	@Autowired
	private SassRefundApplyEngine sassRefundApplyEngine;

	@Autowired
	private RefundApplyAuditStateHandler refundApplyAuditStateHandler;

	@Autowired
	private RefundOperInfoEntityBuilder refundOperInfoEntityBuilder;

	@Autowired
	private RefundAuditParamFilter refundAuditParamFilter;

	@Autowired
	private SaasRefundFinishHandler saasRefundFinishHandler;

	@Autowired
	private SaasRefundRefusedHandler saasRefundRefusedHandler;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private RefundApplyInfoWriteMapper refundRefuseInfoWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private RefundOperLogMQHandler refundOperLogMQHandler;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public RefundAuditResultModel doAudit(final RefundAuditReqModel reqModel, String saleOrderId) {
		RefundAuditResultModel result = new RefundAuditResultModel();
		final String refundId = reqModel.getRefundId();
		result.setRefundId(refundId);
		final OrderEntity saleOrder = orderWriteMapper.getOrderEntityForUpdate(saleOrderId);
		final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
		refundAuditParamFilter.doFilter(saleOrder, refundApply);
		int nextAuditState = refundApplyAuditStateHandler.nextAuditState(refundApply.getRefundAuditState(), saleOrder,
				reqModel.getAuditResult());

		RefundApplyInfoEntity refundAuditInfo = refundOperInfoEntityBuilder.generateRefundAuditInfoEntity(reqModel, refundId);
		saveRefundAuditInfo(refundApply, refundAuditInfo, saleOrder, nextAuditState);
		result.setAuditState(nextAuditState);
		result.setSaleOrderId(saleOrder.getOrder_id());

		RefundOperMQLogModel refundOperModel = new RefundOperMQLogModel();
		refundOperModel.setRefundId(refundId);
		refundOperModel.setRootOrderId(saleOrderId);
		refundOperModel.setOperationId(reqModel.getAuditorId());
		refundOperModel.setOperMsg(reqModel.getRefusedMsg());
		if (nextAuditState == RefundFlowAuditStateEnum.REFUSED.getState()) {
			refundOperModel.setEvent(RefundLogEventEnum.AUDIT_REFUSE);
		} else {
			refundOperModel.setEvent(RefundLogEventEnum.AUDIT_PASS);
		}
		refundOperLogMQHandler.sendOperLog(refundOperModel);
		return result;
	}

	private void saveRefundAuditInfo(final RefundApplyEntity refundApply, final RefundApplyInfoEntity refundAuditInfo,
			OrderEntity saleOrder, int nextAuditState) {
		refundApply.setRefundAuditState(nextAuditState);
		//退款的状态为对接审核中,调用对接审核服务
		if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.DOCK_AUDITING.getState()) {
			sassRefundApplyEngine.callDockRefund(refundApply.getRefundId(), refundApply);
			sassRefundApplyEngine.initRefundState(refundApply);
		}
		//如果退款审核完成
		if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.FINISHED.getState()) {
			refundApply.setRefundState(RefundFlowStateEnum.SUCCESS.getState());
			saasRefundFinishHandler.doFinishHandler(refundApply, saleOrder.getOrder_id());
		} else
		//如果退款审核不通过
		if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.REFUSED.getState()) {
			refundApply.setRefundState(RefundFlowStateEnum.FAIL.getState());
			saasRefundRefusedHandler.doRefusedHandler(refundApply, saleOrder);
		}
		refundRefuseInfoWriteMapper.addRefundRefuseInfo(refundAuditInfo);
		refundApplyWriteMapper.updateRefundApplyRelationStatue(refundApply.getRefundId(), refundApply.getRefundState(),
				refundApply.getRefundAuditState());
	}
}

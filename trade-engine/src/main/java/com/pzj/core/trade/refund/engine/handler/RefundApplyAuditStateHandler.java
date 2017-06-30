package com.pzj.core.trade.refund.engine.handler;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.IsDockMerchEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditResultEnum;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.trade.order.entity.OrderEntity;

@Component
public class RefundApplyAuditStateHandler {
	public int nextAuditState(int currentAuditState, final OrderEntity saleOrder) {
		int nextState = RefundFlowAuditStateEnum.FINISHED.getState();
		//如果当前的审核状态是平台审核
		if (currentAuditState == RefundFlowAuditStateEnum.PLATFORM_AUDIT.getState()
				&& saleOrder.getIs_dock() == IsDockMerchEnum.IS_DOCK.getDock()) {
			nextState = RefundFlowAuditStateEnum.DOCK_AUDITING.getState();
		}
		return nextState;
	}

	public int nextAuditState(int currentAuditState, final OrderEntity saleOrder, int auditResult) {
		int nextState = nextAuditState(currentAuditState, saleOrder);
		if (auditResult == RefundAuditResultEnum.REFUSE.getResult()) {
			nextState = RefundFlowAuditStateEnum.REFUSED.getState();
		}
		return nextState;
	}
}

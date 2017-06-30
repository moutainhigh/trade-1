package com.pzj.core.trade.saas.refund.engine.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.common.RefundSceneTypeEnum;
import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.core.trade.context.model.RefundModel;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;

@Component
public class RefundApplyFinishEvent {
	@Autowired
	private TradePublisherFactory tradePublisherFactory;

	public void dofinish(final String refundId, final String saleOrderId, int auditState) {
		int refundSceneType = -1;
		if (auditState == RefundFlowAuditStateEnum.FINISHED.getState()) {
			refundSceneType = RefundSceneTypeEnum.RefundSucess.getKey();
		} else if (auditState == RefundFlowAuditStateEnum.REFUSED.getState()) {
			refundSceneType = RefundSceneTypeEnum.RefundFailure.getKey();
		} else if (auditState == RefundFlowAuditStateEnum.PLATFORM_AUDIT.getState()) {
			refundSceneType = RefundSceneTypeEnum.RefundApply.getKey();
		} else {
			return;
		}
		final RefundModel refundModel = new RefundModel();
		refundModel.setSaleOrderId(saleOrderId);
		refundModel.setRefundId(refundId);
		refundModel.setRefundSceneType(refundSceneType);
		tradePublisherFactory.publish(refundModel);
	}

}

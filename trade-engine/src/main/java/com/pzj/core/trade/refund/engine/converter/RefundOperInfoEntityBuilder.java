package com.pzj.core.trade.refund.engine.converter;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.entity.RefundApplyInfoEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.model.RefundAuditReqModel;

@Component
public class RefundOperInfoEntityBuilder {

	public RefundApplyInfoEntity generateRefundApplyInfoEntity(final RefundApplyReqModel reqModel, String refundId) {
		final RefundApplyInfoEntity applyInfo = new RefundApplyInfoEntity();
		applyInfo.setRefundId(refundId);
		applyInfo.setOperType(0);
		if (!Check.NuNObject(reqModel.getReason())) {
			applyInfo.setReason(reqModel.getReason().getReason());
		}
		return applyInfo;
	}

	public RefundApplyInfoEntity generateRefundAuditInfoEntity(final RefundAuditReqModel reqModel, String refundId) {
		final RefundApplyInfoEntity applyInfo = new RefundApplyInfoEntity();
		applyInfo.setRefundId(refundId);
		applyInfo.setOperType(1);
		applyInfo.setReason(reqModel.getRefusedMsg());
		return applyInfo;
	}
}

package com.pzj.core.trade.refund.engine.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.exception.RefundFlowNotFoundException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.refund.model.RefundAuditReqModel;

@Component(value = "refundAuditLegitimacyValidator")
public class RefundAuditLegitimacyValidator {

	public Boolean convert(final RefundApplyEntity rae, final List<RefundFlowEntity> refundFlows,
			final RefundAuditReqModel reqModel) {

		if (Check.NuNCollections(refundFlows)) {
			throw new RefundFlowNotFoundException(RefundErrorCode.REFUND_FLOWS_IS_NONE_ERROR_CODE, "退款流水不存在异常.");
		}

		// 当前退款流水状态下，审核方是否正确.
		RefundFlowAuditStateEnum.getRefundFlowStateEnum(rae.getRefundAuditState())
				.isPartyCanAudit(reqModel.getAuditorParty());

		return Boolean.TRUE;
	}

}
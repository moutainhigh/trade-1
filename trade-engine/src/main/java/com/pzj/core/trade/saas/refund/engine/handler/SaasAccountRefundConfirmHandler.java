package com.pzj.core.trade.saas.refund.engine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundOperationIntervalException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.service.AccountBussinessService;

@Component
public class SaasAccountRefundConfirmHandler {

	@Autowired
	private AccountBussinessService accountBussinessService;

	public void confirmRefundApply(RefundApplyEntity refundApply) {
		Result<Boolean> result = accountBussinessService.confirmSaasRefund(refundApply.getRefundId());
		if (Check.NuNObject(result) || !result.isOk()) {
			throw new RefundOperationIntervalException(RefundErrorCode.REFUND_FROZEN_CLEAN_FLOW_ERROR_CODE,
					"调用退款操作调用账户服务退款资金确认异常");
		}
	}
}

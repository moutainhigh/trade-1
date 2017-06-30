package com.pzj.core.trade.saas.refund.engine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundOperationIntervalException;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.service.AccountBussinessService;

@Component
public class SaasAccountRefundCancelHandler {
	@Autowired
	private AccountBussinessService accountBussinessService;

	public void cancelApplyAccountFrozen(String refundId) {
		Result<Boolean> result = accountBussinessService.cancelSaasRefund(refundId);
		if (Check.NuNObject(result) || !result.isOk()) {
			throw new RefundOperationIntervalException(RefundErrorCode.REFUND_FROZEN_CLEAN_FLOW_ERROR_CODE,
					"调用退款操作调用账户服务资金冻结异常");
		}
	}
}

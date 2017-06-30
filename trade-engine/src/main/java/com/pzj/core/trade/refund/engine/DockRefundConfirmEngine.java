package com.pzj.core.trade.refund.engine;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.handler.DockRefundConfirmHandler;
import com.pzj.trade.refund.service.RefundAuditService;

/**
 * 对接退款
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "dockRefundConfirmEngine")
public class DockRefundConfirmEngine {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DockRefundConfirmEngine.class);

	@Resource(name = "dockRefundConfirmHandler")
	private DockRefundConfirmHandler dockRefundConfirmHandler;

	@Resource(name = "refundAuditService")
	private RefundAuditService refundAuditService;

	public int refundConfirm(final String refundId) {

		final int confirmResult = dockRefundConfirmHandler.confirm(refundId);
		return confirmResult;
	}
}

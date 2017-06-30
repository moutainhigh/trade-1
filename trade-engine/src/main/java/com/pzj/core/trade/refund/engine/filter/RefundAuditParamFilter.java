package com.pzj.core.trade.refund.engine.filter;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundRollbackException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;

@Component
public class RefundAuditParamFilter {

	public void doFilter(OrderEntity saleOrder, RefundApplyEntity refundApply) {
		if (Check.NuNObject(refundApply)) {
			throw new RefundRollbackException(RefundErrorCode.REFUND_ROLLBACK_ERROR_CODE, "退款申请信息不存在");
		}

		if (Check.NuNObject(saleOrder)) {
			throw new RefundRollbackException(RefundErrorCode.REFUND_ROLLBACK_ERROR_CODE, "退款申请信息对应的用户订单不存在");
		}
		if (saleOrder.getOrder_status() == OrderStatusEnum.Refunded.getValue()
				|| saleOrder.getOrder_status() == OrderStatusEnum.Cancelled.getValue()
				|| saleOrder.getOrder_status() == OrderStatusEnum.Unpaid.getValue()) {
			throw new RefundRollbackException(RefundErrorCode.REFUND_ROLLBACK_ERROR_CODE, "订单对应的状态异常");
		}
	}
}

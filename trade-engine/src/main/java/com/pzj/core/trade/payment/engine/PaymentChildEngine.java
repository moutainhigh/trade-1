package com.pzj.core.trade.payment.engine;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.common.PayStateEnum;
import com.pzj.core.trade.payment.engine.exception.PaymentPayErrorException;
import com.pzj.core.trade.payment.engine.handler.PaymentSuccessHandler;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.vo.ChildOrderPaymentModel;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

@Component
public class PaymentChildEngine {
	@Autowired
	private PaymentOkEngine paymentOkEngine;
	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private PaymentSuccessHandler paymentSuccessHandler;

	@Autowired
	private PaymentCancelEngine paymentCancelEngine;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void doHandler(final ChildOrderPaymentModel reqModel) {
		final OrderEntity order = orderWriteMapper.getParentOrderByChildId(reqModel.getOrderId());
		if (Check.NuNObject(order)) {
			throw new PaymentPayErrorException(10145, "订单[" + reqModel.getOrderId() + "]对应的下级订单不存在");
		}
		if (order.getPay_state() != PayStateEnum.PAYFINISH.getPayState()) {
			throw new PaymentPayErrorException(10145, "订单[" + reqModel.getOrderId() + "]对应的支付状态无需补差");
		}
		final Date payDate = new Date();
		if (reqModel.isMakeUp()) {
			final boolean isEnd = paymentOkEngine.doSuccess(order, payDate, true);
			if (isEnd) {
				final OrderEntity rootOrder = orderWriteMapper.getOrderEntityNotLock(order.getTransaction_id());

				final FreezeFlowEntity flow = freezeFlowWriteMapper.getFreezeFlow(rootOrder.getOrder_id(),
						ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.Paying.getKey());
				paymentSuccessHandler.doHandler(rootOrder, flow);
			}
		} else {
			paymentCancelEngine.doEngine(order.getTransaction_id());
		}
	}
}

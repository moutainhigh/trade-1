package com.pzj.core.trade.payment.engine.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 子订单支付流水处理器.
 * @author YRJ
 *
 */
@Component(value = "childOrderFreezeFlowHandler")
public class ChildOrderFreezeFlowHandler {

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Resource(name = "childOrderAccountHandler")
	private ChildOrderAccountHandler childOrderAccountHandler;

	public FreezeFlowEntity generateFreezeFlow(final OrderEntity parent, final OrderEntity order) {
		FreezeFlowEntity flowEntity = freezeFlowWriteMapper.getFreezeFlow(order.getOrder_id(),
				ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.Paying.getKey());
		if (flowEntity != null) {
			return flowEntity;
		}
		//线下付款方式, 无付款流水.
		if (!PayWayEnum.getPayWay(order.getPay_way()).isOnline()) {
			return null;
		}
		FreezeFlowEntity flowRoot = freezeFlowWriteMapper.getFreezeFlow(order.getTransaction_id(),
				ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.Paying.getKey());
		final AmountModel amount = childOrderAccountHandler.doAccountFrozen(order, parent.getTotal_amount(),
				flowRoot.getSign_id());
		flowEntity = assembleyPayfrozen(amount.getAccountAmount(), order, flowRoot.getSign_id());
		return flowEntity;
	}

	/**
	 * 保存支付流水
	 *
	 * @param pr
	 * @param order
	 * @param paySignId
	 * @return
	 */
	private FreezeFlowEntity assembleyPayfrozen(final double accountAmount, final OrderEntity order,
			final String paySignId) {
		final FreezeFlowEntity freezeFlow = new FreezeFlowEntity();
		freezeFlow.setOrder_id(order.getOrder_id());
		freezeFlow.setBalance_amount(accountAmount);
		freezeFlow.setFreeze_state(PayFlowStateEnum.Paying.getKey());
		freezeFlow.setReceive_type(ReceiveTypeEnum.Payment.getValue());
		freezeFlow.setPayer_id(order.getPayer_id());
		freezeFlow.setSign_id(paySignId);
		return freezeFlow;
	}

}

package com.pzj.core.trade.payment.engine.handler;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.payment.engine.exception.PaymentPayErrorException;
import com.pzj.settlement.balance.response.PaymentResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 支付流水处理器.
 * @author YRJ
 *
 */
@Component(value = "freezeFlowHandler")
public class FreezeFlowHandler {

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Resource(name = "orderPayAccountHandler")
	private OrderPayAccountHandler orderPayAccountHandler;

	public FreezeFlowEntity generateFreezeFlow(final OrderEntity order, final int payType) {
		FreezeFlowEntity flowEntity = freezeFlowWriteMapper.getFreezeFlow(order.getOrder_id(), ReceiveTypeEnum.Payment.getValue(),
				PayFlowStateEnum.Paying.getKey());
		if (flowEntity != null) {
			return flowEntity;
		}
		//线下付款方式, 无付款流水.
		if (!PayWayEnum.getPayWay(order.getPay_way()).isOnline()) {
			return null;
		}

		final String signId = UUID.randomUUID().toString().replace("-", "");
		final PaymentResponse resp = orderPayAccountHandler.doAccountFrozen(order, signId);

		//特殊化处理
		if (payType == 0 && resp.getThirdPayMoney() > 0D) {
			orderPayAccountHandler.doAccountRefrozen(order, signId);
			throw new PaymentPayErrorException(10205, "当前账号余额不足, 请选择其它支付方式.");
		}

		flowEntity = assembleyPayfrozen(resp, order, signId);
		freezeFlowWriteMapper.insertFreezeFlow(flowEntity);
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
	private FreezeFlowEntity assembleyPayfrozen(final PaymentResponse pr, final OrderEntity order, final String paySignId) {
		final FreezeFlowEntity freezeFlow = new FreezeFlowEntity();
		freezeFlow.setOrder_id(order.getOrder_id());
		freezeFlow.setBalance_amount(pr.getBalancePayMoney());
		freezeFlow.setThird_amount(pr.getThirdPayMoney());
		freezeFlow.setFreeze_state(PayFlowStateEnum.Paying.getKey());
		freezeFlow.setReceive_type(ReceiveTypeEnum.Payment.getValue());
		freezeFlow.setPayer_id(order.getPayer_id());
		freezeFlow.setSign_id(paySignId);
		return freezeFlow;
	}

}

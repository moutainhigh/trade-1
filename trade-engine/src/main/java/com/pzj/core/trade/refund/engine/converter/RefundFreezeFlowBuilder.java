package com.pzj.core.trade.refund.engine.converter;

import org.springframework.stereotype.Component;

import com.pzj.settlement.balance.response.PaymentResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;

@Component
public class RefundFreezeFlowBuilder {

	public FreezeFlowEntity generateRefundFreezeFlow(String refundId, OrderEntity saleOrder, PaymentResponse paymentResponse) {
		FreezeFlowEntity freezeflow = new FreezeFlowEntity();
		freezeflow.setSign_id(refundId);
		freezeflow.setOrder_id(saleOrder.getOrder_id());
		freezeflow.setReceive_type(ReceiveTypeEnum.Refund.getValue());
		freezeflow.setPayer_id(saleOrder.getPayer_id());
		freezeflow.setBalance_amount(paymentResponse.getBalancePayMoney());
		freezeflow.setThird_amount(paymentResponse.getThirdPayMoney());
		return freezeflow;
	}
}

package com.pzj.core.trade.payment.engine.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.core.trade.context.model.PaymentModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.vo.PaymentVO;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

@Component("paymentHandel")
public class PaymentHandel {

	@Resource(name = "orderWriteMapper")
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Resource(name = "tradePublisherFactory")
	private TradePublisherFactory tradePublisherFactory;

	/**
	 * 订单付款老接口
	 * 
	 * @param reqModel
	 * @return
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public Result<PaymentResult> payOrder(final PaymentVO reqModel) {
		final String orderId = reqModel.getOrderId();
		// 获取订单的支付信息
		final FreezeFlowEntity payFlow = freezeFlowWriteMapper.getFreezeFlow(orderId, ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.Paying.getKey());

		//PaymentResponse frozenResult = null;
		if (Check.NuNObject(payFlow)) {
		}
		return null;
	}

	/**
	 * 发布订单支付成功任务
	 *
	 * @param orderId
	 *            订单ID
	 */
	protected void publishPayTask(final String orderId) {
		final PaymentModel paramModel = new PaymentModel();
		paramModel.setPaymentSceneType(2);
		paramModel.setSaleOrderId(orderId);
		tradePublisherFactory.publish(paramModel);
	}

}

package com.pzj.core.trade.payment.engine;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.payment.engine.exception.PaymentPayErrorException;
import com.pzj.core.trade.payment.engine.handler.OrderPayAccountHandler;
import com.pzj.core.trade.payment.engine.handler.PaymentSuccessHandler;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 支付回调引擎.
 * @author YRJ
 *
 */
@Component(value = "paymentCallbackEngine")
public class PaymentCallbackEngine {

	@Resource(name = "orderWriteMapper")
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Resource(name = "paymentOkEngine")
	private PaymentOkEngine paymentOkEngine;

	@Resource(name = "orderPayAccountHandler")
	private OrderPayAccountHandler orderPayAccountHandler;

	@Autowired
	private PaymentSuccessHandler paymentSuccessHandler;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void doHandler(final PayCallbackVO reqModel) {
		final OrderEntity order = orderWriteMapper.getOrderEntityForUpdate(reqModel.getOrderId());
		if (order.getOrder_status() != OrderStatusEnum.Unpaid.getValue()) {
			throw new PaymentPayErrorException(10205, "支付失败, 订单[" + order.getOrder_id() + "]状态值[" + order.getOrder_status()
					+ "]");
		}
		final FreezeFlowEntity flow = freezeFlowWriteMapper.getFreezeFlow(order.getOrder_id(),
				ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.Paying.getKey());
		if (flow.getThird_amount() < order.getTotal_amount()) {
			order.setPay_way(PayWayEnum.MIXED.getPayWay());
		}
		if (Check.NuNObject(flow)) {
			throw new PaymentPayErrorException(10206, "支付失败, 订单[" + order.getOrder_id() + "], 支付信息不存在");
		}
		//保存第三方返回值的相关内容
		flow.setThird_content(JSONConverter.toJson(reqModel));
		freezeFlowWriteMapper.updateFreezeFlowThirdContent(flow.getFreeze_id(), flow.getThird_content());
		Date paytime = new Date();
		//开始处理子订单的付款动作.
		final boolean isEnd = paymentOkEngine.doSuccess(order, paytime, false);
		orderWriteMapper.updateOrderPayState(order.getOrder_id(), null, reqModel.getPayType(), order.getPay_way(), 2, paytime);
		//处理整个订单.
		if (isEnd) {
			paymentSuccessHandler.doHandler(order, flow);
		}
	}

}

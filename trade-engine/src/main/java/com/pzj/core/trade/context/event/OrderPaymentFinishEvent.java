package com.pzj.core.trade.context.event;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.IsDockedEnum;
import com.pzj.core.trade.context.model.PaymentModel;
import com.pzj.core.trade.sms.engine.handle.PaymentSMSHandle;
import com.pzj.core.trade.sms.engine.handle.saas.SaasPaymentSMSHandle;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.mq.ConvertMQMsg;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component
public class OrderPaymentFinishEvent {
	private final static Logger logger = LoggerFactory.getLogger(OrderPaymentFinishEvent.class);

	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Resource(name = "mqUtil")
	private MQUtil mqUtil;
	@Resource(name = "paymentSMSHandle")
	private PaymentSMSHandle paymentSMSHandle;
	@Resource(name = "saasPaymentSMSHandle")
	private SaasPaymentSMSHandle saasPaymentSMSHandle;

	public void doEvent(final PaymentModel paramModel, OrderEntity saleOrder) {
		sendPaymentMsg(saleOrder);
		callDockSystm(saleOrder);
		if ("0".equals(saleOrder.getVersion())) {
			paymentSMSHandle.sendPaymentSMS(saleOrder.getOrder_id());
		} else {
			saasPaymentSMSHandle.sendPaymentSMS(saleOrder.getOrder_id());
		}
	}

	/**
	 * 调用对接系统
	 * 
	 * @param order
	 */
	private void callDockSystm(final OrderEntity saleOrder) {
		//非对接的订单不用发送对接下单的消息
		if (saleOrder.getIs_dock() == IsDockedEnum.NO.getValue()) {
			return;
		}
		OrderEntity order = null;
		if (saleOrder.getVersion().equals("0")) {
			order = orderWriteMapper.getOrderListByPorderId(saleOrder.getOrder_id());
		} else {
			order = orderWriteMapper.getInitialSupplierOrderByTransactionId(saleOrder.getTransaction_id());
		}
		try {
			mqUtil.send(MQTagsEnum.createDockingOrder.getValue(), ConvertMQMsg.convertCreateDockingOrder(order));
		} catch (final Exception e) {
			logger.error("call mqUtil.send reqParam[orderId:" + saleOrder.getOrder_id() + "],errorContent:", e);
		}
	}

	/**
	 * 发送订单支付成功的消息
	 * @param saleOrder
	 */
	private void sendPaymentMsg(final OrderEntity saleOrder) {
		final String tag = "orderPaiedEventMsg";
		final Map<String, Object> msgCache = new HashMap<String, Object>();
		msgCache.put("saleOrderId", saleOrder.getOrder_id());
		msgCache.put("transactionId", saleOrder.getTransaction_id());
		msgCache.put("totalAmount", saleOrder.getTotal_amount());
		msgCache.put("salePort", saleOrder.getSale_port());
		msgCache.put("is_direct", saleOrder.getIs_direct());
		msgCache.put("is_dock", saleOrder.getIs_dock());
		final String msgContent = JSONConverter.toJson(msgCache);
		mqUtil.send(tag, msgContent);
	}
}

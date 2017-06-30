package com.pzj.core.trade.confirm.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.engine.ConfirmEngine;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.mq.OpenApiMqEventEnum;
import com.pzj.trade.order.entity.OrderNumEntity;

/**
 * 核销完毕想OpenApi发送核销信息
 *
 * @author DongChunfu
 * @version $Id: ConfirmFinishedSendOpenApiMqHandler.java, v 0.1 2017年3月22日 上午10:04:37 Administrator Exp $
 */
@Component(value = "confirmFinishedSendOpenApiMqHandler")
public class ConfirmFinishedSendOpenApiMqHandler {

	private static final Logger logger = LoggerFactory.getLogger(ConfirmFinishedSendOpenApiMqHandler.class);

	@Resource(name = "confirmEngine")
	private ConfirmEngine confirmEngine;

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	public void sentHandler(final String transactionId) {
		// 1:判断当前凭证对应的订单的所有商品是否均核销完毕
		final OrderNumEntity orderNumInfo = confirmEngine.getOrderConfirmInfo(transactionId);

		// 2:核销完毕发送mq消息
		if (confirmRealFinished(orderNumInfo)) {
			final String context = getMqContext(orderNumInfo.getOrderId());
			logger.info("confirm finish send open api mq msg,{}.", context);
			mqUtil.send(MQTagsEnum.trade_to_openapi.getValue(), context);
		}
	}

	/**
	 * 根据订单数量判断订单是否核销完毕
	 *
	 * @param numInfo 订单的数量信息
	 * @return <code>true</code>核销完毕;<code>false</code>未核销.
	 */
	private Boolean confirmRealFinished(final OrderNumEntity numInfo) {

		if (numInfo.getCheckNum() == 0) {
			return Boolean.FALSE;
		}

		if (numInfo.getTotalNum() == (numInfo.getRefundNum() + numInfo.getCheckNum())) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * 核销完毕发送的消息内容
	 *
	 * @param orderId 订单ID
	 * @return 消息内容
	 */
	private final String getMqContext(final String orderId) {
		final Map<String, String> context = new HashMap<String, String>();
		context.put("orderId", orderId);
		context.put("event", OpenApiMqEventEnum.CONFIRM_ORDER.getEvent());
		context.put("success", Boolean.TRUE.toString());
		context.put("msg", (String) null);
		return JSONConverter.toJson(context);
	}
}
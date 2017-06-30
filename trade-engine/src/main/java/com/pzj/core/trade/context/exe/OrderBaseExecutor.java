package com.pzj.core.trade.context.exe;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.common.OrderSceneTypeEnum;
import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;
import com.pzj.core.trade.context.exe.base.TradeExecutor;
import com.pzj.core.trade.sms.engine.handle.OrderConfirmSMSHandle;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.mq.OpenApiMqEventEnum;

@Component("orderCreatedExecutor")
public class OrderBaseExecutor implements TradeExecutor<ExecuteBaseModel> {
	private final static Logger logger = LoggerFactory.getLogger(OrderPaymentedExecutor.class);
	@Autowired
	private OrderConfirmSMSHandle orderConfirmSMSHandle;

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	@Override
	public void execute(ExecuteBaseModel paramModel) {
		logger.info("call OrderBaseExecutor.execute sendParam:{}", JSONConverter.toJson(paramModel));
		try {
			//二次确认通过
			if (paramModel.getOrderSceneType() == OrderSceneTypeEnum.AckSucess.getKey()) {
				sendMessageToOpenApi(paramModel.getSaleOrderId(), true);
				ackSucess(paramModel);
			} else if (paramModel.getOrderSceneType() == OrderSceneTypeEnum.ackFailure.getKey()) {
				sendMessageToOpenApi(paramModel.getSaleOrderId(), false);
			}
		} catch (Exception e) {
			logger.info("call OrderBaseExecutor.execute is error, sendParam:" + JSONConverter.toJson(paramModel) + ",errorContent:", e);
		}
	}

	/**
	 * 订单的二次确认通过事件
	 * @param paramModel
	 */
	private void ackSucess(ExecuteBaseModel paramModel) {
		orderConfirmSMSHandle.sendOrderConfirmMsg(paramModel.getSaleOrderId());
	}

	/**
	 * 
	 * @param ackModel
	 */
	private void sendMessageToOpenApi(String order_id, boolean success) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", order_id);
		map.put("event", OpenApiMqEventEnum.CREATE_ORDER.getEvent());
		map.put("success", success);
		String msgStr = JSONConverter.toJson(map).toString();
		mqUtil.send(MQTagsEnum.trade_to_openapi.getValue(), msgStr);
	}
}

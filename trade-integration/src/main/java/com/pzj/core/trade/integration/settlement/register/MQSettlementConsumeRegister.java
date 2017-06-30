package com.pzj.core.trade.integration.settlement.register;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.pzj.core.trade.integration.settlement.register.common.MQSettlementConsumerEnum;
import com.pzj.core.trade.integration.settlement.service.SettlementMQConsumeCallbackService;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.mq.MQUtil;

/**
 * 监听返利金入账操作的消息
 * @author kangzl
 *
 */
@Component("MQSettlementConsumeRegister")
public class MQSettlementConsumeRegister extends MQUtil implements ApplicationContextAware {
	private final static Logger logger = LoggerFactory.getLogger(MQSettlementConsumeRegister.class);

	private static Map<String, DefaultMQPushConsumer> consumerCache = new HashMap<String, DefaultMQPushConsumer>();

	private static ApplicationContext ac;

	private void registMQConsumer(final String tags, final SettlementMQConsumeCallbackService service) {
		final String cacheKey = MQSettlementConsumerEnum.topic + tags;
		if (Check.NuNString(tags) || consumerCache.containsKey(cacheKey)) {
			return;
		}
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(cacheKey);
		consumer.setInstanceName(cacheKey);
		consumer.setNamesrvAddr(this.consumerAddress);
		try {
			consumer.subscribe(MQSettlementConsumerEnum.topic, tags);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
					try {
						final String msgContent = new String(msgs.get(0).getBody());
						logger.info("订阅者topic:[" + MQSettlementConsumerEnum.topic + "],msgContent:[" + msgContent + "]");
						service.doCallback(msgContent);
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					} catch (Exception e) {
						logger.error("订阅者topic:[" + MQSettlementConsumerEnum.topic + "],msgs:[" + JSONConverter.toJson(msgs) + "],errorContent:", e);
						return ConsumeConcurrentlyStatus.RECONSUME_LATER;
					}
				}
			});
			consumer.start();
			consumerCache.put(cacheKey, consumer);
		} catch (MQClientException e) {
			logger.error("注册mq消费客户端失败,topic[" + MQSettlementConsumerEnum.topic + "],callbackService:" + service.getClass().getName(), e);
		}
	}

	private void initMQConsume() {
		MQSettlementConsumerEnum[] mqenums = MQSettlementConsumerEnum.values();
		Map<String, SettlementMQConsumeCallbackService> topicRelCache = new HashMap<String, SettlementMQConsumeCallbackService>();
		for (MQSettlementConsumerEnum e : mqenums) {
			SettlementMQConsumeCallbackService service = ac.getBean(e.getRelBeanId(), SettlementMQConsumeCallbackService.class);
			topicRelCache.put(e.getTagId(), service);
		}
		Iterator<Entry<String, SettlementMQConsumeCallbackService>> iter = topicRelCache.entrySet().iterator();
		for (; iter.hasNext();) {
			Entry<String, SettlementMQConsumeCallbackService> entry = iter.next();
			registMQConsumer(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (!Check.NuNObject(ac)) {
			return;
		}
		ac = applicationContext;
		initMQConsume();
	}
}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.mq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.pzj.framework.toolkit.Check;

/**
 * 
 * 
 * @author Y50
 * @version $Id: MQUtil.java, v 0.1 2016年5月18日 下午9:06:45 Y50 Exp $
 */
@Component("mqUtil")
public class MQUtil {

	public static DefaultMQPushConsumer consumer;

	private final static Logger logger = LoggerFactory.getLogger(MQUtil.class);

	public static void main(final String[] args) {
		final MQUtil util = new MQUtil();
		util.consumerAddress = "10.0.6.25:9876;10.0.6.26:9876";
		util.producerAddress = "10.0.6.25:9876;10.0.6.26:9876";
		util.mqTrade = "trade";
		util.MQUtilInit();
		util.send("rebateInAccountNotify", "babababab");
		//		util.send("confirm", "SP0003412341234", "confirmSP0003412341234true");
	}

	private static DefaultMQProducer producer;
	@Value("${mq.consumer.address}")
	protected String consumerAddress;
	@Value("${mq.producer.address}")
	protected String producerAddress;
	@Value("${mq.trade}")
	protected String mqTrade;
	private static ExecutorService pool;

	@PostConstruct
	private void MQUtilInit() {
		if (producer == null) {
			//			initConsumer();
			initProducer();
		}
		if (Check.NuNObject(pool)) {
			pool = Executors.newFixedThreadPool(10);
		}
	}

	/** 
	 * @see com.pzj.trade.component.MQComp#send(java.lang.String, java.lang.String)
	 */
	//    public SendResult send(String topic, String tags, String key, String msgStr) {
	//        return null;
	//    }

	/** 
	 * @see com.pzj.trade.component.MQComp#send(java.lang.String, java.lang.String)
	 */
	public SendResult send(final String tags, final String key, final String msgStr) {

		final Message msg = new Message("trade", tags, key, msgStr.getBytes());
		SendResult result = null;
		try {
			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			return result;
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			logger.error("消息发送失败", e);
		}
		return result;
	}

	public void send(final String tags, final String msgStr) {
		logger.info("+++++++++++++A++++++++++");
		final String tradeStr = this.mqTrade;
		pool.execute(new Runnable() {
			@Override
			public void run() {
				logger.info("+++++++++++++B++++++++++");
				logger.info("发送消息: topic: [" + tradeStr + "], tags: [" + tags + "], message: " + msgStr + ",address:" + producer.getNamesrvAddr());
				final Message msg = new Message(tradeStr, tags, msgStr.getBytes());
				logger.info("+++++++++++++C++++++++++");
				try {
					Thread.sleep(1000);
					logger.info("+++++++++++++D++++++++++");
					final SendResult result = producer.send(msg);
					logger.info("+++++++++++++E++++++++++");
					logger.info("发送消息结果: topic: [" + tradeStr + "], tags: [" + tags + "], result: " + msgStr + ", sendResult: "
							+ (ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE)));
				} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
					logger.error("发送消息失败: topic: [" + tradeStr + "], tags: [" + tags + "], message: " + msgStr + ",address" + producer.getNamesrvAddr(), e);
				}
				logger.info("+++++++++++++F++++++++++");
			}
		});
		logger.info("+++++++++++++G++++++++++");
	}

	/** 
	 * @see com.pzj.trade.component.MQComp#receive()
	 */
	public boolean receive() {
		return false;
	}

	//	//暂时简单实现
	//	private void initConsumer() {
	//		consumer = new DefaultMQPushConsumer("QuickStartConsumer");
	//		consumer.setInstanceName("QuickStartConsumer");
	//		consumer.setNamesrvAddr(this.consumerAddress);
	//
	//		try {
	//			//订阅PushTopic下Tag为push的消息  
	//			consumer.subscribe(this.mqTrade, "rebateInAccountNotify");
	//			//程序第一次启动从消息队列头取数据  
	//			consumer.registerMessageListener(new MessageListenerConcurrently() {
	//				@Override
	//				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext Context) {
	//					//TODO 订阅者业务处理Message msg = list.get(0);
	//					System.out.println(new String(msgs.get(0).getBody()));
	//					num++;
	//					System.out.println(num);
	//					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	//				}
	//			});
	//			consumer.start();
	//		} catch (final Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 
	 */
	private void initProducer() {
		producer = new DefaultMQProducer("tradeMQProducer");
		producer.setNamesrvAddr(this.producerAddress);
		producer.setInstanceName("trademq-instance");
		try {
			producer.start();
		} catch (final MQClientException e) {
			logger.error("消息发送者启动失败", e);
		}
	}
}

package com.pzj.core.trade.mq.test;

import java.io.IOException;

import org.junit.Test;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class MQPubTest {

	String mqAddr = "10.0.6.25:9876;10.0.6.26:9876";

	@Test
	public void testPublish() {
		DefaultMQProducer producer = new DefaultMQProducer("QuickStartProducer");
		producer.setNamesrvAddr(mqAddr);
		producer.setInstanceName("rmq-instance");
		try {
			producer.start();
			System.out.println("连接MQ服务成功");
		} catch (MQClientException e) {
			e.printStackTrace();
			System.exit(1);
		}

		String topic = "trade";
		String tags = "trade_test";
		String context = "test";
		Message msg = new Message(topic, tags, context.getBytes());
		try {
			SendResult result = producer.send(msg);
			System.out.println(result);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

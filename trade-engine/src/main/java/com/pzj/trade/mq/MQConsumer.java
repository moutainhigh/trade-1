package com.pzj.trade.mq;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

public class MQConsumer {
    private static final String                consumerAddress = "124.251.43.228:9876";
    private static List<DefaultMQPushConsumer> consumerList    = new ArrayList<DefaultMQPushConsumer>();

    public static void main(String[] args) {
        for (MQTagsEnum tag : MQTagsEnum.values()) {
            String name = "Consumer_" + tag.getValue();
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(name);
            consumer.setNamesrvAddr(consumerAddress);
            try {
                //订阅PushTopic下Tag为push的消息  
                consumer.subscribe("trade", tag.getValue() + "_M");
                //程序第一次启动从消息队列头取数据  
                consumer.registerMessageListener(new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                    ConsumeConcurrentlyContext Context) {
                        //TODO 订阅者业务处理Message msg = list.get(0);
                        System.out.println(new String(msgs.get(0).getBody()));
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                });
                consumer.start();
                consumerList.add(consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

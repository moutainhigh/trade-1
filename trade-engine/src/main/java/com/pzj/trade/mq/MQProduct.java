package com.pzj.trade.mq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class MQProduct {
    private static final String      productAddress = "124.251.43.228:9876";
    private static DefaultMQProducer producer;

    public static void main(String[] args) {
        initProducer();
        int i = 0;
        for (final MQTagsEnum tag : MQTagsEnum.values()) {
            Thread t = new Thread() {
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        send(tag.getValue(), tag.getValue() + "----asdfsdfsdf" + i);
                    }
                }
            };
            t.start();
            i++;
        }
        System.out.println(i);
    }

    public static SendResult send(String tags, String msgStr) {
        Message msg = new Message("trade", tags + "_M", msgStr.getBytes());
        SendResult result = null;
        try {
            System.out.println("tag:" + tags + ",content:" + msgStr);
            result = producer.send(msg);
            //            System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
            return result;
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void initProducer() {
        String name = "Product_MQ";
        producer = new DefaultMQProducer(name);
        producer.setNamesrvAddr(productAddress);
        producer.setInstanceName(name);
        try {
            producer.start();

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}

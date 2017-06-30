package com.pzj.core.trade.mq;

public class MQListenerTest {

	//	@Test
	//	public void testSendMsg() {
	//		List<RebateInAccountMessage> list = new ArrayList<RebateInAccountMessage>();
	//		RebateInAccountMessage m1 = new RebateInAccountMessage();
	//		m1.setSkuId(854636901739081728L);
	//		m1.setTransactionId("1040917041910000");
	//		m1.setResellerId(3904099406315520L);
	//		m1.setSupplierId(3904712600977409L);
	//		m1.setInAccountTime(System.currentTimeMillis());
	//		list.add(m1);
	//
	//		RebateInAccountMessage m2 = new RebateInAccountMessage();
	//		m2.setSkuId(854663041564356608L);
	//		m2.setTransactionId("1040917041910003");
	//		m2.setResellerId(3904099406315520L);
	//		m2.setSupplierId(3904712600977409L);
	//		m2.setInAccountTime(System.currentTimeMillis());
	//		list.add(m2);
	//
	//		sendMsg(JSONConverter.toJson(list));
	//	}
	//
	//	private void sendMsg(final String msgStr) {
	//		final String mqaddress = "10.0.6.25:9876;10.0.6.26:9876";
	//		DefaultMQProducer producer = new DefaultMQProducer("QuickStartProducer");
	//		producer.setNamesrvAddr(mqaddress);
	//		producer.setInstanceName("rmq-instance");
	//		try {
	//			producer.start();
	//			final Message msg = new Message(MQSettlementConsumerEnum.topic, MQSettlementConsumerEnum.rebateInAccountNotify.getTagId(), msgStr.getBytes());
	//			SendResult result = producer.send(msg);
	//			System.out.println("发送消息内容: [" + msgStr + "],发送结果：[" + JSONConverter.toJson(result) + "]");
	//		} catch (final Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
}

package com.pzj.trade.console;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.context.exe.base.ExecuteBaseModel;
import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.core.trade.context.model.PaymentModel;

public class Console {

	public static void main(final String[] args) {

		/** 使用dubbo方式启动 */
		//		dubboStart(args);

		/** spring通配配置文件启动 */
		springStart();

		/** spring指定详细配置文件启动  */
		//   springConfStart();
	}

	/** 使用dubbo方式启动 */
	static void dubboStart(final String[] args) {
		com.alibaba.dubbo.container.Main.main(args);
	}

	/**
	 *  spring通配配置文件启动
	 */
	private static void springStart() {
		final ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/trade*.xml");
		System.out.println(context);
		try {
			System.in.read();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void testQuery(final ApplicationContext ac) {
		final TradePublisherFactory bb = ac.getBean(TradePublisherFactory.class);
		final ExecuteBaseModel paramModel = new ExecuteBaseModel();
		for (int i = 0; i < 300; i++) {
			bb.publish(paramModel);
		}
	}

	public static void test(final ApplicationContext ac) {
		//		RefundModel refund = new RefundModel();
		//		refund.setRefundSceneType(2);
		//		refund.setSaleOrderId("MF1074105425");
		//		refund.setRefundId("6b1863a319794d82b45385a63ee3ba2c");
		//		ConsumerModel consumer = new ConsumerModel();
		//		consumer.setVoucherId(1476150880852L);
		//		consumer.setConsumerSceneType(1);
		//		consumer.setSaleOrderId("MF1944112238");
		//		ExecuteBaseModel base = new ExecuteBaseModel();
		//		base.setOrderSceneType(2);
		//		base.setSaleOrderId("MF2021815389");
		//		
		final PaymentModel model = new PaymentModel();
		model.setPaymentSceneType(2);
		model.setSaleOrderId("MF403048712");
		final TradePublisherFactory f = ac.getBean(TradePublisherFactory.class);
		f.publish(model);
	}
}

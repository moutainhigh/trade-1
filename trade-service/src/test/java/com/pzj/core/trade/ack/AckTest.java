package com.pzj.core.trade.ack;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.ack.model.AckModel;
import com.pzj.trade.ack.service.OrderAckService;

public class AckTest {
	static ApplicationContext context;

	private OrderAckService orderAckService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		orderAckService = context.getBean(OrderAckService.class);
	}

	@Test
	public void testValidate() {
		AckModel ack = new AckModel();
		ack.setOrderId("MF241406732");
		ack.setAcknowledge(true);
		try {
			Result<Boolean> reslt = orderAckService.ack(ack, null);
			System.out.println(JSONConverter.toJson(reslt));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

package com.pzj.core.trade.ack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.ack.model.AckModel;
import com.pzj.trade.ack.service.OrderAckService;

public class AckServiceTest {

	static ApplicationContext context;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
	}

	private OrderAckService orderAckService;

	@Before
	public void setUp() {
		orderAckService = context.getBean(OrderAckService.class);
	}

	/*
	 * 订单二次确认
	 */
	@Test
	public void confirm_2() throws Exception {
		AckModel ackModel = new AckModel();
		ackModel.setOrderId("1093717042800006");
		ackModel.setAcknowledge(false);

		Result<Boolean> response = orderAckService.ack(ackModel, null);
		Assert.assertNotNull(response);

		System.out.println(JSONConverter.toJson(response));
	}
}

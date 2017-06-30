package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.trade.order.service.OrderService;

/**
 * 创建订单测试类.
 * @author YRJ
 *
 */
public class TradeOrderTest {

	ApplicationContext context;

	OrderService orderService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		orderService = context.getBean(OrderService.class);

		Assert.assertNotNull(orderService);
	}

	public void testCreateOrder() {

	}
}

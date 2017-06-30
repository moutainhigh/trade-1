package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.book.ServiceTestData;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.service.OrderService;

/**
 * 多级分销订单测试类.
 * @author chj
 *
 */
public class OrderServiceMultiTest2 {

	ApplicationContext context;

	OrderService orderService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		orderService = context.getBean(OrderService.class);

		Assert.assertNotNull(orderService);
	}

	@Test
	public void testRealIDcard() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/851694567296937984.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test1AfterPay() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/1afterPay.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test7cash() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/7cashOffLine.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test8wshopSingle() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/8wshopSingle.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test9wshopMulti() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/9wshopMulti.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test10multiSku() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/10multiSku.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test11SingleSku() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/11multiSku.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test122016() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/122016.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testNew122016() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/new122016.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}
}

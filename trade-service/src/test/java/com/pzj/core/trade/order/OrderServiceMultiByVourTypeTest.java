package com.pzj.core.trade.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 一码一票订单测试类.
 * @author chj
 *
 */
public class OrderServiceMultiByVourTypeTest {

	ApplicationContext context;

	OrderService orderService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		orderService = context.getBean(OrderService.class);

		Assert.assertNotNull(orderService);
	}

	@Test
	public void testMfcodeByNum() {
		//凭证类型：二维码，核销类型：按份数  
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/mfcodeByNum.json", MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test11SingleSku() {
		// 凭证类型：身份证，核销类型：按份数 
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/11multiSku.json", MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testRealIDcard() {
		//凭证类型：身份证，核销类型：按规格
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/851694567296937984.json", MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testOneLevel() {
		//凭证类型：二维码，核销类型：按订单
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/oneLevel.json", MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testMfcodeBySku() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/mfcodeBySku.json", MultiOrderInModel.class);
		//凭证类型：二维码，核销类型：按规格
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void test122016() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/122016.json", MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(1496896245000l);
		String ss = sdf.format(date);
		System.out.println(ss);
		////////////
		long timeStart = sdf.parse("2017-06-09 12:30:45").getTime();
		System.out.println(timeStart);

	}

}

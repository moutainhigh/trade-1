package com.pzj.core.trade.order;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.model.CheckIdCardModel;
import com.pzj.trade.order.model.ContactlimitModel;
import com.pzj.trade.order.service.OrderValidateService;

/**
 * 多级分销订单测试类.
 * @author chj
 *
 */
public class OrderValidateServiceTest {

	ApplicationContext context;

	OrderValidateService orderValidateService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		orderValidateService = context.getBean(OrderValidateService.class);

		Assert.assertNotNull(orderValidateService);
	}

	@Test
	public void testCardId() {
		final CheckIdCardModel model = new CheckIdCardModel();
		model.setProductId(839809607457910784L);
		model.setSupplierId(0);
		model.setDate(new Date(1490284820000L));

		final Result<ArrayList<String>> result = orderValidateService.checkIdCardBuyable(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void testContact() {
		final ContactlimitModel model = new ContactlimitModel();
		model.setSpuId(847717397469958144L);
		model.setContactMobile("13076065570");
		model.setStartTime(new Date(1490284700000L));

		final Result<Integer> result = orderValidateService.getBuyCountByContactee(model, null);
		System.out.println(JSONConverter.toJson(result));
	}

}

package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.order.model.SettlementOrdersReqModel;
import com.pzj.trade.order.model.SettlementOrdersRespModel;
import com.pzj.trade.order.service.OrderQueryService;

/**
 * 售票员订单查询测试类.
 * @author YRJ
 *
 */
public class SettlementOrderMerchDetailQueryTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath:/applicationContext-test.xml");
		System.out.println(context);
	}

	private OrderQueryService orderQueryService;

	@Before
	public void setUp() {
		orderQueryService = context.getBean(OrderQueryService.class);
	}

	@Test
	public void testQueryOrdersDetailByOrderId() {
		final SettlementOrdersReqModel reqModel = new SettlementOrdersReqModel();
		reqModel.setOrderId("MF1589713659");
		final Result<SettlementOrdersRespModel> respModel = orderQueryService.queryOrdersDetailBySettlement(reqModel, null);
		Assert.assertNotNull(respModel);
		final SettlementOrdersRespModel order = respModel.getData();
		System.out.println(order.toString());
		Assert.assertTrue(respModel.isOk());
	}
}

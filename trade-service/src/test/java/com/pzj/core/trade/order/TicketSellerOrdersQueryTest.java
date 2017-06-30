package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.order.model.OperatorOrdersReqModel;
import com.pzj.trade.order.model.TicketSellerOrdersRespModel;
import com.pzj.trade.order.service.OrderQueryService;

/**
 * 售票员订单查询测试类.
 * @author YRJ
 *
 */
public class TicketSellerOrdersQueryTest {

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
	public void testOrdersByOrderId() {
		OperatorOrdersReqModel reqModel = new OperatorOrdersReqModel();
		reqModel.setOperatorId(2216619741564110L);
		reqModel.setTransactionId("MF598851283");
		Result<TicketSellerOrdersRespModel> respModel = orderQueryService.queryOrdersByTicketSeller(reqModel, null);
		Assert.assertNotNull(respModel);
		TicketSellerOrdersRespModel order = respModel.getData();
		System.out.println(order.getTotalAmount());
		Assert.assertTrue(respModel.isOk());
	}
}

package com.pzj.core.trade.order;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.model.OperatorOrdersReqModel;
import com.pzj.trade.order.model.TicketSellerOrderDetailReqModel;
import com.pzj.trade.order.model.TicketSellerOrdersRespModel;
import com.pzj.trade.order.service.OrderQueryService;

public class OrderQueryServiceForTicketTest {

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

	/**
	 * 售票点列表.
	 */
	@Test
	public void testQueryOrdersForTicket() {
		OperatorOrdersReqModel reqModel = new OperatorOrdersReqModel();
		reqModel.setOperatorId(2216619741563734L);
		System.out.println(JSONConverter.toJson(reqModel));
		//reqModel = ServiceTestData.getInstance().createTestData("/query/list.json", OperatorOrdersReqModel.class);
		Result<TicketSellerOrdersRespModel> result = orderQueryService.queryOrdersByTicketSeller(reqModel, null);
		System.out.println(JSONConverter.toJson(result));
	}

	/**
	 * 售票点详情.
	 */
	@Test
	public void testQueryOrderDetailForTicket() {
		TicketSellerOrderDetailReqModel reqModel = new TicketSellerOrderDetailReqModel();
		reqModel.setOperatorId(2216619741563734L);
		reqModel.setOrderId("2173417052400003");
		Result<SupplierOrderDetailResponse> result = orderQueryService.queryOrderDetailByTicketSeller(reqModel, null);
		System.out.println(JSONConverter.toJson(result));
	}

}

package com.pzj.core.trade.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.entity.OrderStrategyResponse;
import com.pzj.trade.order.service.OrderQueryService;
import com.pzj.trade.order.service.OrderService;
import com.pzj.trade.order.vo.PurchaseProductVO;
import com.pzj.trade.order.vo.ResellerOrderListVO;
import com.pzj.trade.order.vo.TradeOrderVO;

public class OrderServiceTest_All {
	static ApplicationContext context;

	private OrderQueryService orderQueryService;
	private OrderService orderService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		orderQueryService = context.getBean(OrderQueryService.class);
		orderService = context.getBean(OrderService.class);
		Assert.assertNotNull(orderQueryService);
		Assert.assertNotNull(orderService);
	}

	/*
	 * 交易创建订单
	 */
	@Test
	public void createOrder() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567546l);
		vo1.setProductNum(3);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566852l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566852l);
		vo.setRemark("下订单");
		vo.setContactMobile("13717611243");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	/*
	 * 查询订单和商品对应的政策列表
	 */
	@Test
	public void getOrderStrategy() throws Exception {

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<ArrayList<OrderStrategyResponse>> response = orderQueryService.getOrderStrategy("CS001", "1", context);
		Assert.assertNotNull(response);

		final int errCode = response.getErrorCode();
		System.out.println(JSONConverter.toJson(response));
		Assert.assertEquals(errCode, 10000);

		final List<OrderStrategyResponse> strategies = response.getData();
		Assert.assertNotNull(strategies);
		for (final OrderStrategyResponse orderStrategyResponse : strategies) {
			final Long id = orderStrategyResponse.getStrategyId();
			System.out.println("strategyid : " + id);
		}
	}

	/*
	 * 查询订单列表
	 */
	@Test
	public void queryOrdersByReseller() throws Exception {

		final ResellerOrderListVO vo = new ResellerOrderListVO();
		vo.setReseller_id(2216619736566852l);
		vo.setPage_size(3);
		vo.setCurrent_page(2);

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<com.pzj.framework.entity.QueryResult<OrderListResponse>> response = orderQueryService.queryOrdersByReseller(vo, context);
		System.out.println(JSONConverter.toJson(response));
		Assert.assertNotNull(response);

		Assert.assertEquals(response.getErrorCode(), 10000);
	}
}

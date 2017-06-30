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
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.service.OrderService;
import com.pzj.trade.order.vo.AgentOrderVO;
import com.pzj.trade.order.vo.PurchaseProductVO;
import com.pzj.trade.order.vo.TradeOrderVO;

public class CreateOrderServiceTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		System.out.println(context);
	}

	private OrderService orderService;

	@Before
	public void setUp() {
		orderService = context.getBean(OrderService.class);
	}

	@Test
	public void testAgengOrder() {
		final AgentOrderVO agentOrderVO = new AgentOrderVO();

		agentOrderVO.setAgent_order_id("1");
		agentOrderVO.setOperator_id(123);
		agentOrderVO.setOrder_id("MF16998507081111");

		final Result<Boolean> resp = orderService.updateAgentOrderId(agentOrderVO, null);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}

	/*
	 * 交易创建订单（直签单票）
	 */
	@Test
	public void createOrder_newProduct() throws Exception {

		final TradeOrderVO vo = new TradeOrderVO();
		new ArrayList<String>();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736566737l);

		vo1.setProductNum(3);
		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566871l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566871l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566871l);
		vo.setContactMobile("13717611243");
		vo.setIdcard_no("370881");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	/*
	 * 交易创建订单（直签单票）
	 */
	@Test
	public void createOrder_zq_dan() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();
		new ArrayList<String>();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736563717l);
		vo1.setProductNum(3);
		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566871l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566871l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566871l);
		vo.setContactMobile("13717611243");
		vo.setIdcard_no("370881");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrder_tongyong() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();
		new ArrayList<String>();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736763729l);
		vo1.setProductNum(3);
		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566871l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566871l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566871l);
		vo.setContactMobile("13717611243");
		vo.setIdcard_no("370881");

		final ServiceContext context = ServiceContext.getServiceContext();
		//		context.setTime(new Date());
		//
		//		OperationEnv env = new OperationEnv();
		//		env.setClientId("192.168.96.14");
		//		env.setClientId("unit-test");
		//		context.setOperationEnv(env);

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrder_zq_dan_houfan() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567543l);
		vo1.setProductNum(3);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736565760l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736565760l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736565760l);

		vo.setContactMobile("13717611243");
		vo.setIdcard_no("370881");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrder_mf_dan() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567553l);
		vo1.setProductNum(3);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566871l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566871l);
		vo.setRemark("下订单");
		vo.setContactMobile("13717611243");
		vo.setOperatorId(2216619736566871l);

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrder_zq_lian() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567546l);
		vo1.setProductNum(3);

		final List<PurchaseProductVO> products = new ArrayList<PurchaseProductVO>();

		//子产品1
		final PurchaseProductVO childVO1 = new PurchaseProductVO();
		childVO1.setProductId(2216619736567544l);
		childVO1.setProductNum(3);
		products.add(childVO1);

		//子产品2
		final PurchaseProductVO childVO2 = new PurchaseProductVO();
		childVO2.setProductId(2216619736567545l);
		childVO2.setProductNum(3);
		products.add(childVO2);

		//添加子产品到父产品
		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566852l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566852l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566852l);
		vo.setContactMobile("13717611243");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrder_mf_lian() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567569l);
		vo1.setProductNum(3);
		vo1.setVoucherId(System.currentTimeMillis());

		final List<PurchaseProductVO> products = new ArrayList<PurchaseProductVO>();

		//子产品1
		final PurchaseProductVO childVO1 = new PurchaseProductVO();
		childVO1.setProductId(2216619736567558l);
		childVO1.setProductNum(3);
		childVO1.setVoucherId(System.currentTimeMillis() + 33);
		products.add(childVO1);

		//子产品2
		final PurchaseProductVO childVO2 = new PurchaseProductVO();
		childVO2.setProductId(2216619736567562l);
		childVO2.setProductNum(3);
		childVO2.setVoucherId(System.currentTimeMillis() + 44);
		products.add(childVO2);

		//添加子产品到父产品
		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736565760l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736565760l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736565760l);
		vo.setContactMobile("13717611243");

		final Result<OrderResponse> res = orderService.createOrder(vo, null);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	/*
	 * 交易创建订单（直签单票）少林
	 */
	@Test
	public void createOrder_zq_dan_sl() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567594l);
		vo1.setProductNum(3);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566852l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566852l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566852l);
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
	 * 交易创建订单（直签单票）少林
	 */
	@Test
	public void createOrder_zq_dan_c() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567387l);
		vo1.setProductNum(1);
		vo1.setVoucherId(1464864104267L);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);
		vo.setGuideId(2216619736569570L);
		vo.setSalePort(3);
		vo.setPayerId(2216619736569576l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736569576l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736569576l);
		vo.setContactMobile("13717611243");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrder_mf_dan_228() throws Exception {
		final TradeOrderVO vo = new TradeOrderVO();

		//父产品
		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736568871l);
		vo1.setProductNum(3);
		final PurchaseProductVO vo2 = new PurchaseProductVO();
		vo2.setProductId(2216619736568870l);
		vo2.setProductNum(3);
		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736569576l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736569576l);
		vo.setRemark("下订单");
		vo.setContactMobile("13717611243");
		vo.setOperatorId(2216619736569576l);

		final ServiceContext context = ServiceContext.getServiceContext();
		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

}

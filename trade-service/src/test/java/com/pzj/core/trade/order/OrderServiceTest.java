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
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.service.OrderService;
import com.pzj.trade.order.vo.OrderRemarkVO;
import com.pzj.trade.order.vo.PurchaseProductVO;
import com.pzj.trade.order.vo.TradeOrderVO;

public class OrderServiceTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		Assert.assertNotNull(context);
	}

	private OrderService orderService;

	@Before
	public void setUp() {
		orderService = context.getBean(OrderService.class);
	}

	/*
	 * 测试单产品、分销模式.
	 */
	@Test
	public void testCreateOrderOnProduct() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(811754339369492480L);
		vo1.setProductNum(1);
		vo1.setVoucherId(1482742878398L);

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(3017465999654913L);
		vo.setProducts(plist);
		vo.setResellerId(3017465999654913L);
		vo.setRemark("测试单产品、分销模式");
		vo.setOperatorId(3017465999654913L);
		vo.setContactMobile("17761300308");
		vo.setIdcard_no("341322199206224312");

		Result<OrderResponse> res = orderService.createOrder(vo, null);
		Assert.assertNotNull(res);
		Assert.assertEquals(res.getErrorCode(), 10000);
		System.out.println(JSONConverter.toJson(res));
	}

	@Test
	public void createOrder_ln() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(813712629849268224L);
		vo1.setProductNum(1);
		vo1.setVoucherId(System.currentTimeMillis());

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(3);
		vo.setPayerId(2216619736763823L);
		vo.setProducts(plist);
		vo.setResellerId(2216619736763823L);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736763823L);
		vo.setContactMobile("13522330020");
		vo.setContactee("殷仁杰");
		vo.setIdcard_no("341322199206224312");

		List<FilledModel> filleds = new ArrayList<FilledModel>();
		FilledModel filled = new FilledModel();
		filled.setAttr_key("expect_to_shop_time");
		filled.setAttr_value("2016-12-23 03:30");
		filled.setGroup("other");
		filleds.add(filled);
		vo.setFilledModelList(filleds);

		Result<OrderResponse> res = orderService.createOrder(vo, null);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	@Test
	public void createOrderMoreProduct() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo2 = new PurchaseProductVO();
		vo2.setProductId(811754339369492480L);
		vo2.setProductNum(2);
		vo2.setVoucherId(System.currentTimeMillis());

		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(805652779146489857L);
		vo1.setProductNum(1);
		vo1.setVoucherId(System.currentTimeMillis());

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);
		plist.add(vo2);

		vo.setSalePort(3);
		vo.setPayerId(3017465999654913L);
		vo.setProducts(plist);
		vo.setResellerId(3017465999654913L);
		vo.setRemark("下订单");
		vo.setOperatorId(3017465999654913L);
		vo.setContactMobile("17761300308");
		vo.setContactee("wwww");
		vo.setIdcard_no("341322199206224312");

		List<FilledModel> filleds = new ArrayList<FilledModel>();
		FilledModel filled = new FilledModel();
		filled.setAttr_key("expect_to_shop_time");
		filled.setAttr_value("2016-12-23 03:30");
		filled.setGroup("other");
		filleds.add(filled);
		vo.setFilledModelList(filleds);

		Result<OrderResponse> res = orderService.createOrder(vo, null);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	/*
	 * 交易创建订单（直签单票/魔方单票）
	 */
	@Test
	public void createOrder_dan() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		//父产品
		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567553l);

		vo1.setProductNum(3);

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566871l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566871l);
		vo.setRemark("下订单");
		vo.setContactMobile("13717611243");
		vo.setContactee("me");
		vo.setOperatorId(2216619736566871l);

		ServiceContext context = ServiceContext.getServiceContext();

		Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);
	}

	/*
	 * 交易创建订单（直签联票/魔方联票，一个订单，两张产品）
	 */
	@Test
	public void createOrder_shuangchanpin() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		//第一个产品，父产品
		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567546l);
		vo1.setProductNum(3);
		vo1.setVoucherId(System.currentTimeMillis());

		List<PurchaseProductVO> products = new ArrayList<PurchaseProductVO>();

		//子产品1
		PurchaseProductVO childVO1 = new PurchaseProductVO();
		childVO1.setProductId(2216619736567544l);
		childVO1.setProductNum(3);
		childVO1.setVoucherId(System.currentTimeMillis() + 66);
		products.add(childVO1);

		//子产品2
		PurchaseProductVO childVO2 = new PurchaseProductVO();
		childVO2.setProductId(2216619736567545l);
		childVO2.setProductNum(3);
		childVO2.setVoucherId(System.currentTimeMillis() + 77);
		products.add(childVO2);

		//第二个产品，父产品
		PurchaseProductVO vo2 = new PurchaseProductVO();
		vo2.setProductId(2216619736567552l);
		vo2.setProductNum(3);
		vo2.setVoucherId(System.currentTimeMillis());

		List<PurchaseProductVO> products2 = new ArrayList<PurchaseProductVO>();

		//子产品3
		PurchaseProductVO childVO3 = new PurchaseProductVO();
		childVO3.setProductId(2216619736567547l);
		childVO3.setProductNum(3);
		childVO3.setVoucherId(System.currentTimeMillis() + 99);
		products2.add(childVO3);

		//子产品4
		PurchaseProductVO childVO4 = new PurchaseProductVO();
		childVO4.setProductId(2216619736567549l);
		childVO4.setProductNum(3);
		childVO4.setVoucherId(System.currentTimeMillis() + 111);
		products2.add(childVO4);

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);
		plist.add(vo2);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566852l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566852l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566852l);
		vo.setContactMobile("13717611243");

		ServiceContext context = ServiceContext.getServiceContext();

		Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	@Test
	public void testConfirm() {
		TradeOrderVO vo = new TradeOrderVO();
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566852l);
		vo.setContactMobile("13717611243");
		//父产品
		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567546l);
		vo1.setProductNum(3);

		List<PurchaseProductVO> products = new ArrayList<PurchaseProductVO>();

		//子产品1
		PurchaseProductVO childVO1 = new PurchaseProductVO();
		childVO1.setProductId(2216619736567544l);
		childVO1.setProductNum(3);
		products.add(childVO1);

		//子产品2
		PurchaseProductVO childVO2 = new PurchaseProductVO();
		childVO2.setProductId(2216619736567545l);
		childVO2.setProductNum(3);
		products.add(childVO2);

		//添加子产品到父产品
		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566852l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736L);

	}

	@Test
	public void createOrder_zl() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		//父产品
		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567546l);
		vo1.setProductNum(3);

		List<PurchaseProductVO> products = new ArrayList<PurchaseProductVO>();

		//子产品1
		PurchaseProductVO childVO1 = new PurchaseProductVO();
		childVO1.setProductId(2216619736567544l);
		childVO1.setProductNum(3);
		products.add(childVO1);

		//子产品2
		PurchaseProductVO childVO2 = new PurchaseProductVO();
		childVO2.setProductId(2216619736567545l);
		childVO2.setProductNum(3);
		products.add(childVO2);

		//添加子产品到父产品
		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566852l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566852l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566852l);
		vo.setContactMobile("13717611243");

		ServiceContext context = ServiceContext.getServiceContext();

		Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	/*
	   * 交易创建订单（直签单票/魔方单票）
	   */

	@Test
	public void createOrder_zq_dan() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619736567541l);

		vo1.setProductNum(3);

		long voucherId = System.currentTimeMillis() + 999;
		vo1.setVoucherId(voucherId);

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736566871l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736566871l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619736566871l);
		vo.setContactMobile("13717611243");

		ServiceContext context = ServiceContext.getServiceContext();

		Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	@Test
	public void testCreateOrderRemark() {
		OrderRemarkVO orderRemarkVO = new OrderRemarkVO();
		orderRemarkVO.setOrder_id("MF708212626");
		orderRemarkVO.setRemark("it's remark");
		orderRemarkVO.setOperator_id(123);
		orderRemarkVO.setOperator_name("123");
		orderRemarkVO.setOperator_type(1);
		//        orderService.CreateOrderRemark(orderRemarkVO, null);

	}
}

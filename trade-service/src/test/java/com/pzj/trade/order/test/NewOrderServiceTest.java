package com.pzj.trade.order.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.pzj.trade.confirm.service.ConfirmService;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.model.OrderRemarkModel;
import com.pzj.trade.order.service.OrderService;
import com.pzj.trade.order.vo.PurchaseProductVO;
import com.pzj.trade.order.vo.TradeOrderVO;
import com.pzj.voucher.entity.VoucherEntity;
import com.pzj.voucher.service.VoucherService;
import com.pzj.voucher.vo.InitVoucherVo;

public class NewOrderServiceTest {

	static ApplicationContext context;

	private OrderService orderService;
	private VoucherService voucherService;
	private ConfirmService confirmService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		orderService = context.getBean(OrderService.class);
		Assert.assertNotNull(orderService);
		voucherService = context.getBean(VoucherService.class);

		confirmService = context.getBean(ConfirmService.class);
		Assert.assertNotNull(confirmService);

	}

	/*
	 * 普通产品
	 */
	@Test
	public void createOrder() throws Exception {
		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();

		vo.setPayerId(2216619736563779L);
		vo.setResellerId(2216619736563779L);
		vo.setOperatorId(2216619736563779L);
		vo.setTravel(0);
		vo.setTravelDepartId(0L);
		vo.setGuideId(0);
		vo.setResellerAgentId(0);
		vo.setSupplierAgentId(0);
		vo.setSalePort(3);
		vo.setContactMobile("17781237326");
		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		PurchaseProductVO pvo = new PurchaseProductVO();
		plist.add(pvo);
		pvo.setProductId(814675958257389568L);
		pvo.setIsTicket(false);
		pvo.setProductNum(2);
		pvo.setConfirm(false);
		pvo.setAgent_flag(0);
		pvo.setPrice(0.0);
		pvo.setChannelId(0);
		pvo.setSupplierId(0);
		pvo.setPlayDate(new Date());
		pvo.setVoucherId(1476150880032L);
		vo.setProducts(plist);
		try {
			Result<OrderResponse> res = orderService.createOrder(vo, null);
			System.out.println(JSONConverter.toJson(res));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 通用产品
	 */
	@Test
	public void createOrderTongGong() throws Exception {

		InitVoucherVo initVoucherVo = new InitVoucherVo();
		initVoucherVo.setContactName("chai");
		//		initVoucherVo.setSupplierId(2216619746563727l);
		initVoucherVo.setVoucherContent("130730198801171232");
		initVoucherVo.setVoucherContentType(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		initVoucherVo.setStartTime(sdf.parse("20160815"));
		//		initVoucherVo.setExpireTime(sdf.parse("20160817"));
		initVoucherVo.setVoucherCategory(9000);
		Result<VoucherEntity> result = voucherService.createVoucher(initVoucherVo);
		Long voucherId = result.getData().getVoucherId();

		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();
		//通用产品
		vo1.setProductId(2216619736566744l);

		vo1.setProductNum(1);
		vo1.setVoucherId(voucherId);

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619746563745l);
		vo.setProducts(plist);
		vo.setResellerId(2216619746563745l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619746563745l);
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
	 * 普通产品
	 */
	@Test
	public void createOrder5() throws Exception {

		InitVoucherVo initVoucherVo = new InitVoucherVo();
		initVoucherVo.setContactName("chai");
		//		initVoucherVo.setSupplierId(2216619746563727l);
		initVoucherVo.setVoucherContent("130730198801171232");
		initVoucherVo.setVoucherContentType(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		initVoucherVo.setStartTime(sdf.parse("20160815"));
		//		initVoucherVo.setExpireTime(sdf.parse("20160817"));
		initVoucherVo.setVoucherCategory(9000);
		Result<VoucherEntity> result = voucherService.createVoucher(initVoucherVo);
		Long voucherId = result.getData().getVoucherId();

		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619741564163l);
		vo1.setProductNum(1);
		vo1.setVoucherId(voucherId);

		PurchaseProductVO vo2 = new PurchaseProductVO();
		vo2.setProductId(2216619741564162l);
		vo2.setProductNum(1);
		vo2.setVoucherId(voucherId);

		PurchaseProductVO vo3 = new PurchaseProductVO();
		vo3.setProductId(2216619741564161l);
		vo3.setProductNum(1);
		vo3.setVoucherId(voucherId);

		PurchaseProductVO vo4 = new PurchaseProductVO();
		vo4.setProductId(2216619741564160l);
		vo4.setProductNum(1);
		vo4.setVoucherId(voucherId);

		PurchaseProductVO vo5 = new PurchaseProductVO();
		vo5.setProductId(2216619741564159l);
		vo5.setProductNum(1);
		vo5.setVoucherId(voucherId);

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);
		plist.add(vo2);
		plist.add(vo3);
		plist.add(vo4);
		plist.add(vo5);

		vo.setSalePort(4);
		vo.setPayerId(2216619736763940l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736763940l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619746563745l);
		vo.setContactMobile("13717611243");

		ServiceContext context = ServiceContext.getServiceContext();
		//		context.setTime(new Date());
		//		OperationEnv env = new OperationEnv();
		//		env.setClientId("192.168.96.14");
		//		env.setClientId("unit-test");
		//		context.setOperationEnv(env);

		Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	@Test
	public void createOrder_chai() throws Exception {

		InitVoucherVo initVoucherVo = new InitVoucherVo();
		initVoucherVo.setContactName("chai");
		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		initVoucherVo.setStartTime(sdf.parse("20161215"));
		//		initVoucherVo.setExpireTime(sdf.parse("20161217"));

		initVoucherVo.setVoucherContentType(1);
		initVoucherVo.setVoucherContent("130730198801171232");
		//		initVoucherVo.setSupplierId(2216619736563723l);
		initVoucherVo.setVoucherCategory(1000);
		Result<VoucherEntity> result = voucherService.createVoucher(initVoucherVo);
		Long voucherId = result.getData().getVoucherId();

		TradeOrderVO vo = new TradeOrderVO();

		PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(808958932201963520l);

		vo1.setProductNum(1);
		vo1.setVoucherId(voucherId);
		//		vo1.setPlay_start_time(initVoucherVo.getStartTime());
		//		vo1.setPlay_end_time(initVoucherVo.getExpireTime());

		List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(7);
		vo.setProducts(plist);

		vo.setPayerId(2216619741564210l);
		vo.setResellerId(2216619736563717L);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619741564210l);
		vo.setContactMobile("13717611243");
		vo.setContactee("test");

		Result<OrderResponse> res = orderService.createOrder(vo, null);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	@Test
	public void testUpdateRemark() {
		OrderRemarkModel orderRemarkModel = new OrderRemarkModel();
		orderRemarkModel.setOperator(123);
		orderRemarkModel.setOrder_id("MF556418617");
		orderRemarkModel.setRemark("修改楼");
		//orderService.updateOrderRemark(orderRemarkModel, null);
	}
}

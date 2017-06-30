package com.pzj.core.trade.order;

import java.text.SimpleDateFormat;
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
import com.pzj.trade.order.entity.OrderResponse;
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

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		orderService = context.getBean(OrderService.class);
		Assert.assertNotNull(orderService);
		voucherService = context.getBean(VoucherService.class);

	}

	/*
	 * 普通产品
	 */
	@Test
	public void createOrder() throws Exception {
		final InitVoucherVo initVoucherVo = new InitVoucherVo();
		initVoucherVo.setContactName("chai");
		//		initVoucherVo.setSupplierId(2216619746563727l);
		initVoucherVo.setVoucherContent("130730198801171232");
		initVoucherVo.setVoucherContentType(1);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		initVoucherVo.setStartTime(sdf.parse("20160815"));
		//		initVoucherVo.setExpireTime(sdf.parse("20160817"));
		initVoucherVo.setVoucherCategory(9000);
		final Result<VoucherEntity> result = voucherService.createVoucher(initVoucherVo);
		final Long voucherId = result.getData().getVoucherId();

		final TradeOrderVO vo = new TradeOrderVO();

		final PurchaseProductVO vo1 = new PurchaseProductVO();
		//测试退款-前分比供金-后分比供金
		vo1.setProductId(2216619741564232l);

		vo1.setProductNum(3);
		vo1.setVoucherId(voucherId);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619736763940l);
		vo.setProducts(plist);
		vo.setResellerId(2216619736763940l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619746563745l);
		vo.setContactMobile("13717611243");
		vo.setContactee("test");

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

	/*
	 * 通用产品
	 */
	@Test
	public void createOrderTongGong() throws Exception {

		final InitVoucherVo initVoucherVo = new InitVoucherVo();
		initVoucherVo.setContactName("chai");
		//		initVoucherVo.setSupplierId(2216619746563727l);
		initVoucherVo.setVoucherContent("130730198801171232");
		initVoucherVo.setVoucherContentType(1);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		initVoucherVo.setStartTime(sdf.parse("20160814"));
		//		initVoucherVo.setExpireTime(sdf.parse("20160814"));
		initVoucherVo.setVoucherCategory(9000);
		final Result<VoucherEntity> result = voucherService.createVoucher(initVoucherVo);
		final Long voucherId = result.getData().getVoucherId();

		final TradeOrderVO vo = new TradeOrderVO();

		final PurchaseProductVO vo1 = new PurchaseProductVO();
		//通用产品
		vo1.setProductId(2216619736566744l);

		vo1.setProductNum(1);
		vo1.setVoucherId(voucherId);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
		plist.add(vo1);

		vo.setSalePort(4);
		vo.setPayerId(2216619746563745l);
		vo.setProducts(plist);
		vo.setResellerId(2216619746563745l);
		vo.setRemark("下订单");
		vo.setOperatorId(2216619746563745l);
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
	 * 普通产品
	 */
	@Test
	public void createOrder5() throws Exception {

		final InitVoucherVo initVoucherVo = new InitVoucherVo();
		initVoucherVo.setContactName("chai");
		//		initVoucherVo.setSupplierId(2216619746563727l);
		initVoucherVo.setVoucherContent("130730198801171232");
		initVoucherVo.setVoucherContentType(1);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//		initVoucherVo.setStartTime(sdf.parse("20160815"));
		//		initVoucherVo.setExpireTime(sdf.parse("20160817"));
		initVoucherVo.setVoucherCategory(9000);
		final Result<VoucherEntity> result = voucherService.createVoucher(initVoucherVo);
		final Long voucherId = result.getData().getVoucherId();

		final TradeOrderVO vo = new TradeOrderVO();

		final PurchaseProductVO vo1 = new PurchaseProductVO();
		vo1.setProductId(2216619741564163l);
		vo1.setProductNum(1);
		vo1.setVoucherId(voucherId);

		final PurchaseProductVO vo2 = new PurchaseProductVO();
		vo2.setProductId(2216619741564162l);
		vo2.setProductNum(1);
		vo2.setVoucherId(voucherId);

		final PurchaseProductVO vo3 = new PurchaseProductVO();
		vo3.setProductId(2216619741564161l);
		vo3.setProductNum(1);
		vo3.setVoucherId(voucherId);

		final PurchaseProductVO vo4 = new PurchaseProductVO();
		vo4.setProductId(2216619741564160l);
		vo4.setProductNum(1);
		vo4.setVoucherId(voucherId);

		final PurchaseProductVO vo5 = new PurchaseProductVO();
		vo5.setProductId(2216619741564159l);
		vo5.setProductNum(1);
		vo5.setVoucherId(voucherId);

		final List<PurchaseProductVO> plist = new ArrayList<PurchaseProductVO>();
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

		final ServiceContext context = ServiceContext.getServiceContext();

		final Result<OrderResponse> res = orderService.createOrder(vo, context);
		Assert.assertNotNull(res);

		System.out.println(com.pzj.framework.converter.JSONConverter.toJson(res));
		Assert.assertEquals(res.getErrorCode(), 10000);

		final String orderId = res.getData().getOrderId();
		System.out.println(">>>orderId : " + orderId);

	}

}

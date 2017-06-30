package com.pzj.core.trade.pay;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.book.ServiceTestData;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.service.OrderService;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.service.PaymentService;
import com.pzj.trade.payment.service.PaymentTaskService;
import com.pzj.trade.payment.vo.ChildOrderPaymentModel;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.vo.PaymentVO;

public class PaymentServiceNewTest {
	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		System.out.println(context);
	}

	private PaymentService paymentService;
	private OrderService orderService;
	private PaymentTaskService paymentTaskService;

	final int source = 1;

	@Before
	public void setUp() {
		paymentService = context.getBean(PaymentService.class);
		orderService = context.getBean(OrderService.class);
		paymentTaskService = context.getBean(PaymentTaskService.class);
	}

	@Test
	public void createOrderAndPay() {
		MultiOrderInModel model = ServiceTestData.getInstance().createTestData("/createOrder/122016.json",
				MultiOrderInModel.class);
		Result<OrderResponse> result = orderService.createMultilevelOrder(model, null);
		System.out.println(JSONConverter.toJson(result));

		if (result.isOk() && !Check.NuNObject(result.getData())) {
			final PaymentVO payment = new PaymentVO();
			payment.setResellerId(result.getData().getReseller_id());
			payment.setOrderId(result.getData().getOrderId());
			payment.setPayType(1);
			payment.setSource(source);
			final Result<PaymentResult> payresult = paymentService.payOrder(payment, null);
			final String json = JSONConverter.toJson(payresult.getData());
			System.out.println(json);
			System.out.println(result.getData().getOrderId());
		}
	}

	/**
	 * 后付款场景, 主订单为线上支付, 二级子订单为线上支付, 三级子订单为后付方式.
	 * 预期结果: 主子订单状态全部变更为已付款, 且主订单、二级子订单生成付款流水, 三级子订单无付款流水.
	 */
	@Test
	public void testMasterOnlineAndSecondOnlineAndThirdAfterPayment() {
		final PaymentVO payment = new PaymentVO();
		payment.setOrderId("1092117042010011");
		payment.setPayType(1);
		payment.setSource(source);
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}

	@Test
	public void testChildOrderPay() {
		ChildOrderPaymentModel pay = new ChildOrderPaymentModel();
		pay.setOrderId("1046517042700105");
		pay.setResellerId(3908750088273921L);
		pay.setMakeUp(false);
		final Result<Boolean> result = paymentService.payChildOrder(pay, null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}

	@Test
	public void testThirdPayCallBack() {
		PayCallbackVO vo = new PayCallbackVO();
		vo.setBankCharges(0);
		vo.setDealId("122544545DDDD");
		vo.setMoney(20.0);
		vo.setOrderId("1074917041410019");
		vo.setPayType(2);
		vo.setSeller_email("54848484848");
		vo.setSuccess(true);
		final Result<Boolean> result = paymentService.payCallback(vo, null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}

	@Test
	public void testPaymentCancel() {
		String saleOrderId = "1092117050300016";
		final Result<Boolean> result = paymentTaskService.cancelPaymentSinge(saleOrderId, null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}

	@Test
	public void testPaymentCancelBatch() {
		final Result<Boolean> result = paymentTaskService.batchCancelPayment(null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}
}

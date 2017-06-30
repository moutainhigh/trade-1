package com.pzj.core.trade.pay;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.service.PaymentService;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.vo.PaymentVO;

public class PayTest {

	private static final Logger logger = LoggerFactory.getLogger(PayTest.class);

	private static ApplicationContext context;

	private static PaymentService paymentService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		paymentService = context.getBean(PaymentService.class);
	}

	@Test
	public void testPayOrder() {
		final PaymentVO payment = new PaymentVO();
		payment.setResellerId(2216619736763823L);
		payment.setOrderId("MF869445278");
		payment.setCurrency(1);
		payment.setSource(3);
		payment.setPayType(2);
		payment.setFailpayUrl("http://wechat.stage.mftour.net/order/failed");
		payment.setReturnUrl("http://wechat.stage.mftour.net/order/success");
		payment.setRid("2216619741564110");
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);

		if (result.isOk()) {
			logger.info(result.getData().toString());
		} else {
			logger.info(result.getErrorCode() + result.getErrorMsg());
		}
	}

	@Test
	public void testPayCallback() {
		final PayCallbackVO payVO = new PayCallbackVO();
		paymentService.payCallback(payVO, null);
	}

}

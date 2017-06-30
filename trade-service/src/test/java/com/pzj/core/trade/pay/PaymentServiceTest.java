package com.pzj.core.trade.pay;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.service.PaymentService;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.vo.PaymentVO;

public class PaymentServiceTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		System.out.println(context);
	}

	private PaymentService paymentService;

	final int source = 1;

	@Before
	public void setUp() {
		paymentService = context.getBean(PaymentService.class);
	}

	/**
	 * 测试非法的请求模型, 验证异常返回.
	 */
	@Test
	public void testIllegalReqModel() {
		final PaymentVO payment = new PaymentVO();
		payment.setResellerId(125L);
		/*payment.setOrderId("101242017041110002");*///当参数orderId为空情况下, 将返回异常码: 10101
		payment.setSource(source);
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isOk());
		System.out.println("result: " + JSONConverter.toJson(result));
	}

	/**
	 * 后付款场景, 各级订单全后付方式.
	 * 预期结果: 付款申请即可完成整个订单链的付款, 主订单及各级子订单全部为付款成功.
	 */
	@Test
	public void testAllAfterPayment() {
		final PaymentVO payment = new PaymentVO();
		payment.setResellerId(2216619736563743L);
		payment.setOrderId("1074917041410001");
		payment.setSource(source);
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);

		Assert.assertNotNull(result);
		Assert.assertFalse(result.isOk());
		System.out.println("result: " + JSONConverter.toJson(result));
	}

	/**
	 * 后付款场景, 主订单为纯余额支付, 子订单全部为后付方式.
	 * 预期结果: 主订单申请成功直接调用回调函数, 并各级子订单全部为付款成功. 子订单无账号变动.
	 */
	@Test
	public void testMasterOnlineAndChildAfterPayment() {
		final PaymentVO payment = new PaymentVO();
		payment.setResellerId(2216619736770352L);
		payment.setOrderId("1074917041410009");
		payment.setSource(source);
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);
		//		Assert.assertNotNull(result);
		//		Assert.assertFalse(result.isOk());
		System.out.println("result: " + JSONConverter.toJson(result));
	}

	/**
	 * 后付款场景, 主订单为线上支付, 二级子订单为线上支付, 三级子订单为后付方式.
	 * 预期结果: 主子订单状态全部变更为已付款, 且主订单、二级子订单生成付款流水, 三级子订单无付款流水.
	 */
	@Test
	public void testMasterOnlineAndSecondOnlineAndThirdAfterPayment() {
		final PaymentVO payment = new PaymentVO();
		payment.setResellerId(125L);
		payment.setOrderId("101242017041110002");
		payment.setSource(source);
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}

	/**
	 * 线上支付场景, 主订单为纯余额或混合或第三方, 子订单全部为货款支付.
	 * 预期结果: 各级订单状态变更为已付款, 且各级子订单全部生成付款流水.
	 */
	@Test
	public void testAllOnlinePayment() {
		final PaymentVO payment = new PaymentVO();
		payment.setResellerId(125L);
		payment.setOrderId("1074917041410013");
		payment.setPayType(1);
		payment.setSource(source);
		final Result<PaymentResult> result = paymentService.payOrder(payment, null);
		final String json = JSONConverter.toJson(result.getData());
		System.out.println(json);
	}

	@Test
	public void testPayCallBack() {
		final PayCallbackVO payVo = new PayCallbackVO();
		payVo.setOrderId("MF942582988");
		payVo.setDealId("112345");
		payVo.setMoney(100);
		payVo.setPayType(2);
		payVo.setSuccess(true);
		payVo.setSeller_email("1324790401");
		payVo.setBankCharges(0.06);
		paymentService.payCallback(payVo, null);
	}
}

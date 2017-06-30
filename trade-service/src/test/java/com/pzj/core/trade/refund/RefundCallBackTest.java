package com.pzj.core.trade.refund;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.refund.service.RefundService;

public class RefundCallBackTest {

	private static ApplicationContext context;

	private static RefundService refundService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		refundService = context.getBean(RefundService.class);
	}

	@Test
	public void testRefundCallback() {

		Result<Boolean> result = null;
		try {

		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (!result.isOk()) {
			System.out.println(result.getErrorMsg());
		}

	}

}

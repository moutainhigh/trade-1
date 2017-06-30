package com.pzj.core.trade.refund;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.refund.engine.common.RefundAuditPartyEnum;
import com.pzj.core.trade.refund.engine.common.RefundAuditResultEnum;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.refund.model.RefundAuditReqModel;
import com.pzj.trade.refund.service.RefundAuditService;

public class RefundAuditTest {

	private static ApplicationContext context;

	private static RefundAuditService refundAuditService;

	private static ServiceContext serviceContext = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		refundAuditService = context.getBean(RefundAuditService.class);
	}

	@Test
	public void testRefundAudit() {
		final RefundAuditReqModel reqModel = new RefundAuditReqModel();

		reqModel.setRefundId("cff5369841ae4e16a0a0e409dd3e10be");
		reqModel.setSaleOrderId("102002017040710007");
		reqModel.setAuditorId(1234567890L);
		reqModel.setAuditorParty(RefundAuditPartyEnum.PLATFORM.getParty());
		reqModel.setAuditResult(RefundAuditResultEnum.PASS.getResult());
		reqModel.setRefusedMsg("退款通过");

		Result<Boolean> result = null;
		try {
			result = refundAuditService.refundAudit(reqModel, serviceContext);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		if (!result.isOk()) {
			System.out.println(result.getErrorMsg());
		}

	}

}

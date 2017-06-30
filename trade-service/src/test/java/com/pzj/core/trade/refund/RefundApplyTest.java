package com.pzj.core.trade.refund;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.service.RefundApplyService;

public class RefundApplyTest {

	private static ApplicationContext context;

	private static RefundApplyService refundApplyService;

	private static ServiceContext serviceContext = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		refundApplyService = context.getBean(RefundApplyService.class);
	}

	@Test
	//测试部分退款
	public void testPartRefundApply() {

		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setInitiatorId(3180074621206529L);
		reqModel.setOrderId("102002017040710007");

		//		final List<RefundMerchandiseVO> refundMerches = new ArrayList<RefundMerchandiseVO>();
		//		final RefundMerchandiseVO rm1 = new RefundMerchandiseVO();
		//		rm1.setMerchId("P1126434219");
		//		rm1.setRefundNum(1);
		//		refundMerches.add(rm1);
		//
		//		final RefundMerchandiseVO rm2 = new RefundMerchandiseVO();
		//		rm2.setMerchId("P927908650");
		//		rm2.setRefundNum(1);
		//		refundMerches.add(rm2);
		//		final RefundMerchandiseVO rm3 = new RefundMerchandiseVO();
		//		rm3.setMerchId("P1797801382");
		//		rm3.setRefundNum(1);
		//		refundMerches.add(rm3);
		//
		//		final RefundMerchandiseVO rm4 = new RefundMerchandiseVO();
		//		rm4.setMerchId("P1935137165");
		//		rm4.setRefundNum(1);
		//		refundMerches.add(rm4);
		//		reqModel.setRefundMerches(refundMerches);

		reqModel.setInitParty(RefundInitPartyEnum.SUPPORT.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.FORCE.getType());
		Result<Boolean> result = null;
		try {

			result = refundApplyService.refundApply(reqModel, serviceContext);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (!result.isOk()) {
			System.out.println(result.getErrorMsg());
		}
	}

	@Test
	//测试部分退款
	public void testAllRefundApply() {
		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setInitiatorId(2216619741564110L);
		reqModel.setOrderId("MF282146902");

		reqModel.setInitParty(RefundInitPartyEnum.SUPPORT.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.GENERAL.getType());

		Result<Boolean> result = null;
		try {

			result = refundApplyService.refundApply(reqModel, serviceContext);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (!result.isOk()) {
			System.out.println(result.getErrorMsg());
		}
	}

}

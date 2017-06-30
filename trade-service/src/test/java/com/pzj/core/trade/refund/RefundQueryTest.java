package com.pzj.core.trade.refund;

import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.refund.model.QueryRefundApplyReqModel;
import com.pzj.trade.refund.service.RefundQueryService;
import com.pzj.trade.refund.vo.ForceRefundApplyVO;

public class RefundQueryTest {

	private static ApplicationContext context;

	private static RefundQueryService refundQueryService;

	private static ServiceContext serviceContext = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		refundQueryService = context.getBean(RefundQueryService.class);
	}

	@Test
	public void testQuereyRefundApply() throws ParseException {
		final QueryRefundApplyReqModel reqModel = new QueryRefundApplyReqModel();
		//reqModel.setOrderId("MF59987854");
		//reqModel.setAuditState(1);
		//		reqModel.setAuditState(3);
		//		reqModel.setThirdAuditState(1);
		//		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//
		//		final Date d1 = sdf.parse("2017-01-03");
		//		final Date d2 = sdf.parse("2017-01-05");
		//		reqModel.setStartApplyDate(d1);
		//		reqModel.setEndApplyDate(d2);

		Result<QueryResult<ForceRefundApplyVO>> queryResult = null;
		try {
			queryResult = refundQueryService.queryRefundApplyPage(reqModel, serviceContext);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final List<ForceRefundApplyVO> records = queryResult.getData().getRecords();
		for (final ForceRefundApplyVO vo : records) {
			System.out.println(vo);
			System.out.println(vo.getOrderId());
		}
	}

}

package com.pzj.core.trade.financeCenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.trade.financeCenter.request.SettleDetailReqModel;
import com.pzj.trade.financeCenter.request.SettleGatherReqModel;
import com.pzj.trade.financeCenter.response.SettleDetailRepModel;
import com.pzj.trade.financeCenter.service.FinanceCenterQueryService;

public class FinanceCenterQueryServiceTest {

	private static ApplicationContext context;

	private final Calendar calendar = Calendar.getInstance();
	private FinanceCenterQueryService financeCenterQueryService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		financeCenterQueryService = context.getBean(FinanceCenterQueryService.class);
	}

	@Test
	public void settleGatherTest() {

		final SettleGatherReqModel reqModel = new SettleGatherReqModel();
		reqModel.setUserId(2216619741563734L);

		reqModel.setUserRole(1);
		calendar.add(Calendar.MONTH, -6);
		reqModel.setBeginDate(calendar.getTime());
		calendar.add(Calendar.MONTH, 6);
		reqModel.setEndDate(calendar.getTime());
		financeCenterQueryService.settleGather(reqModel, null);
	}

	@Test
	public void settleDetailTest() throws ParseException {
		final SettleDetailReqModel reqModel = new SettleDetailReqModel();

		reqModel.setUserId(2216619736664384L);
		/**SAAS用户角色，默认为1；
		 * <li>1,供应商</li>
		 * <li>2,分销商</li>
		 */
		reqModel.setUserRole(1);
		/**
		 * 结算状态，默认为1；
		 * <li>0,待结算</li>
		 * <li>1,已结算</li>
		 */
		reqModel.setSettleState(1);
		/**
		 * 线上线下，默认为1；
		 *
		 * <li>0,线下</li>
		 * <li>1,线上</li>
		 */
		reqModel.setOnline(1);

		//		reqModel.setProductName("dcf");
		//		reqModel.setRelativeUserId(2216619741563734L);
		//		reqModel.setTradeState(0);
		//		reqModel.setTransactionId("");

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		reqModel.setBeginDate(sdf.parse("2017-05-01"));
		reqModel.setEndDate(sdf.parse("2017-05-25"));

		final Result<SettleDetailRepModel> result = financeCenterQueryService.settleDetail(reqModel, null);

		System.out.println(result.getErrorMsg());
		final SettleDetailRepModel data = result.getData();
		System.out.println(data);
	}
}

package com.pzj.core.trade.withdraw;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.withdraw.entity.WithdrawDetailResponse;
import com.pzj.trade.withdraw.model.CashPostalModel;
import com.pzj.trade.withdraw.model.WithdrawReqParameter;
import com.pzj.trade.withdraw.service.CashPostalService;
import com.pzj.trade.withdraw.service.WithdrawQueryService;

public class WithdrawTest {

	private static ApplicationContext context;

	private static CashPostalService cashPostalService;
	
	private static WithdrawQueryService withdrawQueryService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		cashPostalService = context.getBean(CashPostalService.class);
		withdrawQueryService = context.getBean(WithdrawQueryService.class);
	}

	@Test
	public void testWithdraw() {
		final CashPostalModel cashPostal = new CashPostalModel();
		cashPostal.setAccountId(3905010602082305L);
		cashPostal.setCashPostalMoney(300);
		cashPostal.setResellerType(0);
		cashPostal.setUserType(6);
		Result<Boolean> result = cashPostalService.cashPostal(cashPostal, null);
		System.out.println(result);
	}
	
	@Test
	public void testQueryWithdrawDetail() {
		WithdrawReqParameter param =new WithdrawReqParameter();
		param.setAccountId(3905010602082305L);
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, -6);
		System.out.println(c.getTime());
		param.setStartTime(c.getTime());
		c.add(Calendar.MONTH, 6);
		System.out.println(c.getTime());
		param.setEndTime(c.getTime());
		final Result<QueryResult<WithdrawDetailResponse>> resp = withdrawQueryService.queryWithdrawDetail(param);
		Assert.assertNotNull(resp);
		System.out.println(JSONConverter.toJson(resp));
	}
}

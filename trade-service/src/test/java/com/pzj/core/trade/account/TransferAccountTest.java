/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.account;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.model.TransferAccountsReqModel;
import com.pzj.trade.order.service.TransferAccountsService;
import com.pzj.trade.order.vo.OrderTransferAccountsVO;

/**
 * 分账测试
 * @author DongChunfu
 * @version $Id: TransferAccountTest.java, v 0.1 2017年3月25日 下午3:25:52 DongChunfu Exp $
 */
public class TransferAccountTest {
	private static ApplicationContext context;

	private static TransferAccountsService transferAccountsService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		transferAccountsService = context.getBean(TransferAccountsService.class);
	}

	@Test
	//转账查询测试
	public void testTransferQuery() {
		try {
			final TransferAccountsReqModel reqModel = new TransferAccountsReqModel();
			reqModel.setTransactionId("1089717051700009"); //非微店
			//			reqModel.setTransactionId("102002017040710008");//微店
			//			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//			reqModel.setConfirmStartTime(sdf.parse("2016-1-12"));
			//			reqModel.setConfirmEndTime(sdf.parse("2017-8-12"));
			//			reqModel.setPayStartTime(sdf.parse("2016-1-12"));
			//			reqModel.setPayEndTime(sdf.parse("2017-8-12"));
			reqModel.setCurrentPage(1);
			final Result<QueryResult<OrderTransferAccountsVO>> queryResult = transferAccountsService
					.queryTransferAccountsDetail(reqModel, (ServiceContext) null);
			System.out.println(JSONConverter.toJson(queryResult));
			if (queryResult.isOk()) {
				System.out.println(JSONConverter.toJson(queryResult.getData()));
				//				final QueryResult<OrderTransferAccountsVO> queryResultData = queryResult.getData();
				//				final List<OrderTransferAccountsVO> records = queryResultData.getRecords();
				//				if (!Check.NuNObject(records)) {
				//					for (final OrderTransferAccountsVO detail : records) {
				//						System.out.println(detail);
				//					}
				//				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}

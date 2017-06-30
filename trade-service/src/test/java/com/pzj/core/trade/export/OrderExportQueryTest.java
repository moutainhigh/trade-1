
package com.pzj.core.trade.export;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.export.model.OrderExportQueryReqModel;
import com.pzj.trade.export.model.OrderExportRepModel;
import com.pzj.trade.export.model.OrderExportVerifyRepModel;
import com.pzj.trade.export.model.OrderExportVerifyReqModel;
import com.pzj.trade.export.service.OrderExportQueryService;

/**
 * 订单导出查询测试
 * @author DongChunfu
 */
public class OrderExportQueryTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderExportQueryTest.class);

	ApplicationContext context;

	OrderExportQueryService orderExportQueryService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");

		orderExportQueryService = context.getBean(OrderExportQueryService.class);

		Assert.assertNotNull(orderExportQueryService);
	}

	/**
	 * 订单导出日志查询
	 */
	@Test
	public void testQueryOrderExport() {
		final OrderExportQueryReqModel reqModel = new OrderExportQueryReqModel();
		reqModel.setCreateBy("chai");
		final Result<QueryResult<OrderExportRepModel>> queryResult = orderExportQueryService.queryExports(reqModel);

		final QueryResult<OrderExportRepModel> quereyData = queryResult.getData();

		System.out.println(quereyData.getCurrentPage());
		System.out.println(quereyData.getTotal());
		System.out.println(quereyData.getRecords());

	}

	/**
	 * 订单导出验证
	 */
	@Test
	public void testVerifyExport() {
		try {
			final OrderExportVerifyReqModel reqModel = new OrderExportVerifyReqModel();

			//reqModel.setId(97);
			final Result<OrderExportVerifyRepModel> result = orderExportQueryService.verifyExportLog(reqModel);
			final OrderExportVerifyRepModel data = result.getData();
			System.out.println(data.getCreateBy());
		} catch (final Throwable t) {
			logger.error(t.getMessage());
		}

	}

}

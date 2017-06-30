package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.model.SupplierOrdersReqModel;
import com.pzj.trade.order.model.SupplierOrdersRespModel;
import com.pzj.trade.order.service.OrderQueryService;

public class SupplierOrdersQueryTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath:/applicationContext-test.xml");
		System.out.println(context);
	}

	private OrderQueryService orderQueryService;

	@Before
	public void setUp() {
		orderQueryService = context.getBean(OrderQueryService.class);
	}

	/** 供应商ID */
	private long supplier_id = 2216619741563743L/*2216619741563734L*//*2216619741563913L*/;

	/**
	 * 根据订单号进行精准查询.
	 */
	@Test
	public void testByOrderID() {
		SupplierOrdersReqModel reqModel = new SupplierOrdersReqModel();
		reqModel.setSupplier_id(supplier_id);
		//reqModel.setOrder_id("MF1859687388");
		reqModel.setCurrentPage(1);
		Result<SupplierOrdersRespModel> result = orderQueryService.queryOrdersBySupplier(reqModel, null);
		Assert.assertNotNull(result);

		SupplierOrdersRespModel respModel = result.getData();
		Assert.assertNotNull(respModel);
		System.out.println(JSONConverter.toJson(result));
	}

	/**
	 * 根据订单号进行精准查询.
	 */
	@Test
	public void testQueryOrdersBySupplier() {
		SupplierOrdersReqModel reqModel = new SupplierOrdersReqModel();
		reqModel.setSupplier_id(0);
		/*reqModel.setOrder_id("MF1859687388");*/
		reqModel.setOrder_status(0);
		reqModel.setCurrentPage(1);
		reqModel.setPageSize(10);

		Result<SupplierOrdersRespModel> result = orderQueryService.queryOrdersBySupplier(reqModel, null);
		Assert.assertNotNull(result);

		SupplierOrdersRespModel respModel = result.getData();
		Assert.assertNotNull(respModel);
		System.out.println(JSONConverter.toJson(result));
	}

}

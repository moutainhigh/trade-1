/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.trade.export.model.ExportRequestModel;
import com.pzj.trade.export.service.OrderExportService;
import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.vo.PlatformOrderListVO;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExportServiceTest.java, v 0.1 2017年2月8日 下午6:07:53 Administrator Exp $
 */
public class OrderExportTest {
	ApplicationContext context;

	OrderExportService orderExportService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		orderExportService = context.getBean(OrderExportService.class);

		Assert.assertNotNull(orderExportService);
	}

	@Test
	public void testOrderExport() {
		ExportRequestModel exportRequestModel = new ExportRequestModel();
		exportRequestModel.setOperator("chai");
		exportRequestModel.setPage_name("platform1");

		PlatformOrderListVO orderVO = new PlatformOrderListVO();
		orderVO.setOrder_id("1030517042010007");
		exportRequestModel.setSupplierReqModel(orderVO);
		orderExportService.export(exportRequestModel, null);

	}

	@Test
	public void testSaaSResellerOrderExport() {
		ExportRequestModel exportRequestModel = new ExportRequestModel();
		exportRequestModel.setOperator("chai");
		exportRequestModel.setPage_name("chai2");

		exportRequestModel.setType(2);
		exportRequestModel.setOperator("2216619741563734");

		ResellerOrdersReqModel resellerReqModel = new ResellerOrdersReqModel();
		resellerReqModel.setResellerId(2216619741563734L);
		//resellerReqModel.setOrderStatus(40);

		exportRequestModel.setResellerReqModel(resellerReqModel);
		orderExportService.exportForSaaS(exportRequestModel, null);

	}

	@Test
	public void testSaaSSupplierOrderExport() {
		ExportRequestModel exportRequestModel = new ExportRequestModel();
		exportRequestModel.setOperator("chai");
		exportRequestModel.setPage_name("chai2");

		exportRequestModel.setType(1);
		exportRequestModel.setOperator("3905010602082305");

		PlatformOrderListVO supplierReqModel = new PlatformOrderListVO();
		supplierReqModel.setSupplier_id(3905010602082305L);
		exportRequestModel.setSupplierReqModel(supplierReqModel);
		orderExportService.exportForSaaS(exportRequestModel, null);

	}
}

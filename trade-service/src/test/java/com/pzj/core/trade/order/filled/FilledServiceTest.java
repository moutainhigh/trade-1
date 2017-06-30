/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.filled;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.OrderFillModel;
import com.pzj.trade.order.service.FilledService;

/**
 * 
 * @author Administrator
 * @version $Id: FilledServiceTest.java, v 0.1 2017年2月20日 上午11:08:54 Administrator Exp $
 */
public class FilledServiceTest {

	static ApplicationContext context;
	private FilledService filledService;

	@BeforeClass
	public static void setupClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		filledService = (FilledService) context.getBean("filledService");
	}

	@Test
	public void updateFilledTest() {
		OrderFillModel order = new OrderFillModel();
		List<FilledModel> filleds = new ArrayList<FilledModel>();

		FilledModel f1 = new FilledModel();
		f1.setGroup("contact");
		f1.setAttr_key("contact_name1");
		f1.setAttr_value("喔喔1");
		filleds.add(f1);

		//		FilledModel f2 = new FilledModel();
		//		f2.setGroup("contact");
		//		f2.setAttr_key("contact_mobile");
		//		f2.setAttr_value("18678869704");
		//		filleds.add(f2);

		order.setOrder_id("MF1547394512");
		order.setFilleds(filleds);
		Result<Boolean> result = filledService.updateFilledModel(order, null);
		System.out.println(JSONConverter.toJson(result));
	}

}

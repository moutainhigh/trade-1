/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.voucher.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.entity.VoucherResponseModel;
import com.pzj.voucher.service.VoucherService;

/**
 * 
 * @author Administrator
 * @version $Id: FilledServiceTest.java, v 0.1 2017年2月20日 上午11:08:54 Administrator Exp $
 */
public class VoucherServiceTest {

	static ApplicationContext context;
	private VoucherService voucherService;

	@BeforeClass
	public static void setupClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		voucherService = (VoucherService) context.getBean("voucherService");
	}

	@Test
	public void queryBasicVoucherTest() {
		final Long voucherId = 1474277066L;
		final ExecuteResult<VoucherResponseModel> result = voucherService.queryVoucherBasicById(voucherId);
		System.out.println(JSONConverter.toJson(result));
	}

}

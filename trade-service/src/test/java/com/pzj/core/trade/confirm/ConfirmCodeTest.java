package com.pzj.core.trade.confirm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.confirm.model.ConfirmCodeModel;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.confirm.model.MfCodeModel;
import com.pzj.trade.confirm.response.MfcodeResult;
import com.pzj.trade.confirm.service.ConfirmCodeService;

public class ConfirmCodeTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
	}

	private ConfirmCodeService confirmCodeService;

	@Before
	public void setUp() {
		confirmCodeService = context.getBean(ConfirmCodeService.class);
	}

	@Test
	public void testConfirm() {
		final ConfirmModel confirmModel = new ConfirmModel();
		confirmModel.setVoucherId(1476150884392L);

		final MfCodeModel codeModel = new MfCodeModel();
		codeModel.setCodeId(6486L);
		final Result<MfcodeResult> result = confirmCodeService.getMfcode(codeModel, null);

		Assert.assertNotNull(result);
		System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
	}

	@Test
	public void verifyTest() {

		final ConfirmCodeModel codeModel = new ConfirmCodeModel();
		codeModel.setCode("220443");

		codeModel.setSupplierId(4054590253629441L);

		confirmCodeService.verify(codeModel, (ServiceContext) null);
	}

}

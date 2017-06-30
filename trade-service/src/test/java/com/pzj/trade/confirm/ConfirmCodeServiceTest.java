package com.pzj.trade.confirm;

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
import com.pzj.trade.confirm.model.MfCodeModel;
import com.pzj.trade.confirm.response.MfcodeResult;
import com.pzj.trade.confirm.service.ConfirmCodeService;

public class ConfirmCodeServiceTest {

	static ApplicationContext context;

	private ConfirmCodeService confirmCodeService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		confirmCodeService = context.getBean(ConfirmCodeService.class);
	}

	@Test
	public void testValidate() {
		ConfirmCodeModel codeModel = new ConfirmCodeModel();
		codeModel.setCode("326616");
		codeModel.setSupplierId(2216619741564215L);
		Result<String> result = confirmCodeService.verify(codeModel, null);
		Assert.assertNotNull(result);
		System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
	}

	@Test
	public void testGetMfcode() {
		MfCodeModel codeModel = new MfCodeModel();
		codeModel.setCodeId(324);
		Result<MfcodeResult> result = confirmCodeService.getMfcode(codeModel, ServiceContext.getServiceContext());
		Assert.assertNotNull(result);
		System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
	}
}

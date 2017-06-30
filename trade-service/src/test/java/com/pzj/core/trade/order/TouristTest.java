package com.pzj.core.trade.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.model.TouristEditInModel;
import com.pzj.trade.order.service.FilledService;

/**
 * 游客信息接口测试.
 * @author YRJ
 *
 */
public class TouristTest {

	ApplicationContext context;

	FilledService filledService;

	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		filledService = context.getBean(FilledService.class);
		Assert.assertNotNull(filledService);
	}

	@Test
	public void testEditTourist() {
		final TouristEditInModel inModel = new TouristEditInModel();
		inModel.setOrderId("20011722410028");
		inModel.setTouristId(8);
		inModel.setName("柴会见");
		inModel.setMobile("13522330020");
		inModel.setCardId("130633198400994934");

		final Result<Boolean> result = filledService.updateTourist(inModel);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.getData());
	}

	@Test
	public void updateTouristTest() {
		final TouristEditInModel inModel = new TouristEditInModel();
		inModel.setTouristId(389);
		inModel.setCardId("220702198610178956");
		inModel.setMobile("15010049709");
		inModel.setName("GLG");
		inModel.setOrderId("1073417041710000");
		final Result<Boolean> result = filledService.updateTourist(inModel);
		System.out.println(JSONConverter.toJson(result));
	}
}

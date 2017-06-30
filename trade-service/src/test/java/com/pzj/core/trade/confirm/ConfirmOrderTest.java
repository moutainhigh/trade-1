package com.pzj.core.trade.confirm;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.confirm.service.ConfirmService;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.model.DeliveryWay;

public class ConfirmOrderTest {

	static ApplicationContext context = null;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
	}

	private ConfirmService confirmService;

	@Before
	public void setUp() {
		confirmService = context.getBean(ConfirmService.class);
	}

	@Test
	public void testConfirmSpecialty() {
		ConfirmModel confirmModel = new ConfirmModel();
		confirmModel.setVoucherId(1473308968168L);
		confirmModel.setTicketPoint(-999);
		DeliveryDetailModel deliveryDetailModel = new DeliveryDetailModel();
		confirmModel.setDeliveryDetail(deliveryDetailModel);
		deliveryDetailModel.setDeliveryWay(DeliveryWay.EXPRESS_SERVICE.getKey());
		deliveryDetailModel.setOrderID("MF000000000");
		deliveryDetailModel.setExpressCompany("顺丰快递");
		deliveryDetailModel.setExpressNO("SF1010100");
		try {
			confirmService.confirm(confirmModel, ServiceContext.getServiceContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testConfirm() {
		ConfirmModel confirmModel = new ConfirmModel();
		confirmModel.setVoucherId(1482899802462L);
		confirmModel.setTicketPoint(-999);
		try {
			confirmService.confirm(confirmModel, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

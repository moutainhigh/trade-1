/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.confirm.model.BatchConfirmReqModel;
import com.pzj.trade.confirm.model.ConfirmFailValue;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.confirm.service.ConfirmService;
import com.pzj.voucher.entity.VoucherEntity;

/**
 *
 * @author DongChunfu
 * @version $Id: ConfirmTest.java, v 0.1 2017年3月2日 下午5:28:45 DongChunfu Exp $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class ConfirmTest {

	@Autowired
	private ConfirmService confirmService;

	@Test
	// 正常核销
	public void confirmTest() {
		final ConfirmModel confirmModel = new ConfirmModel();
		confirmModel.setVoucherId(1464864171257L);
		confirmModel.setActtingTicket(false);
		confirmModel.setTicketPoint(-999);
		confirmModel.setType(2);
		/*final DeliveryDetailModel deliveryDetail = new DeliveryDetailModel();
		deliveryDetail.setDeliveryWay(1);
		deliveryDetail.setOrderID("MFTEST12345");
		confirmModel.setDeliveryDetail(deliveryDetail);*/
		final Result<VoucherEntity> confirmResult = confirmService.confirm(confirmModel, (ServiceContext) null);
		System.out.println(confirmResult.getErrorCode());
		System.out.println(confirmResult.getErrorMsg());
	}

	@Test
	// 批量核销
	public void batchConfirmTest() {
		final BatchConfirmReqModel reqModel = new BatchConfirmReqModel();
		final List<String> sellOrderIds = reqModel.getSellOrderIds();
		sellOrderIds.add("2064917061200111");

		final Result<ArrayList<ConfirmFailValue>> batchConfirmResult = confirmService.batchConfirm(reqModel,
				(ServiceContext) null);

		if (!batchConfirmResult.isOk()) {
			final ArrayList<ConfirmFailValue> confirmFailValues = batchConfirmResult.getData();
			if (null != confirmFailValues) {
				for (final ConfirmFailValue confirmFailValue : confirmFailValues) {
					System.out.println(confirmFailValue);
				}
			} else {
				System.out.println(batchConfirmResult.getErrorCode());
				System.out.println(batchConfirmResult.getErrorMsg());
			}
		} else {
			System.out.println(batchConfirmResult.getErrorCode());
			System.out.println(batchConfirmResult.getErrorMsg());
		}

	}

}

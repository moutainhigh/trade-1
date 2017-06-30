package com.pzj.core.trade.refund;

import org.junit.Assert;
import org.junit.Test;

import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.model.RefundReasonReqModel;

public class RefundApplyReqModelTest {

	@Test
	public void testRefundApplyReqModel() {
		RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setOrderId("MF董春福");
		reqModel.setInitiatorId(123456L);
		reqModel.setInitParty(1);
		reqModel.setRefundType(1);
		reqModel.setRefundMerches(null);

		RefundReasonReqModel reason = new RefundReasonReqModel();
		reason.setReason("测试强制退款申请原因信息");
		reason.setPics(new String[] { "/images/refund/MF董春福.gif" });
		reqModel.setReason(reason);

		System.out.println(reqModel);
		Assert.assertNotNull("此处永远不会执行到", reqModel);
	}
}

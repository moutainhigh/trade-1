package com.pzj.core.trade.refund;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.vo.RefundMerchandiseVO;
import com.pzj.trade.refund.model.RefundApplyReqModel;
import com.pzj.trade.refund.model.RefundReasonReqModel;
import com.pzj.trade.refund.service.RefundApplyService;

public class RefundApplyNewTest {

	private static ApplicationContext context;

	private static RefundApplyService refundApplyService;

	@BeforeClass
	public static void setUpClass() {
		context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
	}

	@Before
	public void setUp() {
		refundApplyService = context.getBean(RefundApplyService.class);
	}

	/**
	 * （普通退款）
	 * 整单退款测试
	 */
	@Test
	public void refundTotalOrder() {
		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setInitiatorId(3180074621206529L);
		reqModel.setOrderId("1008917051100033");
		reqModel.setInitParty(RefundInitPartyEnum.GENERAL.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.GENERAL.getType());
		RefundReasonReqModel reason = new RefundReasonReqModel();
		reason.setReason("测试整单退款");
		reqModel.setReason(reason);
		Result<Boolean> result = refundApplyService.refundApply(reqModel, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void refundPartyOrder() {
		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setInitiatorId(3180074621206529L);
		reqModel.setOrderId("1072217050800106");
		reqModel.setInitParty(RefundInitPartyEnum.GENERAL.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.GENERAL.getType());
		RefundReasonReqModel reason = new RefundReasonReqModel();
		reason.setReason("测试部分退款");
		reqModel.setReason(reason);
		reqModel.setRefundMerches(new ArrayList<RefundMerchandiseVO>());

		//退款的商品信息
		RefundMerchandiseVO vo = new RefundMerchandiseVO();
		vo.setMerchId("861471972672294912");
		vo.setRefundNum(1);
		reqModel.getRefundMerches().add(vo);
		Result<Boolean> result = refundApplyService.refundApply(reqModel, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void refundForcePartOrder() {
		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setInitiatorId(3908660833484801L);
		reqModel.setOrderId("1092117042700024");
		reqModel.setInitParty(RefundInitPartyEnum.SUPPORT.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.FORCE.getType());
		RefundReasonReqModel reason = new RefundReasonReqModel();
		reason.setReason("测试强制退款，但份数退款");
		reqModel.setReason(reason);
		reqModel.setRefundMerches(new ArrayList<RefundMerchandiseVO>());

		//退款的商品信息
		RefundMerchandiseVO vo = new RefundMerchandiseVO();
		vo.setMerchId("857531742259167233");
		vo.setRefundNum(1);
		reqModel.getRefundMerches().add(vo);
		Result<Boolean> result = refundApplyService.refundApply(reqModel, null);
		System.out.println(JSONConverter.toJson(result));
	}

	@Test
	public void refundForcetotalOrder() {
		final RefundApplyReqModel reqModel = new RefundApplyReqModel();
		reqModel.setInitiatorId(3908660833484801L);
		reqModel.setOrderId("1092117050200106");
		reqModel.setInitParty(RefundInitPartyEnum.SUPPORT.getParty());
		reqModel.setRefundType(RefundApplyTypeEnum.FORCE.getType());
		RefundReasonReqModel reason = new RefundReasonReqModel();
		reason.setReason("测试强制退款，全部退款");
		reqModel.setReason(reason);
		Result<Boolean> result = refundApplyService.refundApply(reqModel, null);
		System.out.println(JSONConverter.toJson(result));
	}
}

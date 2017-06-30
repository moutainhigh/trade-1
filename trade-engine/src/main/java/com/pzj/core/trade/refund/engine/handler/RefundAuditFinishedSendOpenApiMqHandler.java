package com.pzj.core.trade.refund.engine.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.mq.OpenApiMqEventEnum;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 退款审核完毕发送openApi消息
 * @author DongChunfu
 */
@Component(value = "refundAuditFinishedSendOpenApiMqHandler")
public class RefundAuditFinishedSendOpenApiMqHandler {

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	public void sentHandler(final String refundId, final String saleOrderId, final Boolean isPass, final String msg) {
		//		final FreezeFlowEntity freezeFlow = freezeFlowWriteMapper.getFreezingFlowBySignIdForRefund(refundId);
		final Map<String, String> context = new HashMap<String, String>();
		context.put("orderId", saleOrderId);
		context.put("event", OpenApiMqEventEnum.REFUND_AUDIT.getEvent());
		context.put("success", isPass.toString());
		context.put("refundMoney", String.valueOf(getRefundMoney(refundId, saleOrderId)));
		context.put("msg", Check.NuNString(msg) ? "" : msg);
		final String mqContent = JSONConverter.toJson(context);

		mqUtil.send(MQTagsEnum.trade_to_openapi.getValue(), mqContent);
	}

	private final double getRefundMoney(final String refundId, final String saleOrderId) {
		List<RefundFlowEntity> refundflows = merchRefundFlowWriteMapper.getOrderMerchRefund(saleOrderId, refundId);
		double refundMoney = 0;
		for (RefundFlowEntity flow : refundflows) {
			refundMoney += flow.getRefund_price() * flow.getRefund_num();
		}
		return MoneyUtils.getMoenyNumber(refundMoney);
	}
}

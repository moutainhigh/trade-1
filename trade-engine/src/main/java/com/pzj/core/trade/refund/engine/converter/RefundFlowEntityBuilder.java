package com.pzj.core.trade.refund.engine.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.model.MerchCalculateModel;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.model.RefundMerchStrategyModel;

@Component
public class RefundFlowEntityBuilder {

	public List<RefundFlowEntity> generateRefundFlowEntity(final String refundId,
			final List<RefundApplyMerchModel> refundMerches,RefundApplyEntity refundApply, final List<RefundMerchStrategyModel> merchStrategies) {

		List<RefundFlowEntity> refundflows = new ArrayList<RefundFlowEntity>();
		Map<String, RefundApplyMerchModel> merchRefundCache = getMerchRefundCache(refundMerches);
		for (RefundMerchStrategyModel merchStragety : merchStrategies) {
			RefundFlowEntity refundflow = convertRefundFlowEntity(refundId, merchStragety,
					merchRefundCache.get(merchStragety.getMerchId()));
			refundflow.setCreate_time(refundApply.getCreateTime());
			refundflows.add(refundflow);
		}
		return refundflows;
	}

	private Map<String, RefundApplyMerchModel> getMerchRefundCache(final List<RefundApplyMerchModel> refundMerches) {
		Map<String, RefundApplyMerchModel> merchRefundCache = new HashMap<String, RefundApplyMerchModel>();
		for (RefundApplyMerchModel merch : refundMerches) {
			merchRefundCache.put(merch.getMerchId(), merch);
		}
		return merchRefundCache;
	}

	private RefundFlowEntity convertRefundFlowEntity(String refundId, RefundMerchStrategyModel merchStrategy,
			RefundApplyMerchModel refundMerch) {
		MerchCalculateModel merchCalculate = refundMerch.getMerchCalculateModel();
		RefundFlowEntity refundflow = new RefundFlowEntity();
		refundflow.setOrder_id(merchStrategy.getOrderId());
		refundflow.setMerch_id(merchStrategy.getMerchId());
		refundflow.setRefund_id(refundId);
		refundflow.setApply_merch_status(refundMerch.getMerchState());
		refundflow.setRefund_num(refundMerch.getApplyNum());
		refundflow.setRefund_rule_type(merchCalculate.getRefundRuleType());
		refundflow.setRefund_price(merchCalculate.refundPrice(merchStrategy.getPrice()));
		return refundflow;
	}
}

package com.pzj.core.trade.refund.engine.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.OrderRefundModel;
import com.pzj.trade.order.model.RefundMerchStrategyModel;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component
public class RefundOrderUpdateModelBuilder {

	@Autowired
	private SassAccountRefundModelConvert sassAccountRefundModelConvert;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	public List<OrderRefundModel> generateRefundOrderModels(final List<RefundApplyMerchModel> refundMerches,
			final List<RefundMerchStrategyModel> merchStrategies, final int orderStatus, final int isForce) {
		List<OrderRefundModel> models = new ArrayList<OrderRefundModel>();
		final Map<String, List<RefundMerchStrategyModel>> refundOrderCache = sassAccountRefundModelConvert
				.getRefundOrderCache(merchStrategies);
		final Map<String, RefundApplyMerchModel> refundMerchApplyCache = sassAccountRefundModelConvert
				.getRefundApplyCache(refundMerches);
		Iterator<Entry<String, List<RefundMerchStrategyModel>>> iterator = refundOrderCache.entrySet().iterator();
		for (; iterator.hasNext();) {
			Entry<String, List<RefundMerchStrategyModel>> entry = iterator.next();
			String orderId = entry.getKey();
			List<RefundMerchStrategyModel> merchStrategy = entry.getValue();
			models.add(convertOrderRefundModel(orderId, merchStrategy, refundMerchApplyCache, orderStatus, isForce));
		}
		return models;
	}

	public OrderRefundModel convertOrderRefundModel(String orderId, List<RefundMerchStrategyModel> merchStrategies,
			Map<String, RefundApplyMerchModel> refundMerchApplyCache, final int orderStatus, final int isForce) {
		OrderRefundModel model = new OrderRefundModel();
		model.setOrderId(orderId);
		OrderEntity order = orderWriteMapper.getOrderEntityNotLock(orderId);
		int checkNum = order.getChecked_num();
		int refundNum = order.getRefund_num();
		double refundAmount = order.getRefund_amount();
		for (RefundMerchStrategyModel merchStrategy : merchStrategies) {
			RefundApplyMerchModel applyMerch = refundMerchApplyCache.get(merchStrategy.getMerchId());
			if (isForce == RefundApplyTypeEnum.FORCE.getType()
					&& (applyMerch.getMerchState() == MerchStateEnum.CONSUMED.getState() || applyMerch.getMerchState() == MerchStateEnum.FINISHED
							.getState())) {
				checkNum -= applyMerch.getApplyNum();
			}
			refundNum += applyMerch.getApplyNum();
			refundAmount += applyMerch.getMerchCalculateModel().refundPrice(merchStrategy.getPrice())
					* applyMerch.getApplyNum();
		}
		model.setCheckNum(checkNum);
		model.setRefundNum(refundNum);
		model.setRefundAmount(refundAmount);
		model.setOrderStatus(order.getOrder_status());
		return model;
	}
}

package com.pzj.core.trade.saas.refund.engine.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.OrderRefundModel;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

@Component
public class SaasRefundRefusedHandler {
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Autowired
	private SaasAccountRefundCancelHandler sassAccountRefundCancelHandler;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED)
	public void doRefusedHandler(final RefundApplyEntity refundApply, final OrderEntity saleOrder) {
		//获取主订单商品的退款申请流水
		List<RefundFlowEntity> refundFlow = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundApply.getRefundId());
		final Map<String, List<RefundFlowEntity>> refundFlowCache = convertOrderRefundFlow(refundFlow);
		//回滚商品对应的数量,金额信息
		updateMerchRollback(refundFlowCache.get(saleOrder.getOrder_id()), saleOrder.getOrder_id(), refundApply.getRefundId());
		updateOrderRollback(refundFlowCache);

		final FreezeFlowEntity freezeFlow = freezeFlowWriteMapper.getFreezingFlowBySignIdForRefund(refundApply.getRefundId());
		//判断是否存在退款冻结信息
		if (!Check.NuNObject(freezeFlow)) {
			freezeFlowWriteMapper.updateFreezeFlowStatus(saleOrder.getOrder_id(), refundApply.getRefundId(),
					PayFlowStateEnum.Payfailure.getKey());
			sassAccountRefundCancelHandler.cancelApplyAccountFrozen(refundApply.getRefundId());
		}
	}

	private void updateMerchRollback(final List<RefundFlowEntity> saleRefundFlows, final String saleOrderId,
			final String refundId) {
		List<MerchRefundModel> models = new ArrayList<MerchRefundModel>();
		for (RefundFlowEntity flow : saleRefundFlows) {
			MerchRefundModel model = new MerchRefundModel();
			int applyNum = flow.getRefund_num();
			model.setMerchId(flow.getMerch_id());
			MerchEntity merch = merchWriteMapper.getMerchByMerchId(flow.getMerch_id());
			model.setRefundNum(merch.getRefund_num() - applyNum);
			model.setRefundingNum(merch.getRefunding_num() - applyNum);
			model.setRefundAmount(merch.getRefund_amount() - applyNum * flow.getRefund_price());
			model.setMerchState(merch.getMerch_state());
			model.setCheckNum(merch.getCheck_num());
			if (model.getRefundingNum() == 0) {
				model.setIsRefunding(RefundingEnum.NOTREFUNDING.getValue());
			}
			if (model.getMerchState() == MerchStateEnum.FINISHED.getState()
					|| model.getMerchState() == MerchStateEnum.CONSUMED.getState()) {
				model.setCheckNum(merch.getCheck_num() + applyNum);
			}
			models.add(model);
		}
		merchWriteMapper.updateMerchesOfRefund(models);
	}

	private void updateOrderRollback(final Map<String, List<RefundFlowEntity>> refundFlowCache) {
		List<OrderRefundModel> models = new ArrayList<OrderRefundModel>();
		Iterator<Entry<String, List<RefundFlowEntity>>> iterator = refundFlowCache.entrySet().iterator();
		for (; iterator.hasNext();) {
			Entry<String, List<RefundFlowEntity>> entry = iterator.next();
			final String orderId = entry.getKey();
			OrderEntity order = orderWriteMapper.getOrderEntityNotLock(orderId);
			final List<RefundFlowEntity> refundFlows = entry.getValue();
			OrderRefundModel model = new OrderRefundModel();
			model.setOrderId(orderId);
			model.setOrderStatus(order.getOrder_status());
			int refundNum = order.getRefund_num();
			int checkNum = order.getChecked_num();
			double refudnAmount = order.getRefund_amount();
			for (RefundFlowEntity flow : refundFlows) {
				refundNum -= flow.getRefund_num();
				refudnAmount -= flow.getRefund_num() * flow.getRefund_price();
				if (flow.getApply_merch_status() == MerchStateEnum.CONSUMED.getState()
						|| flow.getApply_merch_status() == MerchStateEnum.FINISHED.getState()) {
					checkNum += flow.getRefund_num();
				}
			}
			model.setRefundNum(refundNum);
			model.setRefundAmount(refudnAmount);
			model.setCheckNum(checkNum);
			models.add(model);
		}
		orderWriteMapper.updateOrderOfRefund(models);
	}

	private Map<String, List<RefundFlowEntity>> convertOrderRefundFlow(final List<RefundFlowEntity> refundFlows) {
		Map<String, List<RefundFlowEntity>> flowCache = new HashMap<String, List<RefundFlowEntity>>();
		for (RefundFlowEntity flow : refundFlows) {
			String orderId = flow.getOrder_id();
			if (!flowCache.containsKey(orderId)) {
				flowCache.put(orderId, new ArrayList<RefundFlowEntity>());
			}
			flowCache.get(orderId).add(flow);
		}
		return flowCache;
	}
}

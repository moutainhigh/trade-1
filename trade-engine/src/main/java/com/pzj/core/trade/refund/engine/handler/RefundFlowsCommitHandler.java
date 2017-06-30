/**
 *
 */
package com.pzj.core.trade.refund.engine.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.OrderRefundModel;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 退款流水处理器
 *
 * @author DongChunfu
 * @date 2016年12月3日
 */
@Component(value = "refundFlowsCommitHandler")
public class RefundFlowsCommitHandler {

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	public void commite(final List<RefundFlowEntity> refundFlows, final OrderEntity sellOrder, final int refundType) {

		final Map<String, Double> ordersRefundAmount = new HashMap<String, Double>();
		final Map<String, Integer> uncheckNumber = new HashMap<String, Integer>();
		final Map<String, Integer> checkNumber = new HashMap<String, Integer>();

		// 更新商品信息
		for (final RefundFlowEntity refundFlow : refundFlows) {
			final double refundMoney = updateMerchInfo(refundFlow, refundType, uncheckNumber, checkNumber);

			final String orderid = refundFlow.getOrder_id();
			if (Check.NuNObject(ordersRefundAmount.get(orderid))) {
				ordersRefundAmount.put(orderid, 0D);
			}
			final BigDecimal bd = new BigDecimal(ordersRefundAmount.get(orderid) + refundMoney);
			final double refundAmound = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ordersRefundAmount.put(orderid, refundAmound);

		}

		// 更新订单信息
		updateOrderInfo(ordersRefundAmount, uncheckNumber, checkNumber);

		// 插入退款流水
		merchRefundFlowWriteMapper.insertOrderMerchRefundFlow(refundFlows);
	}

	/**
	 * 更新退款商品信息
	 *
	 * @param refundFlow
	 *            退款流水
	 */
	private double updateMerchInfo(final RefundFlowEntity refundFlow, final int refundType,
			final Map<String, Integer> uncheckNumber, final Map<String, Integer> checkNumber) {

		final int refundNum = refundFlow.getRefund_num();
		final BigDecimal bd = new BigDecimal(refundFlow.getRefund_price() * refundNum);
		final double refundMoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		final MerchEntity merch = merchWriteMapper.getMerchByMerchId(refundFlow.getMerch_id());
		final String orderid = refundFlow.getOrder_id();

		if (Check.NuNObject(checkNumber.get(orderid))) {
			checkNumber.put(orderid, 0);
		}
		if (Check.NuNObject(uncheckNumber.get(orderid))) {
			uncheckNumber.put(orderid, 0);
		}

		if (merch.getMerch_state() == MerchStateEnum.CONSUMED.getState()
				|| merch.getMerch_state() == MerchStateEnum.FINISHED.getState()) {
			checkNumber.put(orderid, checkNumber.get(orderid) + refundNum);

		} else {
			uncheckNumber.put(orderid, uncheckNumber.get(orderid) + refundNum);
		}

		final MerchRefundModel refundModel = new MerchRefundModel();
		refundModel.setMerchId(refundFlow.getMerch_id());
		refundModel.setRefundNum(merch.getRefund_num() + refundNum);

		if (3 == refundType) {
			refundModel.setCheckNum(merch.getCheck_num() - refundFlow.getRefund_num());
		} else if (RefundApplyTypeEnum.FORCE.getType() == refundType) {
			refundModel.setCheckNum(0);
		} else {
			refundModel.setCheckNum(merch.getCheck_num());
		}

		refundModel.setRefundAmount(merch.getRefund_amount() + refundMoney);
		refundModel.setMerchState(merch.getMerch_state());
		refundModel.setIsRefunding(RefundingEnum.REFUNDING.getValue());
		merchWriteMapper.updateMerchOfRefund(refundModel);

		return refundMoney;
	}

	/**
	 * 更新订单信息
	 *
	 * @param ordersRefundAmount
	 *            订单退款金额
	 * @param ordersRefundNumber
	 *            订单退款数量
	 */
	private void updateOrderInfo(final Map<String, Double> ordersRefundAmount, final Map<String, Integer> uncheckNumber,
			final Map<String, Integer> checkNumber) {

		final ArrayList<String> orderIds = new ArrayList<String>(uncheckNumber.keySet());
		orderIds.addAll(new ArrayList<String>(checkNumber.keySet()));

		final List<OrderEntity> orderEntities = orderWriteMapper.getOrderListByIds(orderIds);
		final List<OrderRefundModel> orderModels = new ArrayList<OrderRefundModel>();
		for (final OrderEntity order : orderEntities) {

			OrderRefundModel model = new OrderRefundModel();
			final String orderid = order.getOrder_id();
			model.setOrderId(order.getOrder_id());

			int refundNum = 0;
			if (uncheckNumber.get(orderid) != 0) {
				refundNum = uncheckNumber.get(orderid);
				model.setCheckNum(order.getChecked_num());
			} else {
				refundNum = checkNumber.get(orderid);
				model.setCheckNum(order.getChecked_num() - refundNum);
			}

			final double refundAmount = ordersRefundAmount.get(orderid);
			final BigDecimal bd = new BigDecimal(order.getRefund_amount() + refundAmount);
			model.setRefundAmount(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			model.setRefundNum(order.getRefund_num() + refundNum);
			model.setOrderStatus(order.getOrder_status());
			orderModels.add(model);
		}

		orderWriteMapper.updateOrderOfRefund(orderModels);
	}
}

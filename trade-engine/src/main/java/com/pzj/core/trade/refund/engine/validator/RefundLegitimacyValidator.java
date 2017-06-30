package com.pzj.core.trade.refund.engine.validator;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.OrderNotExistException;
import com.pzj.core.trade.refund.engine.common.MerchStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundInitPartyEnum;
import com.pzj.core.trade.refund.engine.exception.MerchApplyNumException;
import com.pzj.core.trade.refund.engine.exception.MerchStateException;
import com.pzj.core.trade.refund.engine.exception.OrderPayStateException;
import com.pzj.core.trade.refund.engine.exception.RefundMerchStateInconformityException;
import com.pzj.core.trade.refund.engine.exception.RefundOperationIntervalException;
import com.pzj.core.trade.refund.engine.exception.RefundOrderException;
import com.pzj.core.trade.refund.engine.exception.TriggerPartyException;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;

@Component(value = "refundLegitimacyValidator")
public class RefundLegitimacyValidator {

	private static final Logger logger = LoggerFactory.getLogger(RefundLegitimacyValidator.class);

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	public int convert(final RefundMerchModel[] refundMerches, final int initParty, final OrderEntity sellOrder,
			final int isForce) {

		if (Check.NuNObject(sellOrder)) {
			logger.error("退款申请订单不存在.");
			throw new OrderNotExistException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "退款申请订单不存在");
		}

		isResellerOrder(sellOrder);// 退款定单是否为销售订单

		isOrderPaied(sellOrder);// 订单是否已支付

		// 判断是否为平台发起的退已消费的商品
		int refundType = isForce;
		for (final RefundMerchModel refundMerch : refundMerches) {

			if (isForce == RefundApplyTypeEnum.GENERAL.getType()) {
				if (refundMerch.getMerchState() == MerchStateEnum.CONSUMED.getState()) {
					// 判断所有商品的状态是否一致
					judgeMerchState(refundMerches);
					refundType = 3;
				}
			}

			if (isForce == RefundApplyTypeEnum.FORCE.getType()) {
				if (refundMerch.getIsRefunding() == 1) {
					throw new MerchStateException(RefundErrorCode.MERCH_STATE_UNREFUNDABLE_ERROR_CODE, "存在待退款的商品,无法执行强制退款.");
				}
			}

			operationInterval(refundMerch);// 退款操作间隔

			triggable(refundMerch, initParty, isForce);// 商品状态是否可以触发

			refundable(refundMerch, isForce);// 当前退款类型下，当前商品状态是否可退

			illegalApplyNum(refundMerch, isForce);// 申请退款的商品数量是否满足要求
		}

		return refundType;
	}

	private void judgeMerchState(final RefundMerchModel[] refundMerches) {
		for (final RefundMerchModel refundMerch : refundMerches) {
			if (refundMerch.getMerchState() != MerchStateEnum.CONSUMED.getState()) {
				throw new RefundMerchStateInconformityException(RefundErrorCode.REFUND_MERCH_STATE_INCOMFORMITY_ERROR_CODE,
						"平台发起的普通已消费退款,商品状态不一致");
			}
		}
	}

	/**
	 * 判断商品是否为已支付状态
	 *
	 * @param orderEntity
	 */
	private void isOrderPaied(final OrderEntity orderEntity) {
		final int orderStatus = orderEntity.getOrder_status();

		if (orderStatus == OrderStatusEnum.AlreadyPaid.getValue()) {
			return;
		}
		if (orderStatus == OrderStatusEnum.Finished.getValue()) {
			return;
		}

		throw new OrderPayStateException(RefundErrorCode.REFUND_ORDER_STATE_ERROR_CODE, "指定的订单状态存在问题,不可退款,订单ID:["
				+ orderEntity.getOrder_id() + "],订单状态：[" + orderStatus + "]");
	}

	/**
	 * 判断退款订单是否为销售订单
	 *
	 * @param orderEntity
	 */
	private void isResellerOrder(final OrderEntity orderEntity) {
		if (!orderEntity.getOrder_id().equals(orderEntity.getP_order_id())) {
			throw new RefundOrderException(RefundErrorCode.REFUND_IS_NOT_SELLER_ORDER_ERROR_CODE, "退款的订单必须是销售订单,orderId:["
					+ orderEntity.getOrder_id() + "]");
		}
	}

	/**
	 * 判断商品退款操作时间间隔是否大于1分钟
	 *
	 * @param merch
	 */
	private void operationInterval(final RefundMerchModel merch) {

		final Date createTime = merchRefundFlowWriteMapper.getLastRefundOrderMerch(merch.getMerchId());
		if (createTime != null) {
			if (System.currentTimeMillis() - createTime.getTime() < 1000 * TradeConstant.OPERATION_INTERVAL) {
				throw new RefundOperationIntervalException(RefundErrorCode.REFUND_OPERATION_INTERVAL_ERROR_CODE,
						"同一规格退款操作间隔须大于1分钟");
			}
		}
	}

	/**
	 * 判断商品在当前状态下是否可进行退款操作.
	 * <p>
	 * 商品处于不同状态下, 所允许的退款申请类型不同.
	 * </p>
	 *
	 * @param merch
	 */
	private void refundable(final RefundMerchModel merch, final int isForce) {
		final MerchStateEnum state = MerchStateEnum.getMerchState(merch.getMerchState());
		if (state.refundable(isForce)) {
			return;
		}

		final RefundApplyTypeEnum refundType = RefundApplyTypeEnum.getRefundApplyType(isForce);
		throw new MerchStateException(RefundErrorCode.MERCH_STATE_UNREFUNDABLE_ERROR_CODE, "商品[" + merch.getMerchId()
				+ "], 状态为: [" + state.getMsg() + "], 不允许执行: [" + refundType.getNote() + "]");
	}

	/**
	 * 判断商品在当前状态下是否可进行退款操作.
	 * <p>
	 * 商品处于不同状态下, 所允许的退款申请类型不同.
	 * </p>
	 *
	 * @param merch
	 */
	private void triggable(final RefundMerchModel merch, final int initParty, final int isForce) {
		final MerchStateEnum state = MerchStateEnum.getMerchState(merch.getMerchState());
		if (state.canTrigger(initParty, isForce)) {
			return;
		}

		final RefundInitPartyEnum refundInitPartyEnum = RefundInitPartyEnum.getRefundInitPartyEnum(initParty);
		throw new TriggerPartyException(RefundErrorCode.MERCH_STATE_REFUND_TRIGGE_ERROR_CODE, "商品[" + merch.getMerchId()
				+ "], 状态为: [" + state.getMsg() + "], 不允许[" + refundInitPartyEnum.getNote() + "]触发");
	}

	/**
	 * 判断申请退款的数量是否合法.
	 *
	 * @param merch
	 */
	private void illegalApplyNum(final RefundMerchModel refundMerch, final int isForce) {

		if (RefundApplyTypeEnum.FORCE.getType() == isForce) {// 强制退款

			final int available = refundMerch.forceRefundavailable();
			if (refundMerch.getApplyNum() != refundMerch.forceRefundavailable()) {
				throw new MerchApplyNumException(RefundErrorCode.REFUND_NUM_ERROR_CODE, "商品[" + refundMerch.getMerchId()
						+ "], 当前可退量: [" + available + "], 申退量: [" + refundMerch.getApplyNum() + "], 强制退款申请量不符合要求.");
			}
		} else {
			int available = refundMerch.generalRefundavailable();

			if (refundMerch.getMerchState() == MerchStateEnum.CONSUMED.getState()) {
				available = refundMerch.generalConsumedRefundavailable();
				if (available <= 0 || available < refundMerch.getApplyNum()) {
					throw new MerchApplyNumException(RefundErrorCode.REFUND_NUM_ERROR_CODE, "商品[" + refundMerch.getMerchId()
							+ "], 当前可退已消费商品量: [" + available + "], 申退量: [" + refundMerch.getApplyNum() + "], 退款申请量不符合要求.");
				}
			}

			if (available <= 0 || available < refundMerch.getApplyNum()) {
				throw new MerchApplyNumException(RefundErrorCode.REFUND_NUM_ERROR_CODE, "商品[" + refundMerch.getMerchId()
						+ "], 当前可退量: [" + available + "], 申退量: [" + refundMerch.getApplyNum() + "], 普通退款申请数量不符合要求.");
			}

		}
	}

}
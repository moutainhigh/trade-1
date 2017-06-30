package com.pzj.core.trade.refund.engine.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.exception.OrderNotExistException;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowStateEnum;
import com.pzj.core.trade.refund.engine.exception.RefundFlowIsNoneException;
import com.pzj.core.trade.refund.engine.exception.RefundSignIsNoneException;
import com.pzj.core.trade.refund.engine.validator.RefundCallbackParamValidator;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.mq.RefundMQMSgConverter;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.voucher.service.VoucherService;

@Component(value = "refundSuccessCallBackHandler")
public class RefundSuccessCallBackHandler {

	private static final Logger logger = LoggerFactory.getLogger(RefundSuccessCallBackHandler.class);

	@Resource(name = "refundCallbackParamValidator")
	private RefundCallbackParamValidator refundCallbackParamValidator;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private VoucherService voucherService;

	@Resource(name = "settlRefundCleanHandler")
	private SettlRefundCleanHandler settlRefundCleanHandler;

	@Resource(name = "refundMQMSgConverter")
	private RefundMQMSgConverter refundMQMSgConverter;

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 4)
	public Result<Boolean> callBack(final String orderId, final String refundId, final int refundType) {
		List<RefundFlowEntity> refundFlows = null;
		try {
			refundCallbackParamValidator.convert(orderId, refundId, refundType);

			final OrderEntity sellOrder = orderWriteMapper.getOrderEntityNotLock(orderId);
			if (Check.NuNObject(sellOrder)) {
				throw new OrderNotExistException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "销售订单不存在");
			}

			refundFlows = merchRefundFlowWriteMapper.getOrderMerchRefund(null, refundId);

			if (Check.NuNCollections(refundFlows)) {
				logger.error("refund flows is none ,refundId:{}.", refundId);
				throw new RefundFlowIsNoneException(RefundErrorCode.REFUND_FLOWS_IS_NONE_ERROR_CODE, "没有对应的退款申请信息,refundId:" + refundId);
			}
			// 退款申请状态,移至退款申请表中
			refundApplyWriteMapper.updateRefundApplyRelationStatue(refundId, RefundFlowStateEnum.SUCCESS.getState(), null);

			final FreezeFlowEntity freezeFlow = freezeFlowWriteMapper.getFreezingFlowBySignIdForRefund(refundId);
			if (!Check.NuNObject(freezeFlow)) {// 非直签
				if (freezeFlow.getReceive_type() != ReceiveTypeEnum.Refund.getValue() || freezeFlow.getFreeze_state() != PayFlowStateEnum.Paying.getKey()) {
					throw new RefundSignIsNoneException(RefundErrorCode.REFUND_SIGN_IS_NONE_ERROR_CODE, "退款申请唯一标识为空");
				}
			}

			settlRefundCleanHandler.handler(refundFlows, refundId, sellOrder, refundType, freezeFlow);

			freezeFlowWriteMapper.updateFreezeFlowStatus(orderId, refundId, PayFlowStateEnum.PaySuccess.getKey());

			finishRefundUpdateOrderAndMerchState(refundFlows, refundId, sellOrder);

			callVoucharLogout(orderId, refundId, refundFlows);

			//			if (refundApply.getIsForce() == RefundApplyTypeEnum.FORCE.getType()) {
			//				refundMQMSgConverter.refundLogMsg(refundFlows, true, RefundLogEventEnum.FORCE_REFUND_SUCCESS.getEvent(),
			//						null);
			//			}
			return new Result<Boolean>();
		} catch (final Throwable t) {
			//			if (refundApply != null) {
			//				if (refundApply.getIsForce() == RefundApplyTypeEnum.FORCE.getType()) {
			//					refundMQMSgConverter.refundLogMsg(refundFlows, Boolean.FALSE,
			//							RefundLogEventEnum.FORCE_REFUND_ERROR.getEvent(), t.getMessage());
			//				}
			//			}
			throw t;
		}
	}

	/**
	 * 调用vouchar注销服务接口
	 */
	private void callVoucharLogout(final String orderId, final String refundId, final List<RefundFlowEntity> refundFlows) {
		//		final List<VoucherNumberVO> voucharlist = new ArrayList<VoucherNumberVO>();
		//		//获取销售订单对应的退款流水
		//		refundFlows = RefundFlowEntity.filterFlows(refundFlows, RefundUserTypeEnum.resellerRefund.getKey());
		//		for (final RefundFlowEntity refundFlow : refundFlows) {
		//			final MerchEntity merch = merchWriteMapper.getMerchByMerchId(refundFlow.getMerch_id());
		//			final VoucherNumberVO vo = new VoucherNumberVO();
		//			vo.setNumber(refundFlow.getRefund_num());
		//			vo.setVoucherId(merch.getVoucher_id());
		//			vo.setProductId(merch.getProduct_id());
		//			voucharlist.add(vo);
		//		}
		//		if (logger.isInfoEnabled()) {
		//			logger.info("call refundVoucherBatch content:" + JSONConverter.toJson(voucharlist));
		//		}
		//		TODO YRJ现屏蔽
		//		final ExecuteResult<Boolean> result = voucherService.refundVoucherBatch(voucharlist);
		//
		//		if (logger.isInfoEnabled()) {
		//			logger.info("call refundVoucherBatch resultContent:" + JSONConverter.toJson(result));
		//		}
		//
		//		if (Check.NuNObject(result) || Check.NuNObject(result.getStateCode()) || result.getStateCode().intValue() != ExecuteResult.SUCCESS.intValue()) {
		//
		//			logger.error("refund errorContent:" + JSONConverter.toJson(result));
		//			logger.error("voucher注销失败,req param:" + JSONConverter.toJson(voucharlist) + ",orderId:" + orderId + ",refundId:" + refundId);
		//		}

	}

	private void finishRefundUpdateOrderAndMerchState(final List<RefundFlowEntity> refundflows, final String refundId, final OrderEntity sellOrder) {

		final Set<String> orderIds = new HashSet<String>();
		updateMerchState(sellOrder, refundflows, orderIds);

		// 修改订单相关的状态信息
		for (final String orderId : orderIds) {
			updateOrderState(sellOrder, orderId);
		}

	}

	private void updateOrderState(OrderEntity sellOrder, final String orderId) {

		if (!sellOrder.getOrder_id().equals(orderId)) {
			sellOrder = orderWriteMapper.getOrderEntityNotLock(orderId);
		}

		final int num = merchRefundFlowWriteMapper.getRefundAuditingMerchCount(orderId, RefundFlowStateEnum.REFUNDING.getState());
		if (num > 1) {
			return;
		}

		if (sellOrder.getTotal_num() == sellOrder.getRefund_num() + sellOrder.getChecked_num()) {

			if (sellOrder.getChecked_num() == 0) {
				final int orderStatus = OrderStatusEnum.Refunded.getValue();
				orderWriteMapper.updateOrderStatusByOrderId(sellOrder.getOrder_id(), orderStatus);
			}
		}
	}

	private void updateMerchState(final OrderEntity sellOrder, final List<RefundFlowEntity> refundflows, final Set<String> orderIds) {

		final List<String> refundOverMerchIds = new ArrayList<>();
		for (final RefundFlowEntity flow : refundflows) {
			// 获取当前订单,该规格退款中的申请条数
			final int num = merchRefundFlowWriteMapper.getRefundAuditingOfMerch(flow.getOrder_id(), flow.getMerch_id(),
					RefundFlowStateEnum.REFUNDING.getState());
			if (num > 1) {
				continue;
			}
			refundOverMerchIds.add(flow.getMerch_id());
			final MerchEntity merch = merchWriteMapper.getMerchByMerchId(flow.getMerch_id());

			// 更改商品状态
			if (merch.getTotal_num() == merch.getCheck_num() + merch.getRefund_num()) {

				if (merch.getCheck_num() == 0) {
					final Map<String, Object> params = new HashMap<String, Object>();
					params.put("merchId", merch.getMerch_id());
					params.put("transactionId", merch.getTransaction_id());
					params.put("status", MerchStateEnum.REFUNDED.getState());
					merchWriteMapper.updateOrderMerchStatus(params);
				}

				orderIds.add(flow.getOrder_id());

			}
		}

		merchWriteMapper.updateMerchToFinishiRefund(refundOverMerchIds);
	}

}

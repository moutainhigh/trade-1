package com.pzj.trade.engine.payment;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.refund.engine.TradeCleanEngine;
import com.pzj.core.trade.refund.engine.handler.RefundSuccessCallBackHandler;
import com.pzj.framework.context.Result;

/**
 * 退款服务引擎层实现.
 *
 * @author xuandonghui
 *
 */
@Service(value = "refundServiceEngine")
public class RefundServiceEngine {
	private final static Logger logger = LoggerFactory.getLogger(RefundServiceEngine.class);

	@Autowired
	private RefundSuccessCallBackHandler refundSuccessCallBackHandler;

	@Resource(name = "tradeCleanEngine")
	private TradeCleanEngine tradeCleanEngine;

	public Result<Boolean> refundPayMoneyCallback(final String orderId, final String refundId, final int refundType) {
		try {
			refundSuccessCallBackHandler.callBack(orderId, refundId, refundType);
//			tradeCleanEngine.clean(orderId, refundId, 2);
		} catch (final Throwable t) {
			logger.error("refund fail.", t);
		}
		return new Result<Boolean>(Boolean.TRUE);
	}

	//	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	//	@Deprecated
	//	public Result<Boolean> refundPayMoney(final long paymentAccountId, final String orderId, final String refundId,
	//			final FreezeFlowEntity freezeflow, final int refundType) {
	//
	//		if (Check.NuNObject(paymentAccountId)) {
	//			return new Result<Boolean>(4501, "付款方资金帐号为空");
	//		}
	//		if (Check.NuNObject(orderId)) {
	//			return new Result<Boolean>(4502, "退款订单号为空");
	//		}
	//		logger.info("refund orderId:" + orderId + ",paymentAccountId:" + paymentAccountId + ",refundId:" + refundId
	//				+ "banlanceRefundMoeny:" + freezeflow.getBalance_amount() + ",thirdRefundMoney:"
	//				+ freezeflow.getThird_amount());
	//		// 如果没有第三方支付
	//		if (freezeflow.getThird_amount() == 0) {
	//			// 直接调用退款的回调
	//			return refundPayMoneyCallback(orderId, refundId, refundType);
	//
	//		} else {
	//			final RefundApplyRequestModel reqParam = new RefundApplyRequestModel();
	//			reqParam.setOrderId(orderId);
	//			reqParam.setRefundId(refundId);
	//			reqParam.setRefundMoney(freezeflow.getThird_amount());
	//			reqParam.setRefundType(refundType);
	//			logger.info("call paymentRefundService.refundApply send:{}", JSONConverter.toJson(reqParam));
	//			final Result<Boolean> result = paymentRefundService.refundApply(reqParam);
	//			logger.info("request paymentRefundService.refundApply response:{}", JSONConverter.toJson(result));
	//			if (!result.isOk()) {
	//				throw new ServiceException(result.getErrorMsg());
	//			}
	//			return result;
	//		}
	//	}
	//
	//	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	//	@Deprecated
	//	public void finishRefund(final List<RefundFlowEntity> refundflows, final String refundId,
	//			final String rootOrderId) {
	//
	//		logger.info("refund finish refundlowlist:" + JSONConverter.toJson(refundflows));
	//		final OrderEntity rootOrder = orderWriteMapper.getOrderEntityNotLock(rootOrderId);
	//
	//		// 没有退款中的商品集合
	//		final List<String> refundOverMerchId = new ArrayList<String>();
	//		final Set<String> orderIds = new HashSet<String>();
	//
	//		final List<MerchCleanRelationEntity> merchcleanlist = new ArrayList<MerchCleanRelationEntity>();
	//
	//		for (final RefundFlowEntity flow : refundflows) {
	//			logger.info("=========================" + (JSONConverter.toJson(flow)));
	//
	//			final int num = merchRefundFlowWriteMapper.getRefundAuditingOfMerch(flow.getOrder_id(), flow.getMerch_id(),
	//					PayFlowStateEnum.Paying.getKey());
	//			logger.info("=========================数量: " + num);
	//			if (num > 1) {
	//				continue;
	//			}
	//			refundOverMerchId.add(flow.getMerch_id());
	//			final MerchEntity merch = merchWriterMapper.getMerchByMerchId(flow.getMerch_id());
	//			// 判断商品状态是否是需要清算
	//			if (merch.getTotal_num() == merch.getCheck_num() + merch.getRefund_num()) {
	//				final Map<String, Object> params = new HashMap<String, Object>();
	//				params.put("merchId", merch.getMerch_id());
	//				params.put("transactionId", merch.getTransaction_id());
	//				if (merch.getCheck_num() == 0) {
	//					params.put("status", MerchStateEnum.REFUNDED.getState());
	//				} else {
	//					params.put("status", MerchStateEnum.CONSUMED.getState());
	//				}
	//				// merchWriterMapper.updateOrderMerchStatus(params);
	//				orderIds.add(flow.getOrder_id());
	//				// 采购结算信息
	//				if (flow.getRefund_type() == RefundUserTypeEnum.supplierRefund.getKey()
	//						&& rootOrder.getTotal_amount() > rootOrder.getRefund_amount()) {
	//					final ConfirmMerchEntity cmerch = convertConfirmMerch(merch);
	//					final MerchCleanRelationEntity merchClean = merchCleanRelationEvent
	//							.generateMerchCleanRelation(cmerch);
	//					merchcleanlist.add(merchClean);
	//				} else if (rootOrder.getTotal_amount() == rootOrder.getRefund_amount()) {
	//					merchWriteMapper.updateCleanedByTransactionId(rootOrder.getTransaction_id());
	//				}
	//			}
	//		}
	//		// 修改订单相关的状态信息
	//		for (final String orderId : orderIds) {
	//			final OrderEntity order = orderWriteMapper.getOrderEntityNotLock(orderId);
	//			final int num = merchRefundFlowWriteMapper.getRefundAuditingMerchCount(orderId,
	//					PayFlowStateEnum.Paying.getKey());
	//			if (num > 1) {
	//				continue;
	//			}
	//			if (order.getTotal_num() == order.getRefund_num() + order.getChecked_num()) {
	//				int orderStatus = OrderStatusEnum.AlreadyPaid.getValue();
	//				if (order.getChecked_num() == 0) {
	//					orderStatus = OrderStatusEnum.Refunded.getValue();
	//				}
	//				orderWriteMapper.updateOrderStatusByOrderId(order.getOrder_id(), orderStatus);
	//			}
	//		}
	//		if (!Check.NuNCollections(merchcleanlist)) {
	//			merchCleanRelationWriteMapper.insertMerchCleanRelation(merchcleanlist);
	//		}
	//		if (!Check.NuNCollections(refundOverMerchId)) {
	//			merchWriterMapper.updateMerchToFinishiRefund(refundOverMerchId);
	//		}
	//		// 退款成功后通知ota系统
	//		sendRefundOtaSucess(rootOrderId);
	//	}
	//
	//	@Deprecated
	//	private ConfirmMerchEntity convertConfirmMerch(final MerchEntity merch) {
	//		final ConfirmMerchEntity cmerch = new ConfirmMerchEntity();
	//		BeanCopier.create(MerchEntity.class, ConfirmMerchEntity.class, false).copy(merch, cmerch, null);
	//		return cmerch;
	//	}
	//
	//	/**
	//	 * 向ota系统发送订单退款成功消息
	//	 *
	//	 * @param orderId
	//	 */
	//	@Deprecated
	//	private void sendRefundOtaSucess(final String orderId) {
	//		final String msg = DockOtaMQMSgConverter.getRefundOrderMsg(orderId, true);
	//		mqUtil.send(MQTagsEnum.otaRefundMerch.getValue(), msg);
	//	}
}

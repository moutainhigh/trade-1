package com.pzj.core.trade.refund.engine;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pzj.core.payment.model.RefundApplyRequestModel;
import com.pzj.core.payment.service.RefundService;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.exception.CallPaymentRefundException;
import com.pzj.core.trade.refund.engine.handler.RefundSuccessCallBackHandler;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 审核完毕或无需审核执行退款操作
 *
 * @author DongChunfu
 * @date 2016年12月15日
 */
@Component(value = "doRefundEngine")
@Scope("singleton")
public class DoRefundEngine {

	private static final Logger logger = LoggerFactory.getLogger(DoRefundEngine.class);

	@Resource(name = "refundSuccessCallBackHandler")
	private RefundSuccessCallBackHandler refundSuccessCallBackHandler;

	@Resource(name = "paymentRefundService")
	private RefundService paymentRefundService;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Resource(name = "tradeCleanEngine")
	private TradeCleanEngine tradeCleanEngine;

	public void refund(final String refundId) {

		final FreezeFlowEntity freezeFlow = freezeFlowWriteMapper.getFreezingFlowBySignIdForRefund(refundId);

		final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
		final String sellOrderId = merchRefundFlowWriteMapper.queryOrderIdByRefundId(refundId,
				RefundUserTypeEnum.resellerRefund.getKey());

		final OrderEntity sellOrder = orderWriteMapper.getOrderEntityNotLock(sellOrderId);
		final int isForce = refundApply.getIsForce();

		logger.info("冻结流水记录:" + JSONConverter.toJson(freezeFlow));

		if (!Check.NuNObject(freezeFlow) && freezeFlow.getThird_amount() > 0) {
			// 调用支付系统,进行退款申请操作
			callRefundMoney(buildRefundApplyReqParam(sellOrder.getOrder_id(), refundId, freezeFlow.getThird_amount(), isForce));
		}
		refundSuccessCallBackHandler.callBack(sellOrder.getOrder_id(), refundId, isForce);
	}

	/**
	 * 构建退款申请请求参数
	 *
	 * @param orderId
	 *            订单ID
	 * @param refundId
	 *            退款ID
	 * @param thirdPayAmout
	 *            三方支付金额
	 * @param refundType
	 *            退款类型
	 * @return
	 */
	private RefundApplyRequestModel buildRefundApplyReqParam(final String orderId, final String refundId,
			final double thirdPayAmout, final int isForce) {
		final RefundApplyRequestModel reqParam = new RefundApplyRequestModel();
		reqParam.setOrderId(orderId);
		reqParam.setRefundId(refundId);
		reqParam.setRefundMoney(thirdPayAmout);
		if (isForce == RefundApplyTypeEnum.FORCE.getType()) {
			reqParam.setRefundType(2);
		} else {
			reqParam.setRefundType(0);
		}
		return reqParam;
	}

	/**
	 * 调用支付系统退款申请服务
	 *
	 * @param reqParam
	 */
	private void callRefundMoney(final RefundApplyRequestModel reqParam) {

		if (logger.isInfoEnabled()) {
			logger.info("支付系统退款申请,reqModel:{}", JSONConverter.toJson(reqParam));
		}
		Result<Boolean> result = null;
		try {
			result = paymentRefundService.refundApply(reqParam);
		} catch (final Throwable t) {
			logger.error("支付系统退款申请失败,reqModel:{}.", JSONConverter.toJson(reqParam));
			throw new CallPaymentRefundException(RefundErrorCode.PAYMENT_REFUND_ERROR_CODE, "支付系统退款申请失败");
		}

		logger.info("支付系统退款申请结果, reqModel:{},reqResult:{}.", JSONConverter.toJson(reqParam), JSONConverter.toJson(result));

		if (!result.isOk()) {
			throw new CallPaymentRefundException(RefundErrorCode.PAYMENT_REFUND_ERROR_CODE, "支付系统退款申请失败");
		}
	}
}

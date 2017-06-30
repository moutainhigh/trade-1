package com.pzj.core.trade.payment.engine;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.payment.model.RefundApplyRequestModel;
import com.pzj.core.payment.service.RefundService;
import com.pzj.core.product.service.SeatRecordService;
import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.payment.engine.exception.PayErrorCode;
import com.pzj.core.trade.payment.engine.exception.PaymentCancelException;
import com.pzj.core.trade.payment.engine.handler.PaymentCancelHandler;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

@Component("paymentCancelEngine")
public class PaymentCancelEngine {
	private final static Logger logger = LoggerFactory.getLogger(PaymentCancelEngine.class);

	@Autowired
	private OrderReadMapper orderReadMapper;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Autowired
	private PaymentCancelHandler paymentCancelHandler;

	@Autowired
	private RefundService paymentRefundService;

	@Autowired
	private AccountBussinessService accountBussinessService;

	@Autowired
	private SeatRecordService seatRecordService;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public void doEngine(String transactionId) {
		logger.info("主订单ID:{}发起支付取消", transactionId);
		OrderEntity saleOrder = orderWriteMapper.getOrderEntityForUpdate(transactionId);
		if (Check.NuNObject(saleOrder)) {
			logger.error("获取主订单信息为空,主订单ID:{}", transactionId);
			throw new PaymentCancelException(PayErrorCode.PAYMENT_CANCEL_ERROR_CODE, "订单[" + transactionId + "]信息为空");
		}
		if (saleOrder.getOrder_status() != OrderStatusEnum.Unpaid.getValue()) {
			logger.error("主订单的订单状态异常,订单状态为:{},主订单ID:{}", saleOrder.getOrder_status(), transactionId);
			throw new PaymentCancelException(PayErrorCode.PAYMENT_CANCEL_ERROR_CODE, "订单[" + transactionId + "]状态为"
					+ OrderStatusEnum.getOrderStatus(saleOrder.getOrder_status()).getMsg());
		}
		paymentCancelHandler.doHandle(saleOrder, 123456789L);
		FreezeFlowEntity saleFreezeFlow = freezeFlowWriteMapper.getFreezeFlow(saleOrder.getOrder_id(),
				ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.Paying.getKey());
		changeFreezeFlowState(saleFreezeFlow);
		if (!Check.NuNString(saleFreezeFlow.getThird_content())) {
			PayCallbackVO vo = JSONConverter.toBean(saleFreezeFlow.getThird_content(), PayCallbackVO.class);
			callThirdPayRefund(vo, saleOrder);
		}
		callAccountPayCancel(saleFreezeFlow);
	}

	/**
	 * 提交支付取消要操作的相关业务
	 */
	private void changeFreezeFlowState(FreezeFlowEntity saleFreezeFlow) {
		List<OrderEntity> orders = orderReadMapper.getTransactionOrderById(saleFreezeFlow.getOrder_id());
		for (OrderEntity order : orders) {
			freezeFlowWriteMapper.updateFreezeFlowStatus(order.getOrder_id(), saleFreezeFlow.getSign_id(),
					PayFlowStateEnum.Payfailure.getKey());
		}
	}

	/**
	 * 调用第三方支付退款
	 * @param vo
	 */
	private void callThirdPayRefund(final PayCallbackVO vo, final OrderEntity saleOrder) {
		final RefundApplyRequestModel reqParam = new RefundApplyRequestModel();
		String refundId = UUID.randomUUID().toString().replace("-", "");
		reqParam.setOrderId(saleOrder.getOrder_id());
		reqParam.setRefundId(refundId);
		reqParam.setRefundMoney(MoneyUtils.getMoenyNumber(vo.getMoney()));
		reqParam.setRefundType(3);
		logger.info("子订单等待支付补差的时间超过了主订单支付成功后60分钟,订单执行支付取消,调用第三方退款服务,reqModel:{}", JSONConverter.toJson(reqParam));
		Result<Boolean> result = null;
		try {
			result = paymentRefundService.refundApply(reqParam);
		} catch (final Throwable t) {
			logger.error("支付取消业务调用第三方退款异常,reqModel:" + JSONConverter.toJson(reqParam) + ",errorContent:", t);
			throw new PaymentCancelException(PayErrorCode.PAYMENT_CANCEL_ERROR_CODE, "支付系统退款申请失败");
		}
		logger.info("支付取消服务调用第三方退款申请结果, reqModel:{},reqResult:{}.", JSONConverter.toJson(reqParam),
				JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new PaymentCancelException(PayErrorCode.PAYMENT_CANCEL_ERROR_CODE, "支付系统退款申请失败");
		}
	}

	/**
	 * 调用账户服务进行支付取消
	 */
	private void callAccountPayCancel(final FreezeFlowEntity saleFreezeFlow) {
		Result<Boolean> result = null;
		try {
			result = accountBussinessService.cancelSaasPayment(saleFreezeFlow.getSign_id());
		} catch (Throwable e) {
			logger.error("支付取消业务调用账户服务的支付取消接口异常,signId:" + saleFreezeFlow.getSign_id() + ",errorContent:", e);
			throw new PaymentCancelException(PayErrorCode.PAYMENT_CANCEL_ERROR_CODE, "支付系统调用账户服务失败");
		}
		logger.info("支付取消服务调用账户取消结果, reqModel:{},reqResult:{}.", saleFreezeFlow.getSign_id(), JSONConverter.toJson(result));
		if (!result.isOk()) {
			throw new PaymentCancelException(PayErrorCode.PAYMENT_CANCEL_ERROR_CODE, "支付系统调用账户服务失败");
		}
	}
}

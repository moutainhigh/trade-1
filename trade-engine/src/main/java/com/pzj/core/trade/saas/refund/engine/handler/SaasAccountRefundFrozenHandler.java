package com.pzj.core.trade.saas.refund.engine.handler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.refund.engine.common.RefundApplyTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.converter.SassAccountRefundModelConvert;
import com.pzj.core.trade.refund.engine.exception.RefundOperationIntervalException;
import com.pzj.core.trade.refund.engine.exception.SettlementRefundFrozenException;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.request.LaunchSaasRefundVo;
import com.pzj.settlement.balance.response.PaymentResponse;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.RefundMerchStrategyModel;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.trade.refund.model.RefundApplyReqModel;

@Component
public class SaasAccountRefundFrozenHandler {

	private static final Logger logger = LoggerFactory.getLogger(SaasAccountRefundFrozenHandler.class);
	@Autowired
	private SassAccountRefundModelConvert sassAccountRefundModelConvert;

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;
	@Autowired
	private AccountBussinessService accountBussinessService;

	public PaymentResponse applyAccountFrozen(final RefundApplyReqModel reqModel, final OrderEntity saleOrder,
			final RefundApplyEntity refundApply, List<RefundMerchStrategyModel> merchStrategies,
			List<RefundApplyMerchModel> refundMerches) {
		if (!PayWayEnum.getPayWay(saleOrder.getPay_way()).isOnline()) {
			return null;
		}
		LaunchSaasRefundVo model = sassAccountRefundModelConvert.convert(refundApply.getRefundId(),
				saleOrder.getTransaction_id(), refundApply.getIsForce(), saleOrder.getOrder_status(), merchStrategies,
				refundMerches);
		FreezeFlowEntity payfreezeflow = freezeFlowWriteMapper.getFreezeFlow(saleOrder.getOrder_id(),
				ReceiveTypeEnum.Payment.getValue(), PayFlowStateEnum.PaySuccess.getKey());
		model.setPaymentSignId(payfreezeflow.getSign_id());
		if (Check.NuNCollections(model.getRefundDetialList())) {
			return null;
		} else {
			logger.info("调用账户退款冻结服务输入参数为：{}", JSONConverter.toJson(model));
			try {
				Result<PaymentResponse> accountResult = accountBussinessService.launchSaasRefund(model);
				if (Check.NuNObject(accountResult) || !accountResult.isOk() || Check.NuNObject(accountResult.getData())) {
					throw new RefundOperationIntervalException(RefundErrorCode.REFUND_FROZEN_CLEAN_FLOW_ERROR_CODE,
							"调用退款操作调用账户服务资金冻结异常");
				}
				return accountResult.getData();
			} catch (RuntimeException e) {
				String errorMsg = "退款冻结账户金额,操作失败";
				if (reqModel.getRefundType() == RefundApplyTypeEnum.FORCE.getType().intValue()) {
					errorMsg = "此订单对应的返还款余额不足，无法执行强制退款";
				}
				throw new SettlementRefundFrozenException(RefundErrorCode.SETTLEMENT_ACCOUNT_BUSSINESS_ERROR_CODE, errorMsg);
			}
		}
	}
}

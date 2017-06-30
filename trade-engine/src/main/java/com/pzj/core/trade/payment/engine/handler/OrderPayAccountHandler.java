package com.pzj.core.trade.payment.engine.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.payment.engine.exception.PayAccountConfirmException;
import com.pzj.core.trade.payment.engine.exception.PayAccountFrozenErrorException;
import com.pzj.core.trade.payment.engine.exception.PayErrorCode;
import com.pzj.core.trade.payment.engine.exception.PaymentException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.settlement.balance.request.ConfirmPaymentVo;
import com.pzj.settlement.balance.request.PrePaymentVo;
import com.pzj.settlement.balance.response.PaymentResponse;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.vo.PayCallbackVO;

@Component("orderPayAccountHandler")
public class OrderPayAccountHandler {

	private final static Logger logger = LoggerFactory.getLogger(OrderPayAccountHandler.class);

	@Autowired
	private AccountBussinessService accountBussinessService;

	/**
	 * 调用账户冻结
	 *
	 * @param orderEntity
	 * @param paySignId
	 */
	public PaymentResponse doAccountFrozen(final OrderEntity orderEntity, final String paySignId) {
		final PrePaymentVo payModel = generatePrePaymentModel(orderEntity, paySignId);
		Result<PaymentResponse> result = null;
		try {
			result = accountBussinessService.preparePayOrder(payModel);
		} catch (final Throwable e) {
			logger.error("账户冻结失败, reqModel: " + (payModel), e);
			throw new PayAccountFrozenErrorException(PayErrorCode.PAY_ACCOUND_FROZEN_ERROR_CODE, e.getMessage());
		}

		if (!result.isOk()) {
			logger.error("账户冻结失败,reqModel: " + JSONConverter.toJson(payModel) + ", result: " + JSONConverter.toJson(result));
			throw new PayAccountFrozenErrorException(PayErrorCode.PAY_ACCOUND_FROZEN_ERROR_CODE, result.getErrorMsg());
		}

		final PaymentResponse paymentResponse = result.getData();
		return paymentResponse;
	}

	/**
	 * 调用资金账户进行解冻
	 *
	 * @param orderEntity
	 * @param paySignId
	 */
	public void doAccountRefrozen(final OrderEntity orderEntity, final String signId) {
		final PrePaymentVo reqModel = generatePrePaymentModel(orderEntity, signId);

		Result<Boolean> result = null;
		try {
			result = accountBussinessService.cancelPayOrder(reqModel);
		} catch (final Throwable e) {
			logger.error("账户解冻失败, reqModel: " + (reqModel), e);
			throw new PaymentException("订单的资金回滚异常", e);
		}

		if (!result.isOk()) {
			logger.error("账户解冻失败,reqModel: " + JSONConverter.toJson(reqModel) + ", result: " + JSONConverter.toJson(result));
			throw new PayAccountFrozenErrorException(PayErrorCode.PAY_ACCOUND_FROZEN_ERROR_CODE, result.getErrorMsg());
		}
	}

	/**
	 * 构建账户冻结请求参数.
	 * @param orderEntity
	 * @param signId
	 * @return
	 */
	private PrePaymentVo generatePrePaymentModel(final OrderEntity orderEntity, final String signId) {
		final PrePaymentVo prePaymentVo = new PrePaymentVo();
		prePaymentVo.setUserId(orderEntity.getReseller_id());
		prePaymentVo.setOrderId(orderEntity.getOrder_id());
		prePaymentVo.setPayMoney(MoneyUtils.getMoenyNumber(orderEntity.getTotal_amount()));
		prePaymentVo.setSignId(signId);
		prePaymentVo.setIsMShop(SalePortEnum.isMicShop(orderEntity.getSale_port()));
		return prePaymentVo;
	}

	/**
	 * 调用账户支付确认
	 *
	 * @param order
	 * @param signId
	 * @param balanceMoney
	 * @param thirdPayMoney
	 * @param payVO
	 */
	public void doAccountConfirm(final OrderEntity order, final FreezeFlowEntity flow) {
		final ConfirmPaymentVo reqModel = generateConfirmPaymentModel(order, flow);
		logger.info("账号付款确认, reqModel: " + reqModel);
		Result<Boolean> result = null;
		try {
			result = accountBussinessService.confirmPayOrder(reqModel);
		} catch (final Throwable e) {
			logger.error("账号付款确认失败, reqModel: " + (reqModel), e);
			throw new PayAccountConfirmException(PayErrorCode.PAY_ACCOUNT_CONFIRM_ERROR_CODE, "账户确认支付失败.");
		}

		if (!result.isOk()) {
			logger.error("账号付款确认失败,reqModel: " + JSONConverter.toJson(reqModel) + ", result: " + JSONConverter.toJson(result));
			throw new PayAccountConfirmException(PayErrorCode.PAY_ACCOUNT_CONFIRM_ERROR_CODE, "账户确认支付失败.");
		}
	}

	private ConfirmPaymentVo generateConfirmPaymentModel(final OrderEntity order, final FreezeFlowEntity flow) {
		final ConfirmPaymentVo reqModel = new ConfirmPaymentVo();
		reqModel.setOrderId(order.getOrder_id());
		reqModel.setSignId(flow.getSign_id());
		reqModel.setUserId(order.getReseller_id());
		reqModel.setBalancePayMoney(flow.getBalance_amount());
		reqModel.setThirdPayMoney(flow.getThird_amount());
		reqModel.setIsMShop(SalePortEnum.isMicShop(order.getSale_port()));
		if (flow.getThird_amount() > 0 && !Check.NuNString(flow.getThird_content())) {
			final PayCallbackVO payCallback = JSONConverter.toBean(flow.getThird_content(), PayCallbackVO.class);
			reqModel.setBatchTransaction(payCallback.getDealId());
			reqModel.setCounterFee(payCallback.getBankCharges());
			reqModel.setThridPayType(payCallback.getPayType());
			reqModel.setReceiptBankAccount(payCallback.getSeller_email());
		}
		return reqModel;
	}

}

package com.pzj.core.trade.payment.engine.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.payment.model.CommonPayRequestModel;
import com.pzj.core.payment.model.CommonPayResponseModel;
import com.pzj.core.payment.service.PayService;
import com.pzj.core.trade.payment.engine.exception.PayErrorCode;
import com.pzj.core.trade.payment.engine.exception.PaymentPayErrorException;
import com.pzj.framework.context.Result;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.constant.TradeConstant;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.payment.vo.PaymentVO;

/**
 * 涉及到第三方支付申请, 负责构建支付申请参数及构建响应结构.
 * @author YRJ
 *
 */
@Component(value = "thirdPayApplyEvent")
public class ThirdPayApplyEvent {

	private final static Logger logger = LoggerFactory.getLogger(ThirdPayApplyEvent.class);

	@Resource(name = "paymentPayService")
	private PayService paymentPayService;

	public CommonPayResponseModel applyThirdPay(final PaymentVO reqModel, final OrderEntity order, final double thirdAmount) {
		final CommonPayRequestModel payModel = getPayModel(reqModel, order, thirdAmount);
		logger.info("第三方支付申请, reqModel: " + payModel);

		Result<CommonPayResponseModel> payResult = null;
		try {
			payResult = paymentPayService.commonPay(payModel);
		} catch (final Throwable t) {
			logger.error("第三方支付申请失败, reqModel: " + payModel, t);

			if (t instanceof ServiceException) {
				final ServiceException se = (ServiceException) t;
				throw new PaymentPayErrorException(PayErrorCode.PAYMENT_PAY_ERROR_CODE, se.getMessage());
			}
			throw new ServiceException("支付系统异常", t);
		}

		final CommonPayResponseModel respModel = payResult.getData();
		if (!payResult.isOk() || Check.NuNObject(respModel)) {
			logger.error("第三方支付申请失败, reqModel: " + payModel + ", 响应错误码: " + payResult.getErrorCode() + ", 错误描述: "
					+ payResult.getErrorMsg());
			throw new PaymentPayErrorException(PayErrorCode.PAYMENT_PAY_ERROR_CODE, payResult.getErrorMsg());
		}
		return respModel;
	}

	/**
	 * 构建第三方支付请求参数.
	 *
	 * @param payment
	 * @param order
	 * @param thirdPayMoney
	 * @return
	 */
	private CommonPayRequestModel getPayModel(final PaymentVO payment, final OrderEntity order, final double thirdPayMoney) {
		final CommonPayRequestModel payModel = new CommonPayRequestModel();
		payModel.setPayType("PAY");
		payModel.setPayChannel(payment.getPayType());
		payModel.setSource(payment.getSource());

		payModel.setUserId(order.getPayer_id());
		payModel.setPayAmount(thirdPayMoney);
		payModel.setOrderAmount(order.getTotal_amount());
		payModel.setOrderId(order.getOrder_id());
		payModel.setAppId(payment.getAppId());
		payModel.setReturnUrl(payment.getReturnUrl());
		payModel.setRid(payment.getRid());
		payModel.setSid(payment.getSid());
		payModel.setFailUrl(payment.getFailpayUrl());
		payModel.setTimeExpire(TradeConstant.QR_CODE_EXPIRED_TIME);
		return payModel;
	}
}

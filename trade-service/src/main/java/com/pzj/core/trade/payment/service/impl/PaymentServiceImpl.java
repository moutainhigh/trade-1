package com.pzj.core.trade.payment.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.payment.engine.PaymentApplyEngine;
import com.pzj.core.trade.payment.engine.PaymentCallbackEngine;
import com.pzj.core.trade.payment.engine.PaymentCancelEngine;
import com.pzj.core.trade.payment.engine.PaymentChildEngine;
import com.pzj.core.trade.payment.engine.PaymentOkEngine;
import com.pzj.core.trade.payment.engine.exception.PayErrorCode;
import com.pzj.core.trade.payment.engine.exception.PayException;
import com.pzj.core.trade.payment.engine.exception.PayReqParamErrorException;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.service.PaymentService;
import com.pzj.trade.payment.vo.ChildOrderPaymentModel;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.vo.PaymentVO;

/**
 * 支付服务实现
 *
 * @author YRJ
 * @author DongChunfu
 */
@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService {

	private final static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Resource(name = "payRequestParamValidator")
	private ObjectConverter<ChildOrderPaymentModel, ServiceContext, Result<Boolean>> payRequestParamValidator;

	@Resource(name = "payCallbackReqParamValidator")
	private ObjectConverter<PayCallbackVO, ServiceContext, Result<Boolean>> payCallbackReqParamValidator;

	@Resource(name = "paymentApplyEngine")
	private PaymentApplyEngine paymentApplyEngine;

	@Resource(name = "paymentOkEngine")
	private PaymentOkEngine paymentOkEngine;

	@Resource(name = "paymentCallbackEngine")
	private PaymentCallbackEngine paymentCallbackEngine;

	@Resource(name = "paymentChildEngine")
	private PaymentChildEngine paymentChildEngine;
	@Autowired
	private PaymentCancelEngine paymentCancelEngine;

	@Override
	public Result<PaymentResult> payOrder(final PaymentVO reqModel, final ServiceContext context) {
		logger.info("主订单付款申请, reqModel:{}. ", reqModel);

		PaymentResult payResp = null;
		try {
			payRequestParamValidator.convert(reqModel, context);
			payResp = paymentApplyEngine.doHandler(reqModel);
		} catch (final Throwable t) {
			logger.error("主订单付款申请失败, reqModel: " + reqModel.toString(), t);

			if (t instanceof TradeException) {
				final TradeException te = (TradeException) t;
				return new Result<PaymentResult>(te.getErrCode(), te.getMessage());
			}
			throw new ServiceException("付款失败.", t);
		}

		logger.info("主订单付款申请成功, reqModel: " + (reqModel) + ", result: " + (JSONConverter.toJson(payResp)));
		return new Result<PaymentResult>(payResp);
	}

	@Override
	public Result<Boolean> payChildOrder(final ChildOrderPaymentModel reqModel, final ServiceContext context) {
		logger.info("子订单付款, reqModel: " + (reqModel));

		try {
			payRequestParamValidator.convert(reqModel, context);
			paymentChildEngine.doHandler(reqModel);
		} catch (final Throwable t) {
			logger.error("子订单付款失败, reqModel: " + reqModel.toString(), t);
			if (t instanceof TradeException) {
				final TradeException te = (TradeException) t;
				return new Result<Boolean>(te.getErrCode(), te.getMessage());
			}
			throw new ServiceException("付款失败.", t);
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

	@Override
	public Result<Boolean> payCallback(final PayCallbackVO reqModel, final ServiceContext context) {
		logger.info("支付成功回调, reqModel: " + reqModel);
		try {
			if (reqModel.getSuccess()) {
				payCallbackReqParamValidator.convert(reqModel, context);
				paymentCallbackEngine.doHandler(reqModel);
			} else {
				if (Check.NuNObject(reqModel) || Check.NuNString(reqModel.getOrderId())) {
					throw new PayReqParamErrorException(PayErrorCode.PAY_CALLBACK_REQ_PARAM_ERROR_CODE, "支付回调请求参数订单ID为空");
				}
				paymentCancelEngine.doEngine(reqModel.getOrderId());
			}
		} catch (final Throwable t) {
			logger.error("支付成功回调, reqModel: " + reqModel, t);
			if (t instanceof PayException) {
				final PayException pe = (PayException) t;
				return new Result<Boolean>(pe.getErrCode(), pe.getMessage());
			}
			throw new OrderException(10500, "付款回调失败");
		}
		logger.info("支付成功回调, reqModel: " + reqModel);
		return new Result<Boolean>(Boolean.TRUE);
	}

}

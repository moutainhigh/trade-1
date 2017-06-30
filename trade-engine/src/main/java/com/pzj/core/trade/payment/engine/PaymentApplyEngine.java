package com.pzj.core.trade.payment.engine;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.payment.model.CommonPayResponseModel;
import com.pzj.core.payment.model.WeChat;
import com.pzj.core.trade.order.engine.common.PayStateEnum;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.payment.engine.handler.FreezeFlowHandler;
import com.pzj.core.trade.payment.engine.handler.OrderPayAccountHandler;
import com.pzj.core.trade.payment.engine.handler.PaymentSuccessHandler;
import com.pzj.core.trade.payment.engine.handler.ThirdPayApplyEvent;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.entity.WeChatResult;
import com.pzj.trade.payment.vo.PaymentVO;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 支付申请引擎.
 * @author YRJ
 *
 */
@Component(value = "paymentApplyEngine")
public class PaymentApplyEngine {

	@Resource(name = "orderWriteMapper")
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Resource(name = "freezeFlowHandler")
	private FreezeFlowHandler freezeFlowHandler;

	@Resource(name = "thirdPayApplyEvent")
	private ThirdPayApplyEvent thirdPayApplyEvent;

	@Resource(name = "orderPayAccountHandler")
	private OrderPayAccountHandler orderPayAccountHandler;

	@Autowired
	private PaymentSuccessHandler paymentSuccessHandler;

	@Resource(name = "paymentOkEngine")
	private PaymentOkEngine paymentOkEngine;

	@Resource(name = "payLegitimacyValidator")
	private ObjectConverter<String, OrderEntity, Result<Boolean>> payLegitimacyValidator;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public PaymentResult doHandler(final PaymentVO reqModel) {
		// TODO 没有做主订单的校验
		final OrderEntity order = orderWriteMapper.getOrderEntityForUpdate(reqModel.getOrderId());
		payLegitimacyValidator.convert(reqModel.getOrderId(), order);
		final FreezeFlowEntity flow = freezeFlowHandler.generateFreezeFlow(order, reqModel.getPayType());
		// 当且仅当, payType == '现金' or '后付', flow == null.
		int payWay = order.getPay_way();
		if (PayWayEnum.getPayWay(payWay).isOnline()) {
			payWay = reqModel.getPayType();
		}
		int payState = PayStateEnum.ONLOCK.getPayState();
		boolean isEnd = false;
		PaymentResult payResult = null;
		final Date paydate = new Date();
		flag: {
			if (flow == null || flow.getThird_amount() == 0d) {//支付方式为'现金'、'后付'的情况.
				payResult = generatePaymentResponse(reqModel, null, 0);
				isEnd = paymentOkEngine.doSuccess(order, paydate, false);
				payState = PayStateEnum.PAYFINISH.getPayState();
				break flag;
			}
			//需要三方支付
			final CommonPayResponseModel respModel = thirdPayApplyEvent.applyThirdPay(reqModel, order, flow.getThird_amount());
			payResult = generatePaymentResponse(reqModel, respModel, flow.getThird_amount());
		}

		if (isEnd) {
			paymentSuccessHandler.doHandler(order, flow);
		}
		orderWriteMapper.updateOrderPayState(order.getOrder_id(), null, 0, payWay, payState, paydate);
		return payResult;
	}

	private PaymentResult generatePaymentResponse(final PaymentVO reqModel, final CommonPayResponseModel respModel,
			final double thirdPayMoney) {
		final PaymentResult response = new PaymentResult();
		if (respModel == null) {
			response.setPayType(0);
			response.setHtml("true");
			return response;
		}

		response.setPayType(reqModel.getPayType());
		response.setHtml(respModel.getUrl());
		response.setAliPaySign(respModel.getAliPaySign());
		response.setThirdPayMoney(thirdPayMoney);

		final WeChat weChat = respModel.getWechat();
		if (weChat != null) {
			final WeChatResult weChatResult = new WeChatResult();
			weChatResult.setAppId(weChat.getAppId());
			weChatResult.setCodeUrl(weChat.getCodeUrl());
			weChatResult.setNonceStr(weChat.getNonceStr());
			weChatResult.setOrderId(reqModel.getOrderId());
			weChatResult.setPackAge(weChat.getPackAge());
			weChatResult.setPartnerid(weChat.getPartnerid());
			weChatResult.setPaySign(weChat.getPaySign());
			weChatResult.setPrepayid(weChat.getPrepayid());
			weChatResult.setReturnUrl(weChat.getReturnUrl());
			weChatResult.setSignType(weChat.getSignType());
			weChatResult.setTimeStamp(weChat.getTimeStamp());
			response.setWeChatResult(weChatResult);
		}
		return response;
	}
}

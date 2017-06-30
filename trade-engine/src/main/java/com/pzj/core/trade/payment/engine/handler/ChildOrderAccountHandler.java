package com.pzj.core.trade.payment.engine.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.payment.engine.exception.PayAccountFrozenErrorException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.settlement.balance.request.GoodsPaymentVo;
import com.pzj.settlement.balance.service.AccountBussinessService;
import com.pzj.trade.order.entity.OrderEntity;

/**
 * 子订单支付账号操作处理器.
 * @author YRJ
 *
 */
@Component(value = "childOrderAccountHandler")
public class ChildOrderAccountHandler {

	private final static Logger logger = LoggerFactory.getLogger(ChildOrderAccountHandler.class);

	@Autowired
	private AccountBussinessService accountBussinessService;

	public AmountModel doAccountFrozen(final OrderEntity order, final double parentTotalAmount, final String signId) {
		final GoodsPaymentVo reqModel = new GoodsPaymentVo();
		reqModel.setSignId(signId);
		reqModel.setUserId(order.getReseller_id());
		reqModel.setTransactionId(order.getTransaction_id());
		final AmountModel amountModel = assembleyAmountModel(parentTotalAmount, order.getTotal_amount());
		reqModel.setGoodsAmount(amountModel.getGoodAmount());
		reqModel.setAccountAmount(amountModel.getAccountAmount());

		logger.info("货款支付, 订单[" + order.getOrder_id() + "], 参数: " + (JSONConverter.toJson(reqModel)));
		Result<Boolean> result = null;
		try {
			result = accountBussinessService.goodsPayment(reqModel);
		} catch (final Throwable e) {
			logger.error("货款支付, 订单[" + order.getOrder_id() + "],  参数: " + (JSONConverter.toJson(reqModel)), e);
			throw new PayAccountFrozenErrorException(10205, "货款支付失败, 订单[" + order.getOrder_id() + "]", e);
		}

		if (!result.isOk()) {
			logger.error("货款支付, 订单[" + order.getOrder_id() + "],  参数: " + (JSONConverter.toJson(reqModel)) + ", 错误码: " + result.getErrorCode() + ", 错误描述: "
					+ result.getErrorMsg());
			throw new PayAccountFrozenErrorException(10205, "货款支付失败, 订单[" + order.getOrder_id() + "], 错误吗: " + result.getErrorCode() + ", 错误描述: "
					+ result.getErrorMsg());
		}
		logger.info("货款支付成功, 订单[" + order.getOrder_id() + "], 参数: " + (JSONConverter.toJson(reqModel)));
		return amountModel;
	}

	/**
	 * 计算货款金额.
	 * @param parentOrderTotalAmount
	 * @param childOrderTotalAmount
	 * @return
	 */
	private AmountModel assembleyAmountModel(final double parentOrderTotalAmount, final double childOrderTotalAmount) {
		if (parentOrderTotalAmount >= childOrderTotalAmount) {
			return new AmountModel(childOrderTotalAmount, 0);
		} else {
			final double accountAmount = childOrderTotalAmount - parentOrderTotalAmount;
			return new AmountModel(parentOrderTotalAmount, accountAmount);
		}
	}

}

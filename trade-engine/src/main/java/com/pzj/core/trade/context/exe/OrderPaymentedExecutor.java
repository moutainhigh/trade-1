package com.pzj.core.trade.context.exe;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.event.OrderPaymentFinishEvent;
import com.pzj.core.trade.context.exe.base.TradeExecutor;
import com.pzj.core.trade.context.model.PaymentModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.mq.MQUtil;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component("orderPaymentedExecutor")
public class OrderPaymentedExecutor implements TradeExecutor<PaymentModel> {

	private final static Logger logger = LoggerFactory.getLogger(OrderPaymentedExecutor.class);

	@Resource(name = "mqUtil")
	private MQUtil mqUtil;
	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private OrderPaymentFinishEvent orderPaymentFinishEvent;

	@Override
	public void execute(final PaymentModel paramModel) {
		logger.info("call OrderPaymentedExecutor.execute sendParam:{}", JSONConverter.toJson(paramModel));
		try {
			if (paramModel.getPaymentSceneType() == 1) {
				payApply(paramModel);
			} else if (paramModel.getPaymentSceneType() == 2) {
				paySuccess(paramModel);
			} else if (paramModel.getPaymentSceneType() == 3) {
				payCancel(paramModel);
			}
		} catch (final Exception e) {
			logger.info("call OrderPaymentedExecutor.execute is error, sendParam:" + JSONConverter.toJson(paramModel) + ",errorContent:", e);
		}
	}

	/**
	 * 支付成功后续业务
	 * @param paramModel
	 */
	private void paySuccess(final PaymentModel paramModel) {
		final OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(paramModel.getSaleOrderId());
		orderPaymentFinishEvent.doEvent(paramModel, saleOrder);
	}
	/**
	 * 支付申请
	 * @param paramModel
	 */
	private void payApply(final PaymentModel paramModel) {

	}
	/**
	 * 支付取消
	 * @param paramModel
	 */
	private void payCancel(final PaymentModel paramModel) {

	}
}

package com.pzj.core.trade.context.exe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.clean.engine.event.RefundApplyCleanEvent;
import com.pzj.core.trade.context.event.RefundFinishEvent;
import com.pzj.core.trade.context.exe.base.TradeExecutor;
import com.pzj.core.trade.context.model.RefundModel;
import com.pzj.core.trade.refund.engine.RefundApplyMQEngine;
import com.pzj.core.trade.refund.engine.handler.RefundAuditFinishedSendOpenApiMqHandler;
import com.pzj.core.trade.refund.read.RefundApplyInfoReadMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;

@Component("orderRefundedExecutor")
public class OrderRefundedExecutor implements TradeExecutor<RefundModel> {

	private final static Logger logger = LoggerFactory.getLogger(OrderRefundedExecutor.class);
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private RefundApplyCleanEvent refundApplyCleanEvent;
	@Autowired
	private RefundApplyMQEngine refundApplyMQEngine;
	@Autowired
	private RefundFinishEvent refundFinishEvent;

	@Autowired
	private RefundApplyInfoReadMapper refundApplyInfoReadMapper;

	@Autowired
	private RefundAuditFinishedSendOpenApiMqHandler refundAuditFinishedSendOpenApiMqHandler;

	@Override
	public void execute(final RefundModel paramModel) {
		logger.info("OrderRefundedExecutor.execute getParam:{}", JSONConverter.toJson(paramModel));
		final OrderEntity saleOrder = orderWriteMapper.getOrderEntityNotLock(paramModel.getSaleOrderId());
		try {
			if (paramModel.getRefundSceneType() == 1) {
				this.refundApply(paramModel, saleOrder);
			} else if (paramModel.getRefundSceneType() == 2) {
				this.refundSuccess(paramModel, saleOrder);
			} else if (paramModel.getRefundSceneType() == 3) {
				this.refundCancel(paramModel, saleOrder);
			}
		} catch (final Exception e) {
			logger.error("OrderRefundedExecutor.execute is error,reqParam:" + JSONConverter.toJson(paramModel) + ",errorContent:", e);
		}
	}

	/**
	 * 退款申请
	 * @param paramModel
	 */
	private void refundApply(final RefundModel paramModel, final OrderEntity saleOrder) {
		//1.0老的退款操作需要将对应的待清算信息进行冻结
		if ("0".equals(saleOrder.getVersion())) {
			refundApplyCleanEvent.frozenCleanflow(paramModel.getRefundId());
		}
		refundApplyMQEngine.sendRefundApplyMQ(paramModel.getRefundId(), paramModel.getSaleOrderId());
	}

	/**
	 * 退款回滚
	 * @param paramModel
	 */
	private void refundCancel(final RefundModel paramModel, final OrderEntity saleOrder) {
		if ("0".equals(saleOrder.getVersion())) {
			refundApplyCleanEvent.cancelfrozenCleanflow(paramModel.getRefundId());
		}
		final String msg = refundApplyInfoReadMapper.queryReasonByRefundId(paramModel.getRefundId());
		refundAuditFinishedSendOpenApiMqHandler.sentHandler(paramModel.getRefundId(), saleOrder.getOrder_id(), false, msg);
	}

	/**
	 * 退款成功
	 * @param paramModel
	 */
	private void refundSuccess(final RefundModel paramModel, final OrderEntity saleOrder) {
		refundAuditFinishedSendOpenApiMqHandler.sentHandler(paramModel.getRefundId(), saleOrder.getOrder_id(), true, "");
		refundFinishEvent.doEvent(paramModel, saleOrder);
	}

}

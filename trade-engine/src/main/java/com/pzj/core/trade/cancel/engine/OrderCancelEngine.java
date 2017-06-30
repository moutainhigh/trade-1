package com.pzj.core.trade.cancel.engine;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.exception.OrderNotExistException;
import com.pzj.core.trade.payment.engine.handler.PaymentCancelHandler;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 订单取消引擎.
 * @author YRJ
 *
 */
@Component(value = "orderCancelEngine")
public class OrderCancelEngine {

	@Resource(name = "orderWriteMapper")
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Autowired
	private PaymentCancelHandler paymentCancelHandler;

	/**
	 * 查询未付款的主订单.
	 * @return
	 */
	public List<String> queryUnPaiedOrderByLimit() {
		final List<String> orderIds = orderReadMapper.getUnPayOrderIds();
		return orderIds;
	}

	/**
	 * 取消订单.
	 * @param orderId
	 * @return 支付记录信息
	 */
	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public boolean cancel(final String orderId, final long operatorId) {
		final OrderEntity order = orderWriteMapper.getOrderEntityForUpdate(orderId);
		final boolean cancel = validate(orderId, order);
		if (!cancel) {
			return cancel;
		}
		paymentCancelHandler.doHandle(order, operatorId);
		return true;
	}

	/*
	 * 验证订单是否可执行取消操作.
	 */
	private boolean validate(final String orderId, final OrderEntity order) {
		if (Check.NuNObject(order)) {
			throw new OrderNotExistException(10301, "订单[" + orderId + "]取消失败, 该订单不存在.");
		}
		if (order.getOrder_status() != OrderStatusEnum.Unpaid.getValue()) {
			return false;
		}
		if (order.getPay_state() != 0) {
			return false;
		}
		if (!order.getOrder_id().equals(order.getTransaction_id())) {
			return false;
		}
		return true;
	}
}

package com.pzj.core.trade.order.engine.event;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.sku.common.constants.ProductTypeGlobal;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.DeliveryDetailEntity;
import com.pzj.trade.order.entity.OrderBasticEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.read.DeliveryReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;

/**
 * 快递详情查询事件.
 * @author YRJ
 *
 */
@Component(value = "deliveryDetailQueryEvent")
public class DeliveryDetailQueryEvent {

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Resource(name = "deliveryReadMapper")
	private DeliveryReadMapper deliveryReadMapper;

	/**
	 * 根据订单及产品类别查询发货信息.
	 * @param orderEntity
	 * @param category
	 * @return
	 */
	public DeliveryDetailModel queryDeliveryDetailByTransactionId(OrderEntity orderEntity, int category) {
		if (category != ProductTypeGlobal.NATIVE_PRODUCT) {//特产类产品才具有发货快递信息.
			return null;
		}

		if (orderEntity.getOrder_status() != OrderStatusEnum.Finished.getValue()) {//已完成订单才具有发货快递信息.
			return null;
		}

		//先获取所有的子订单.
		//update by guanluguang 
		// 修改此处查询，如果是一级分销的话，此处查不出订单，所以要查询出所有的订单，进行过滤
		List<OrderBasticEntity> orders = orderReadMapper.queryAllChildOrderByTransactionId(orderEntity.getTransaction_id());
		OrderBasticEntity order = doFilterHigherLevel(orders);

		DeliveryDetailEntity delivery = deliveryReadMapper.getDeliveryByOrderId(order.getOrder_id());
		if (delivery == null) {
			return null;
		}
		DeliveryDetailModel deliveryModel = new DeliveryDetailModel();
		deliveryModel.setOrderID(delivery.getOrderID());
		deliveryModel.setExpressNO(delivery.getExpressNO());
		deliveryModel.setExpressCompany(delivery.getExpressCompany());
		deliveryModel.setDeliveryWay(delivery.getDeliveryWay());
		return deliveryModel;
	}

	/**
	 * 过滤最上级订单. 也就是真实供应商给一级分销商的订单.
	 * @param orders
	 * @return
	 */
	private OrderBasticEntity doFilterHigherLevel(List<OrderBasticEntity> orders) {
		OrderBasticEntity higher = orders.get(0);
		int size = orders.size();
		int index = 0;
		do {
			OrderBasticEntity order = orders.get(index);
			if (order.getOrder_level() == 1) {
				higher = order;
			}
		} while (++index < size);
		return higher;
	}
}

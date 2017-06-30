package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.DeliveryDetailEntity;

public interface DeliveryReadMapper {

	/**
	 * 根据订单ID列表查询ID查询配送信息.
	 * @param order_ids
	 * @return
	 */
	List<DeliveryDetailEntity> getDeliveryByOrderIds(@Param("order_ids") List<String> order_ids);

	/**
	 * 根据订单ID查询配送信息.
	 * @param order_id
	 * @return
	 */
	DeliveryDetailEntity getDeliveryByOrderId(String order_id);

}

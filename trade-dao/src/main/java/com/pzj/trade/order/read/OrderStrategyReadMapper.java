package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderRebateEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.model.RefundMerchStrategyModel;

public interface OrderStrategyReadMapper {
	/**
	 * 根据订单ID、商品ID查询策略信息.
	 * @param order_id
	 * @return
	 */
	List<OrderStrategyEntity> getOrderStrategys(@Param("order_id") String order_id, @Param("merch_id") String merch_id);

	/**
	 * 获取订单商品对应的价格政策
	 * @param transactionId
	 * @param merchIds
	 * @return
	 */
	List<RefundMerchStrategyModel> getRefundMerchStrategys(@Param("transactionId") String transactionId,
			@Param("merchIds") List<String> merchIds);

	/** 根据订单ID、商品ID查询策略信息.
	 * @param order_id
	 * @return
	 */
	List<OrderStrategyEntity> getOrderStrategyByOrders(@Param("order_ids") List<String> order_ids,
			@Param("merch_ids") List<String> merch_ids);

	List<OrderRebateEntity> selectOrderRebates(@Param("orderId") String orderId);

	Long getOrderMerchAfterRebateStrategyId(@Param("orderId") String orderId, @Param("merchId") String merchId);
}

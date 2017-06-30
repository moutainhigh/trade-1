package com.pzj.trade.order.write;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderEntity;

/**
 * 订单取消接口相关
 * @author Administrator
 *
 */
public interface OrdCanlHandWriteMapper {

	/**
	 * 获取订单信息加锁(订单为为非支付中的)
	 *
	 * @param order_id
	 * @return
	 */
	OrderEntity getOrdLock(@Param(value = "orderId") String orderId,
			@Param(value = "thirdPayTypes") int[] thirdPayTypes);
}

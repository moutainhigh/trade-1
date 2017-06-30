package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.query.entity.AppRebateOrdersOrdersQueryModel;
import com.pzj.trade.order.entity.AppRebateOrdersEntity;
import com.pzj.trade.order.entity.OrderCountEntity;

/**
 * app返利订单查询映射.
 * @author GLG
 *
 */
public interface OrderForAppRebateReadMapper {

	/**
	 * 统计订单总数量.
	 * @param inModel
	 * @return
	 */
	OrderCountEntity queryOrderTotalNum(@Param(value = "param") AppRebateOrdersOrdersQueryModel inModel);

	/**
	 * 售票员分页查询订单.
	 * @param inModel
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	List<AppRebateOrdersEntity> queryOrders(@Param(value = "param") AppRebateOrdersOrdersQueryModel inModel,
			@Param(value = "page_index") int page_index, @Param(value = "page_size") int page_size);

}

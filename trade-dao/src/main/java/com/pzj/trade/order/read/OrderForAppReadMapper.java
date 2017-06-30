package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.query.entity.AppOrdersOrdersQueryModel;
import com.pzj.core.trade.query.entity.AppRebateOrdersOrdersQueryModel;
import com.pzj.trade.order.entity.AppRebateOrdersEntity;
import com.pzj.trade.order.entity.OrderCountEntity;

/**
 * app账户余额订单查询映射.
 * @author GLG
 *
 */
public interface OrderForAppReadMapper {

	/**
	 * 统计订单总数量.
	 * @param inModel
	 * @return
	 */
	OrderCountEntity queryOrderTotalNum(AppRebateOrdersOrdersQueryModel inModel);

	/**
	 * 售票员分页查询订单.
	 * @param inModel
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	List<AppRebateOrdersEntity> queryOrders(@Param(value = "param") AppOrdersOrdersQueryModel inModel);

}

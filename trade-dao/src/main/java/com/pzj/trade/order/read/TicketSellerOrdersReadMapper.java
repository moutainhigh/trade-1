package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.query.entity.TicketSellerOrdersQueryModel;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.TicketSellerOrdersEntity;

/**
 * 售票员订单查询映射.
 * @author YRJ
 *
 */
public interface TicketSellerOrdersReadMapper {

	/**
	 * 统计订单总数量.
	 * @param inModel
	 * @return
	 */
	OrderCountEntity queryOrderTotalNum(@Param(value = "param") TicketSellerOrdersQueryModel inModel);

	/**
	 * 售票员分页查询订单.
	 * @param inModel
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	List<TicketSellerOrdersEntity> queryOrdersByScrollData(@Param(value = "param") TicketSellerOrdersQueryModel inModel,
			@Param(value = "page_index") int page_index, @Param(value = "page_size") int page_size);

}

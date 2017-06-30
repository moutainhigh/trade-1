package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.query.entity.OrderMerchDetailQueryModel;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderMerchDetailEntity;
import com.pzj.trade.order.entity.SettlementOrderMerchDetailEntity;

/**
 * 订单商品明细数据查询映射.
 * @author YRJ
 *
 */
public interface SettlementOrdersReadMapper {

	/**
	 * 订单商品明细统计数值.
	 * @param queryModel
	 * @return
	 */
	OrderCountEntity queryOrderTotalNum(@Param(value = "param") OrderMerchDetailQueryModel queryModel);

	/**
	 * 订单商品明细分页查询.
	 * @param queryModel
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	List<OrderMerchDetailEntity> queryOrdersByScrollData(@Param(value = "param") OrderMerchDetailQueryModel queryModel,
			@Param(value = "page_index") int page_index, @Param(value = "page_size") int page_size);

	List<String> queryOrderIds(@Param(value = "param") OrderMerchDetailQueryModel queryModel, @Param(value = "page_index") int page_index,
			@Param(value = "page_size") int page_size);

	List<SettlementOrderMerchDetailEntity> queryOrderMerchsByUser(@Param(value = "param") OrderMerchDetailQueryModel queryModel);

}

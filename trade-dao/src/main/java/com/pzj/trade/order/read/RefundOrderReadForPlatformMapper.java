package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.ForceRefundOrderCountEntity;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderListParameter;

/**
 * 支撑平台退款审核、已强制退款订单查询
 * */
public interface RefundOrderReadForPlatformMapper {
	/**
	 * 支撑平台根据强制退款流水参数查询订单数量.
	 *
	 * @param param
	 * @return
	 */
	OrderCountEntity getRefundOrderCountByCondition(@Param(value = "param") OrderListParameter param);

	/**
	 * 支撑平台根据强制退款流水参数查询订单.
	 *
	 * @param param
	 * @return
	 */
	List<MerchListEntity> getRefundOrderByCondition(@Param(value = "param") OrderListParameter param, @Param(value = "page_index") int page_index,
			@Param(value = "page_size") int page_size);

	/**
	 * 支撑平台根据强制退款流水参数查询订单数量.
	 *
	 * @param param
	 * @return
	 */
	List<ForceRefundOrderCountEntity> getForceRefundOrderCountByCondition(@Param(value = "param") OrderListParameter param);

	/**
	 * 支撑平台根据强制退款流水参数查询订单.
	 *
	 * @param param
	 * @return
	 */
	List<MerchListEntity> getForceRefundOrderByCondition(@Param(value = "param") OrderListParameter param);
}

package com.pzj.trade.merch.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.merch.entity.MerchCleanRelationEntity;

public interface MerchCleanReadMapper {

	/**
	 * 根据是否自动清算获取订单商 品信息
	 * @param is_manual
	 * @return
	 */
	List<String> getOrderIdsByIsManual(Integer is_manual);

	/**
	 * 根据订单ID和商品ID获取该商品对应的清算信息.
	 * @param orderId
	 * @param merchId
	 * @return
	 */
	MerchCleanRelationEntity queryCleanRelationByOrderIdAndMerchId(@Param(value = "order_id") String orderId, @Param(value = "merch_id") String merchId);

	/**
	 * 根据订单ID和商品ID获取该商品对应的清算信息集合.
	 * @param orderId
	 * @param merchId
	 * @return
	 */
	List<MerchCleanRelationEntity> queryCleanRelationsByOrderIdAndMerchId(@Param(value = "order_id") String orderId, @Param(value = "merch_id") String merchId);

	/**
	 * 根据商品ID获取该商品对应的已清算信息集合.
	 * @param merchId
	 * @return
	 */
	List<MerchCleanRelationEntity> queryCleanedByMerchId(@Param(value = "merch_id") String merchId);
}

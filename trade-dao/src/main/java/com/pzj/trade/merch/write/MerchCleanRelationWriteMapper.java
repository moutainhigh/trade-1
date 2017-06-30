package com.pzj.trade.merch.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.merch.entity.MerchCleanRelationEntity;

/**
 * 商品退款记录写库操作.
 * @author YRJ
 *
 */
public interface MerchCleanRelationWriteMapper {

	/**
	 * 保存商品结算关系.
	 * @param relation
	 */
	void insertMerchCleanRelation(@Param(value = "relations") List<MerchCleanRelationEntity> relations);

	/**
	 * 根据订单ID和商品ID获取该商品对应的清算信息.
	 * @param orderId
	 * @param merchId
	 * @return
	 */
	MerchCleanRelationEntity queryCleanRelationByOrderIdAndMerchId(@Param(value = "order_id") String orderId,
			@Param(value = "merch_id") String merchId, @Param("clean_state") int cleanState);

	/**
	 * 获取待清算的记录.
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<MerchCleanRelationEntity> queryUnClearRecordByPager(@Param(value = "now_time") long now_time,
			@Param(value = "pageIndex") int pageIndex, @Param(value = "pageSize") int pageSize);

	/**
	 * 更新清算记录状态.
	 * @param clean_id
	 * @param state
	 */
	void updateCleanRelationStateById(@Param(value = "clean_id") long clean_id, @Param(value = "state") int state);
}

package com.pzj.trade.order.read;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.TouristEntity;

public interface TouristReadMapper {

	/**
	 * 身份证判重.
	 * @param order_id
	 * @return
	 */
	List<String> getUsedIdcarNo(@Param("productId") long productId, @Param("supplierId") long supplierId, @Param("idcardNos") List<String> idcardNos,
			@Param("playDate") Date playDate);

	/**
	 * 根据订单id 商品id查询出游客信息
	 * */
	List<TouristEntity> getByOrderMerchId(@Param("order_id") String order_id, @Param("merch_id") String merch_id);

}

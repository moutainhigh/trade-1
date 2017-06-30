/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.TouristEditModel;
import com.pzj.trade.order.entity.TouristEntity;

/**
 * 游客实体
 * 
 * @author Administrator
 * @version $Id: TouristWriteMapper.java, v 0.1 2017年3月21日 下午2:57:04 Administrator Exp $
 */
public interface TouristWriteMapper {

	/**
	 * 保存游客信息.
	 * @param tourists
	 */
	void insertTourists(@Param(value = "tourists") List<TouristEntity> tourists);

	/**
	 * 根据游客ID及订单ID查询游客.
	 * @param order_id
	 * @param tourist_id
	 * @return
	 */
	TouristEntity queryTouristById(@Param(value = "order_id") String order_id, @Param(value = "tourist_id") long tourist_id);

	/**
	 * 更新游客信息.
	 * @param inModel
	 */
	void updateTourist(TouristEditModel editModel);
}

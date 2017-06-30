/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.trade.order.entity.TouristEntity;
import com.pzj.trade.order.model.OrderTouristOutModel;
import com.pzj.trade.order.read.TouristReadMapper;

/**
 * 查询游客信息
 * @author Administrator
 * @version $Id: OrderTouristEvent.java, v 0.1 2017年3月27日 下午1:50:49 Administrator Exp $
 */

@Component(value = "orderTouristEvent")
public class OrderTouristEvent {

	@Resource(name = "touristReadMapper")
	private TouristReadMapper touristReadMapper;

	public List<OrderTouristOutModel> getOrderToruists(String order_id, String merch_id) {
		List<TouristEntity> touristEntitys = touristReadMapper.getByOrderMerchId(order_id, merch_id);
		return assembleOrderTourist(touristEntitys);
	}

	private List<OrderTouristOutModel> assembleOrderTourist(List<TouristEntity> touristEntitys) {
		List<OrderTouristOutModel> orderTouristOutModels = new ArrayList<OrderTouristOutModel>();
		for (TouristEntity touristEntity : touristEntitys) {
			OrderTouristOutModel orderTouristOutModel = new OrderTouristOutModel();
			orderTouristOutModel.setOther(touristEntity.getOther());
			orderTouristOutModel.setMerchId(touristEntity.getMerch_id());
			orderTouristOutModel.setCreateTime(touristEntity.getCreate_time());
			orderTouristOutModel.setIdcard(touristEntity.getIdcard());
			orderTouristOutModel.setMobile(touristEntity.getMobile());
			orderTouristOutModel.setName(touristEntity.getName());
			orderTouristOutModel.setNameSpell(touristEntity.getName_spell());
			orderTouristOutModel.setTouristId(touristEntity.getTourist_id());
			orderTouristOutModels.add(orderTouristOutModel);

		}
		return orderTouristOutModels;
	}
}

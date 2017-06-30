/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.AppOrdersOrdersQueryModel;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.AppRebateOrdersEntity;
import com.pzj.trade.order.model.AppOrdersRespModel;
import com.pzj.trade.order.model.AppRebateMerchRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderForAppReadMapper;

/**
 * app账户余额订单列表引擎
 * @author GLG 
 * @version $Id: AppRebateOrderQueryEngine.java, v 0.1 2017年3月14日 下午5:57:05 Administrator Exp $
 */
@Component(value = "appOrderQueryEngine")
public class AppOrderQueryEngine {

	@Resource(name = "orderForAppReadMapper")
	private OrderForAppReadMapper orderForAppReadMapper;
	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	public List<AppOrdersRespModel> queryOrders(AppOrdersOrdersQueryModel queryModel) {

		List<AppRebateOrdersEntity> orderEntities = orderForAppReadMapper.queryOrders(queryModel);
		if (orderEntities == null || orderEntities.size() == 0) {
			return null;
		}
		Set<String> paramTransactionIds = new HashSet<String>();
		for (final AppRebateOrdersEntity order : orderEntities) {
			paramTransactionIds.add(order.getTransaction_id());
		}
		//根据订单查询出所有的商品
		final List<MerchEntity> merchEntitys = merchReadMapper.getMerchByTransactionIds(new ArrayList<String>(paramTransactionIds));

		List<AppOrdersRespModel> orders = new ArrayList<AppOrdersRespModel>();

		for (AppRebateOrdersEntity orderEntity : orderEntities) {
			AppOrdersRespModel order = generateOrderOutModel(orderEntity, merchEntitys);
			orders.add(order);
		}

		return orders;
	}

	private AppOrdersRespModel generateOrderOutModel(AppRebateOrdersEntity orderEntity, List<MerchEntity> merchEntitys) {
		AppOrdersRespModel orderModel = new AppOrdersRespModel();
		orderModel.setCreateTime(orderEntity.getCreate_time());
		orderModel.setOrderId(orderEntity.getOrder_id());
		orderModel.setResellerId(orderEntity.getReseller_id());
		orderModel.setTotalAmount(BigDecimal.valueOf(orderEntity.getTotal_amount()));
		orderModel.setResellerId(orderEntity.getReseller_id());
		orderModel.setTransactionId(orderEntity.getTransaction_id());
		orderModel.setTotalNum(orderEntity.getTotal_num());
		//添加商品信息
		for (final MerchEntity me : merchEntitys) {
			if (orderModel.getTransactionId().equals(me.getOrder_id())) {
				final List<AppRebateMerchRespModel> merchEntities = orderModel.getMerchs();
				final AppRebateMerchRespModel merchEntity = convertToMerchOutModel(me);
				merchEntities.add(merchEntity);
			}
		}
		return orderModel;
	}

	private AppRebateMerchRespModel convertToMerchOutModel(MerchEntity merch) {
		AppRebateMerchRespModel appRebateMerchRespModel = new AppRebateMerchRespModel();
		appRebateMerchRespModel.setSupProductName(merch.getMerch_name());
		appRebateMerchRespModel.setTotalNum(merch.getTotal_num());
		appRebateMerchRespModel.setProductId(merch.getProduct_id());
		appRebateMerchRespModel.setVisitStartTime(merch.getStart_time());
		appRebateMerchRespModel.setVisitEndTime(merch.getExpire_time());
		appRebateMerchRespModel.setSkuProductName(merch.getSku_name());
		return appRebateMerchRespModel;
	}

}

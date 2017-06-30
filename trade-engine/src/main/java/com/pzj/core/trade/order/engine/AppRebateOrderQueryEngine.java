/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.AppRebateOrdersOrdersQueryModel;
import com.pzj.framework.entity.PageableRequestBean;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.AppRebateOrdersEntity;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.model.AppRebateMerchRespModel;
import com.pzj.trade.order.model.AppRebateOrdersOutModel;
import com.pzj.trade.order.model.AppRebateOrdersRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderForAppRebateReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;

/**
 * app返利订单列表引擎
 * @author GLG 
 * @version $Id: AppRebateOrderQueryEngine.java, v 0.1 2017年3月14日 下午5:57:05 Administrator Exp $
 */
@Component(value = "appRebateOrderQueryEngine")
public class AppRebateOrderQueryEngine {

	@Resource(name = "orderForAppRebateReadMapper")
	private OrderForAppRebateReadMapper orderForAppRebateReadMapper;
	@Resource(name = "orderStrategyReadMapper")
	private OrderStrategyReadMapper orderStrategyReadMapper;
	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	public AppRebateOrdersRespModel queryOrders(AppRebateOrdersOrdersQueryModel queryModel, PageableRequestBean pageBean) {

		OrderCountEntity orderCountEntity = orderForAppRebateReadMapper.queryOrderTotalNum(queryModel);

		List<AppRebateOrdersEntity> orderEntities = orderForAppRebateReadMapper.queryOrders(queryModel, pageBean.getPageIndex(), pageBean.getPageSize());
		if (orderEntities == null || orderEntities.size() == 0) {
			return null;
		}
		Set<String> paramTransactionIds = new HashSet<String>();
		for (final AppRebateOrdersEntity order : orderEntities) {
			paramTransactionIds.add(order.getTransaction_id());
		}
		//根据订单查询出所有的商品
		final List<MerchEntity> merchEntitys = merchReadMapper.getMerchByTransactionIds(new ArrayList<String>(paramTransactionIds));
		AppRebateOrdersRespModel appRebateOrdersRespModel = new AppRebateOrdersRespModel();
		List<AppRebateOrdersOutModel> orders = new ArrayList<AppRebateOrdersOutModel>();

		for (AppRebateOrdersEntity orderEntity : orderEntities) {
			AppRebateOrdersOutModel order = generateOrderOutModel(orderEntity, merchEntitys);
			orders.add(order);
		}
		appRebateOrdersRespModel.setOrders(orders);

		appRebateOrdersRespModel.setCurrentPage(pageBean.getCurrentPage());
		appRebateOrdersRespModel.setMaxPage(pageBean.getPageSize());
		appRebateOrdersRespModel.setTotalNum(orderCountEntity.getOrder_num());
		return appRebateOrdersRespModel;
	}

	private AppRebateOrdersOutModel generateOrderOutModel(AppRebateOrdersEntity orderEntity, List<MerchEntity> merchEntitys) {

		AppRebateOrdersOutModel orderModel = new AppRebateOrdersOutModel();
		orderModel.setOrderCreateTime(orderEntity.getCreate_time());
		orderModel.setOrderId(orderEntity.getOrder_id());
		orderModel.setResellerId(orderEntity.getReseller_id());
		orderModel.setTotalAmount(orderEntity.getTotal_amount());
		orderModel.setOrderState(orderEntity.getOrder_status());
		orderModel.setTransactionId(orderEntity.getTransaction_id());

		List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategys(orderEntity.getOrder_id(), null);
		BigDecimal after_rebate_amount = new BigDecimal(0);
		if (orderStrategyEntitys != null && orderStrategyEntitys.size() != 0) {//如果订单政策不为空
			for (OrderStrategyEntity orderStrategyEntity : orderStrategyEntitys) {
				if (orderStrategyEntity.getAfter_rebate_amount() != null) {//如果后返金额不为空，计算订单的后返金额

					for (final MerchEntity merchEntity : merchEntitys) {
						if (orderStrategyEntity.getMerchId().equals(merchEntity.getMerch_id())) {
							if (merchEntity.getIs_refunding() != 1) {//如果不存在退款中的商品，那么按返利单价*（总数-退款数）计算后返金额
								after_rebate_amount = after_rebate_amount.add(orderStrategyEntity.getAfter_rebate_amount().multiply(
										new BigDecimal(merchEntity.getTotal_num() - merchEntity.getRefund_num())));
							} else {//如果存在退款中的商品，那么按返利单价*总数计算后返金额
								after_rebate_amount = after_rebate_amount.add(orderStrategyEntity.getAfter_rebate_amount().multiply(
										new BigDecimal(merchEntity.getTotal_num())));
							}
						}
					}
				}
				if (orderStrategyEntity.getRebate_settlement() == 1) {//如果是立返
					orderModel.setRebateTimeMark("订单完成后");

				} else {//如果是周期返
					if (orderEntity.getOrder_status() == OrderStatusEnum.Finished.getValue()) {//如果是订单是已完成状态,则计算预计返利时间
						if (!Check.NuNObject(orderEntity.getConfirm_time())) {//如果核销日期不为空，则计算具体的预计返利时间
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(orderEntity.getConfirm_time());
							if (!Check.NuNObject(orderStrategyEntity.getInterval_day()) && orderStrategyEntity.getInterval_day() > 0) {//如果是次日返，那么预计返利时间=核销时间+次日返日期
								calendar.add(calendar.DATE, orderStrategyEntity.getInterval_day());
							} else {//如果是次日返，那么预计返利时间=下个月一号
								calendar.add(Calendar.MONTH, 1);
								calendar.set(Calendar.DAY_OF_MONTH, 1);
							}
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							orderModel.setRebateTimeMark(format.format(calendar.getTime()));
						} else {
							if (!Check.NuNObject(orderStrategyEntity.getInterval_day()) && orderStrategyEntity.getInterval_day() > 0) {
								orderModel.setRebateTimeMark("订单完成后" + orderStrategyEntity.getInterval_day() + "天");
							} else {
								orderModel.setRebateTimeMark("订单完成后的下个自然月1号");
							}

						}
					} else {//如果是订单不是已完成状态，那么只显示文案
						if (!Check.NuNObject(orderStrategyEntity.getInterval_day()) && orderStrategyEntity.getInterval_day() > 0) {//如果是次日返
							orderModel.setRebateTimeMark("订单完成后" + orderStrategyEntity.getInterval_day() + "天");
						} else {
							orderModel.setRebateTimeMark("订单完成后的下个自然月1号");
						}
					}
				}
			}
		}
		orderModel.setRebatePrice(after_rebate_amount.doubleValue());
		orderModel.setResellerId(orderEntity.getReseller_id());
		//添加商品信息
		for (final MerchEntity me : merchEntitys) {
			if (orderModel.getTransactionId().equals(me.getOrder_id())) {
				final List<AppRebateMerchRespModel> merchEntities = orderModel.getAppRebateMerchRespModels();
				final AppRebateMerchRespModel merchEntity = convertToMerchOutModel(me, orderStrategyEntitys);
				merchEntities.add(merchEntity);
			}
		}
		return orderModel;
	}

	private AppRebateMerchRespModel convertToMerchOutModel(MerchEntity merch, List<OrderStrategyEntity> orderStrategyEntitys) {
		AppRebateMerchRespModel appRebateMerchRespModel = new AppRebateMerchRespModel();
		appRebateMerchRespModel.setSupProductName(merch.getMerch_name());
		appRebateMerchRespModel.setTotalNum(merch.getTotal_num());
		appRebateMerchRespModel.setProductId(merch.getProduct_id());
		appRebateMerchRespModel.setVisitStartTime(merch.getStart_time());
		appRebateMerchRespModel.setVisitEndTime(merch.getExpire_time());
		if (orderStrategyEntitys != null && orderStrategyEntitys.size() != 0) {
			for (OrderStrategyEntity orderStrategyEntity : orderStrategyEntitys) {
				if (orderStrategyEntity.getAfter_rebate_amount() != null) {
					if (orderStrategyEntity.getMerchId().equals(merch.getMerch_id())) {
						appRebateMerchRespModel.setStrategyId(orderStrategyEntity.getStrategyId());
					}
				}
			}
		}
		return appRebateMerchRespModel;
	}
}

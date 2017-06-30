/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.converter.OrderListResponseConverter;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.MerchListEntity;
import com.pzj.trade.order.entity.ForceRefundOrderCountEntity;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.entity.OrderListResponse;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.read.RefundOrderReadForPlatformMapper;
import com.pzj.trade.order.vo.OrderListVO;

/**
 * 
 * @author Administrator
 * @version $Id: OrderQueryDetailEngine.java, v 0.1 2017年4月12日 下午3:36:45 Administrator Exp $
 */
@Component(value = "orderQuerysOnPlatformEngine")
public class OrderQuerysOnPlatformEngine {

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Resource(name = "refundOrderReadForPlatformMapper")
	private RefundOrderReadForPlatformMapper refundOrderReadForPlatformMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Autowired
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Resource(name = "orderListResponseConverter")
	private OrderListResponseConverter orderListResponseConverter;

	public QueryResult<OrderListResponse> queryOrdersOnPlatform(final OrderListParameter param, final OrderListVO orderVO) {

		final QueryResult<OrderListResponse> qr = new QueryResult<OrderListResponse>(orderVO.getCurrent_page(), orderVO.getPage_size());
		qr.setTotal(0);
		// 1. 先查询订单数量.
		final OrderCountEntity orderCountEntity = orderReadMapper.getOrderCountByCondition(param);
		if (orderCountEntity.getOrder_num() == 0) {
			return qr;
		}
		qr.setTotal(orderCountEntity.getOrder_num());

		// 2. 订单数量大于0时, 查询真实的订单信息.
		final List<MerchListEntity> merchs = orderReadMapper.getOrderByCondition(param, orderVO.getPage_index(), orderVO.getPage_size());
		//查询所有订单商品的价格，结算价，渠道，政策
		Set<String> orderIds = new HashSet<String>();
		Set<String> merchIds = new HashSet<String>();
		for (final MerchListEntity merch : merchs) {
			merchIds.add(merch.getMerch_id());
			orderIds.add(merch.getOrder_id());
		}
		List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(orderIds),
				new ArrayList<String>(merchIds));

		final Map<String, OrderListResponse> orders = new LinkedHashMap<String, OrderListResponse>();
		for (final MerchListEntity merch : merchs) {
			OrderListResponse order = orders.get(merch.getOrder_id());
			if (order == null) {
				order = orderListResponseConverter.convert(merch, null);
				orders.put(merch.getOrder_id(), order);
			}

			final List<MerchResponse> merchEntities = order.getMerchs();
			final MerchResponse merchEntity = orderListResponseConverter.convertToMerch(merch, orderStrategyEntitys);

			// 查询商品的退款信息
			if (merchEntity.getRefundNum() > 0) {
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getOrderId(), merchEntity.getMerchId(), merch.getVersion(),
						merch.getTransaction_id(), merch.getRoot_merch_id());
				merchEntity.setRefundInfos(refundFlowResponses);

			} else {
				merchEntity.setRefundInfos(new ArrayList<RefundFlowResponse>());
			}
			merchEntities.add(merchEntity);
		}

		final List<OrderListResponse> orderResponse = new ArrayList<OrderListResponse>();
		for (final Entry<String, OrderListResponse> entry : orders.entrySet()) {
			orderResponse.add(entry.getValue());
		}
		if (!Check.NuNCollections(orderResponse)) {
			orderResponse.get(0).setAll_merch_num(orderCountEntity.getMerch_num());
			orderResponse.get(0).setAll_amount(orderCountEntity.getAmount());
		}
		qr.setRecords(orderResponse);
		return qr;
	}

	public QueryResult<OrderListResponse> queryRefundAuditOrdersOnPlatform(final OrderListParameter param, final OrderListVO orderVO) {
		final QueryResult<OrderListResponse> qr = new QueryResult<OrderListResponse>(orderVO.getCurrent_page(), orderVO.getPage_size());
		qr.setTotal(0);

		// 1. 先查询订单数量.
		final OrderCountEntity orderCountEntity = refundOrderReadForPlatformMapper.getRefundOrderCountByCondition(param);
		if (orderCountEntity.getOrder_num() == 0) {
			return qr;
		}
		qr.setTotal(orderCountEntity.getOrder_num());

		// 2. 订单数量大于0时, 查询真实的订单信息.
		final List<MerchListEntity> merchs = refundOrderReadForPlatformMapper.getRefundOrderByCondition(param, orderVO.getPage_index(), orderVO.getPage_size());

		//查询所有订单商品的价格，结算价，渠道，政策
		Set<String> orderIds = new HashSet<String>();
		Set<String> merchIds = new HashSet<String>();
		for (final MerchListEntity merch : merchs) {
			merchIds.add(merch.getMerch_id());
			orderIds.add(merch.getOrder_id());
		}
		List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(orderIds),
				new ArrayList<String>(merchIds));

		final Map<String, OrderListResponse> orders = new LinkedHashMap<String, OrderListResponse>();
		for (final MerchListEntity merch : merchs) {
			OrderListResponse order = orders.get(merch.getOrder_id());
			if (order == null) {
				order = orderListResponseConverter.convert(merch, null);
				orders.put(merch.getOrder_id(), order);
			}

			final List<MerchResponse> merchEntities = order.getMerchs();
			final MerchResponse merchEntity = orderListResponseConverter.convertToMerch(merch, orderStrategyEntitys);

			// 查询商品的退款信息
			if (merchEntity.getRefundNum() > 0) {
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getOrderId(), merchEntity.getMerchId(), merch.getVersion(),
						merch.getTransaction_id(), merch.getMerch_id());
				merchEntity.setRefundInfos(refundFlowResponses);
			} else {
				merchEntity.setRefundInfos(new ArrayList<RefundFlowResponse>());
			}
			merchEntities.add(merchEntity);
		}

		final List<OrderListResponse> orderResponse = new ArrayList<OrderListResponse>();
		for (final Entry<String, OrderListResponse> entry : orders.entrySet()) {
			orderResponse.add(entry.getValue());
		}
		if (!Check.NuNCollections(orderResponse)) {
			orderResponse.get(0).setAll_merch_num(orderCountEntity.getMerch_num());
			orderResponse.get(0).setAll_amount(orderCountEntity.getAmount());
		}
		qr.setRecords(orderResponse);

		return qr;
	}

	public QueryResult<OrderListResponse> queryForceRefundOrdersOnPlatform(final OrderListParameter param, final OrderListVO orderVO) {
		final QueryResult<OrderListResponse> qr = new QueryResult<OrderListResponse>(orderVO.getCurrent_page(), orderVO.getPage_size());
		qr.setTotal(0);
		// 1. 先查询订单数量.
		final List<ForceRefundOrderCountEntity> forceRefundOrderCountEntitys = refundOrderReadForPlatformMapper.getForceRefundOrderCountByCondition(param);
		final OrderCountEntity orderCountEntity = refundOrderReadForPlatformMapper.getRefundOrderCountByCondition(param);
		if (forceRefundOrderCountEntitys == null || forceRefundOrderCountEntitys.size() == 0) {
			return qr;
		}
		qr.setTotal(forceRefundOrderCountEntitys.size());

		final List<String> paramOrderIds = new ArrayList<String>();
		final List<String> paramTransactionIds = new ArrayList<String>();
		final List<String> paramRefundIds = new ArrayList<String>();

		for (int i = orderVO.getPage_index(); i < orderVO.getPage_index() + orderVO.getPage_size(); i++) {
			if (i < forceRefundOrderCountEntitys.size()) {
				paramOrderIds.add(forceRefundOrderCountEntitys.get(i).getOrder_id());
				paramRefundIds.add(forceRefundOrderCountEntitys.get(i).getRefund_id());
				paramTransactionIds.add(forceRefundOrderCountEntitys.get(i).getTransaction_id());
			} else {
				break;
			}

		}
		param.setOrder_ids(paramOrderIds);
		param.setRefund_ids(paramRefundIds);

		// 2. 订单数量大于0时, 查询真实的订单信息.
		final List<MerchListEntity> orders = refundOrderReadForPlatformMapper.getForceRefundOrderByCondition(param);

		//根据订单查询出所有的商品
		final List<MerchEntity> merchEntitys = merchReadMapper.getMerchByTransactionIds(paramTransactionIds);
		final List<OrderListResponse> orderResponse = new ArrayList<OrderListResponse>();
		//查询所有订单商品的价格，结算价，渠道，政策
		Set<String> merchIds = new HashSet<String>();
		for (final MerchEntity merch : merchEntitys) {
			merchIds.add(merch.getMerch_id());
		}
		List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(paramOrderIds, new ArrayList<String>(merchIds));

		//组装订单商品信息
		for (final MerchListEntity orderMerch : orders) {
			final OrderListResponse order = orderListResponseConverter.convert(orderMerch, null);
			for (final MerchEntity me : merchEntitys) {
				if (me.getOrder_id() != null) {
					if (me.getOrder_id().equals(order.getTransactionId())) {
						final List<MerchResponse> merchEntities = order.getMerchs();
						final MerchResponse merchEntity = orderListResponseConverter.convertToMerchEntity(me, orderStrategyEntitys, orderMerch);
						// 查询商品的退款信息
						if (merchEntity.getRefundNum() > 0) {
							List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(order.getOrderId(), merchEntity.getMerchId(),
									orderMerch.getVersion(), orderMerch.getTransaction_id(), merchEntity.getRootMerchId());
							merchEntity.setRefundInfos(refundFlowResponses);
						} else {
							merchEntity.setRefundInfos(new ArrayList<RefundFlowResponse>());
						}
						merchEntities.add(merchEntity);
					}
				}
			}
			orderResponse.add(order);
		}
		if (!Check.NuNCollections(orderResponse)) {
			orderResponse.get(0).setAll_merch_num(orderCountEntity.getMerch_num());
			orderResponse.get(0).setAll_amount(orderCountEntity.getAmount());
		}
		qr.setRecords(orderResponse);

		return qr;
	}
}

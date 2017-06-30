/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.common.OrderRebateMethodEnum;
import com.pzj.core.trade.order.engine.converter.OrderDetailResponseByPlatformConverter;
import com.pzj.core.trade.order.engine.event.DeliveryDetailQueryEvent;
import com.pzj.core.trade.order.engine.event.MerchCleanRelationEvent;
import com.pzj.core.trade.order.engine.event.OrderTouristEvent;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.order.engine.event.RemarkQueryEvent;
import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.entity.QueryResult;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.entity.ConfirmCodeEntity;
import com.pzj.trade.confirm.read.ConfirmCodeReadMapper;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderDetailResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderExtendAttrEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.entity.SupplierOrderEntity;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.OrderTouristOutModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.trade.order.vo.OrderDetailVO;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 
 * @author Administrator
 * @version $Id: OrderQueryDetailEngine.java, v 0.1 2017年4月12日 下午3:36:45 Administrator Exp $
 */
@Component(value = "orderQueryDetailEngine")
public class OrderQueryDetailEngine {

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "orderDetailResponseConverter")
	private ObjectConverter<OrderEntity, List<MerchEntity>, OrderDetailResponse> orderDetailResponseConverter;

	/** 填单项 */
	@Autowired
	private OrderExtendAttrEngine orderExtendAttrEngine;

	@Resource(name = "confirmCodeReadMapper")
	private ConfirmCodeReadMapper confirmCodeReadMapper;

	@Resource(name = "orderDetailResponseByPlatformConverter")
	private OrderDetailResponseByPlatformConverter orderDetailResponseByPlatformConverter;

	@Autowired
	private DeliveryEngine deliveryEngine;

	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Resource(name = "merchCleanRelationEvent")
	private MerchCleanRelationEvent merchCleanRelationEvent;

	@Resource(name = "orderTouristEvent")
	private OrderTouristEvent orderTouristEvent;

	@Resource(name = "remarkQueryEvent")
	private RemarkQueryEvent remarkQueryEvent;

	@Autowired
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Resource(name = "deliveryDetailQueryEvent")
	private DeliveryDetailQueryEvent deliveryDetailQueryEvent;

	public OrderDetailResponse queryOrderDetail(final OrderDetailVO orderDetailVO) {
		// 查询所有订单
		final OrderEntity orderEntity = orderReadMapper.getOrderById(orderDetailVO.getOrder_id());
		if (orderEntity == null) {
			return null;
		}
		// 查询商品和政策
		final List<MerchEntity> merchs = merchReadMapper.getMerchWithStrategyByTransactionId(orderEntity.getOrder_id(), orderEntity.getTransaction_id());
		// 查询填单项
		final OrderExtendAttrEntity extendAttr = new OrderExtendAttrEntity();
		extendAttr.setTransaction_id(orderEntity.getTransaction_id());
		final List<FilledModel> filledModelList = orderExtendAttrEngine.queryOrderExtendAttrList(extendAttr);

		final OrderDetailResponse order = orderDetailResponseConverter.convert(orderEntity, merchs);
		order.setFilledModelList(filledModelList);

		// 查询魔方码
		final ConfirmCodeEntity confirmCodeEntity = confirmCodeReadMapper.getMftourCodeByTransactionId(orderEntity.getTransaction_id());
		if (!Check.NuNObject(confirmCodeEntity)) {
			order.setMftour_code(confirmCodeEntity.getMf_code());
			order.setCode_state(confirmCodeEntity.getCode_state());
		}

		// 查询快递信息
		DeliveryDetailModel deliveryDetailModel = new DeliveryDetailModel();
		if (merchs != null && merchs.size() > 0) {
			deliveryDetailModel = deliveryDetailQueryEvent.queryDeliveryDetailByTransactionId(orderEntity, merchs.get(0).getMerch_type());
		}
		order.setDelivery_info(deliveryDetailModel);
		//获取初始供应商id
		order.setOrigin_supplier_id(merchs.get(0).getSupplier_id());
		//获取订单返利信息
		final List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategys(orderEntity.getOrder_id(), null);
		BigDecimal after_rebate_amount = new BigDecimal(0);
		int rebateMethod = OrderRebateMethodEnum.BEFORE.getMethod();
		for (final MerchResponse merchResponse : order.getMerchs()) {
			if (merchResponse.getRefundNum() > 0) {
				Integer version = 1;
				if (orderEntity.getVersion() != null) {
					version = Integer.valueOf(orderEntity.getVersion());
				}
				final List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(orderEntity.getOrder_id(), merchResponse.getMerchId(),
						version, orderEntity.getTransaction_id(), merchResponse.getRootMerchId());
				merchResponse.setRefundInfos(refundFlowResponses);
			}
			final List<MerchCleanRelationResponse> merchCleanRelationResponses = merchCleanRelationEvent.getMerchCleanRelation(merchResponse.getOrderId(),
					merchResponse.getMerchId());
			merchResponse.setMerchCleanRelations(merchCleanRelationResponses);
			//查询游客信息
			final List<OrderTouristOutModel> orderTouristOutModels = orderTouristEvent.getOrderToruists(order.getTransaction_id(), merchResponse.getMerchId());
			merchResponse.setOrderTouristOutModels(orderTouristOutModels);
			if (orderStrategyEntitys != null && orderStrategyEntitys.size() != 0) {
				for (final OrderStrategyEntity orderStrategyEntity : orderStrategyEntitys) {
					if (orderStrategyEntity.getMerchId().equals(merchResponse.getMerchId())) {
						if (orderStrategyEntity.getAfter_rebate_amount() != null) {
							after_rebate_amount = after_rebate_amount.add(orderStrategyEntity.getAfter_rebate_amount().multiply(
									new BigDecimal(merchResponse.getTotalNum() - merchResponse.getRefundNum())));
						}
					}
					if (OrderRebateMethodEnum.AFTER.getMethod() == orderStrategyEntity.getRebate_method()) {
						rebateMethod = OrderRebateMethodEnum.AFTER.getMethod();
					}
				}
			}

		}

		// 查询商品的voucher信息
		final ExecuteResult<List<VoucherEntity>> executeResult = voucherQueryEvent.getVoucherInfo(orderEntity.getTransaction_id());
		if (executeResult != null) {
			voucherQueryEvent.assembleVoucherInfos(order.getMerchs(), executeResult, orderEntity.getTransaction_id());
		}
		//		order.setMerchs(merchResponses);

		// 查询备注
		final List<RemarkResponse> remarkResponses = remarkQueryEvent.queryRemarkByOrderId(orderEntity.getOrder_id());
		order.setRemarks(remarkResponses);
		order.setRebateAmount(after_rebate_amount.doubleValue());
		order.setRebateMethod(rebateMethod);
		return order;
	}

	public QueryResult<SupplierOrderDetailResponse> querySupplierOrderDetailByPlatform(final String orderId) {
		// 查询所有订单
		final List<SupplierOrderEntity> supplierOrderEntityList = orderReadMapper.getSupplierOrdersByResellerOrderId(orderId);
		if (supplierOrderEntityList == null) {
			return null;
		}
		final Map<String, SupplierOrderDetailResponse> response = new HashMap<String, SupplierOrderDetailResponse>();
		final Set<String> orderIds = new HashSet<String>();
		final Set<String> merchIds = new HashSet<String>();
		for (final SupplierOrderEntity merch : supplierOrderEntityList) {
			orderIds.add(merch.getSupplier_order_id());
			merchIds.add(merch.getMerch_id());
		}
		//查询所有订单商品的价格，结算价，渠道，政策
		final List<OrderStrategyEntity> orderStrategyEntitys = orderStrategyReadMapper.getOrderStrategyByOrders(new ArrayList<String>(orderIds),
				new ArrayList<String>(merchIds));

		for (final SupplierOrderEntity item : supplierOrderEntityList) {
			if (!response.containsKey(item.getSupplier_order_id())) {
				final SupplierOrderDetailResponse supplierOrderDetailResponse = orderDetailResponseByPlatformConverter.convert(item, null);
				response.put(item.getSupplier_order_id(), supplierOrderDetailResponse);
			}
			final SupplierOrderDetailResponse supplierOrderDetailResponse = response.get(item.getSupplier_order_id());
			final MerchResponse merchResponse = orderDetailResponseByPlatformConverter.convertMerch(item, orderStrategyEntitys);
			supplierOrderDetailResponse.getMerchs().add(merchResponse);
			response.put(item.getSupplier_order_id(), supplierOrderDetailResponse);
		}

		final List<SupplierOrderDetailResponse> responseList = new ArrayList<SupplierOrderDetailResponse>();

		for (final Entry<String, SupplierOrderDetailResponse> entry : response.entrySet()) {
			responseList.add(entry.getValue());
		}
		final QueryResult<SupplierOrderDetailResponse> qr = new QueryResult<SupplierOrderDetailResponse>(0, 20);
		qr.setRecords(responseList);
		return qr;
	}
}

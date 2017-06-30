/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysUser;
import com.pzj.base.service.sys.IUserService;
import com.pzj.core.trade.order.engine.event.DeliveryDetailQueryEvent;
import com.pzj.core.trade.order.engine.event.MerchCleanRelationEvent;
import com.pzj.core.trade.order.engine.event.OrderExtendAttrEvent;
import com.pzj.core.trade.order.engine.event.OrderTouristEvent;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.order.engine.event.RemarkQueryEvent;
import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.read.MerchCleanReadMapper;
import com.pzj.trade.order.entity.MerchCleanRelationResponse;
import com.pzj.trade.order.entity.MerchResponse;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.entity.SupplierOrderDetailResponse;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.model.FilledModel;
import com.pzj.trade.order.model.OrderTouristOutModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderForSupplierReadMapper;
import com.pzj.trade.order.read.OrderStrategyReadMapper;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 
 * @author Administrator
 * @version $Id: SupplierOrderDetailEngine.java, v 0.1 2017年3月23日 下午4:48:30 Administrator Exp $
 */
@Component(value = "supplierOrderDetailEngine")
public class SupplierOrderDetailEngine {

	@Resource(name = "orderForSupplierReadMapper")
	private OrderForSupplierReadMapper orderForSupplierReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "orderExtendAttrEvent")
	private OrderExtendAttrEvent orderExtendAttrEvent;

	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	@Resource(name = "merchCleanRelationEvent")
	private MerchCleanRelationEvent merchCleanRelationEvent;

	@Resource(name = "deliveryDetailQueryEvent")
	private DeliveryDetailQueryEvent deliveryDetailQueryEvent;
	@Resource(name = "deliveryEngine")
	private DeliveryEngine deliveryEngine;

	@Resource(name = "merchCleanReadMapper")
	private MerchCleanReadMapper merchCleanReadMapper;

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;
	@Resource(name = "remarkQueryEvent")
	private RemarkQueryEvent remarkQueryEvent;
	@Autowired
	private IUserService userService;

	@Resource(name = "orderTouristEvent")
	private OrderTouristEvent orderTouristEvent;
	@Autowired
	private OrderStrategyReadMapper orderStrategyReadMapper;

	@Resource(name = "orderDetailBySupplierResponseConverter")
	private ObjectConverter<OrderEntity, List<MerchEntity>, SupplierOrderDetailResponse> orderDetailBySupplierResponseConverter;

	/**
	 * 
	 * @param order_id
	 * @param supplier_id
	 * @param query_type
	 * @return
	 */
	public SupplierOrderDetailResponse queryOrderDetailByOrderId(String order_id, long supplier_id, String query_type, String transactionId) {
		//查询订单信息
		OrderEntity orderEntity = orderForSupplierReadMapper.queryOrderDetailByOrderId(order_id, supplier_id, transactionId);
		if (orderEntity == null) {
			return null;
		}
		// 查询商品和政策
		final List<MerchEntity> merchs = merchReadMapper.getMerchWithStrategyByTransactionId(orderEntity.getOrder_id(), orderEntity.getTransaction_id());
		//参数转换
		final SupplierOrderDetailResponse order = orderDetailBySupplierResponseConverter.convert(orderEntity, merchs);
		// 查询填单项
		List<FilledModel> filledModelList = orderExtendAttrEvent.getOrderExtendAttr(orderEntity.getTransaction_id());
		order.setFilledModelList(filledModelList);
		// 查询快递信息
		DeliveryDetailModel deliveryDetailModel = new DeliveryDetailModel();
		if (merchs != null && merchs.size() > 0) {
			deliveryDetailModel = deliveryDetailQueryEvent.queryDeliveryDetailByTransactionId(orderEntity, merchs.get(0).getMerch_type());
		}
		order.setDelivery_info(deliveryDetailModel);

		//获取我的分销商的相关信息
		SysUser resellerUser = userService.getById(orderEntity.getReseller_id());
		if (resellerUser != null) {
			order.setReseller_mobile(resellerUser.getCorporaterMobile());
		}

		double supplierRebateAmount = 0;
		//获取导游手机
		if (order.getGuide_id() > 0) {
			SysUser guideUser = userService.getById(order.getGuide_id());
			if (guideUser != null) {
				order.setGuide_mobile(guideUser.getCorporaterMobile());
			}
		}

		// 查询商品的退款信息
		for (MerchResponse merchResponse : order.getMerchs()) {
			List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(orderEntity.getOrder_id(), merchResponse.getMerchId(),
					Integer.valueOf(orderEntity.getVersion()), orderEntity.getTransaction_id(), merchResponse.getRootMerchId());
			merchResponse.setRefundInfos(refundFlowResponses);

			List<MerchCleanRelationResponse> merchCleanRelationResponses = merchCleanRelationEvent.getMerchCleanRelation(merchResponse.getOrderId(),
					merchResponse.getMerchId());
			merchResponse.setMerchCleanRelations(merchCleanRelationResponses);
			//获取我的供应商的价格及返利信息
			List<OrderStrategyEntity> supplierOrderStrategy = orderStrategyReadMapper.getOrderStrategys(order.getOrder_id(), merchResponse.getMerchId());
			if (supplierOrderStrategy != null && supplierOrderStrategy.size() != 0) {
				if (supplierOrderStrategy.get(0).getAfter_rebate_amount() != null) {
					supplierRebateAmount = supplierRebateAmount + supplierOrderStrategy.get(0).getAfter_rebate_amount().doubleValue()
							* merchResponse.getCheckNum();
				}
			}
			//查询游客信息
			List<OrderTouristOutModel> orderTouristOutModels = orderTouristEvent.getOrderToruists(order.getTransaction_id(), merchResponse.getMerchId());
			merchResponse.setOrderTouristOutModels(orderTouristOutModels);

		}
		order.setRebateAmount(supplierRebateAmount);
		// 查询商品的voucher信息
		ExecuteResult<List<VoucherEntity>> executeResult = voucherQueryEvent.getVoucherInfo(orderEntity.getTransaction_id());
		if (executeResult != null) {
			voucherQueryEvent.assembleVoucherInfos(order.getMerchs(), executeResult, orderEntity.getTransaction_id());
		}
		// 查询备注
		List<RemarkResponse> remarkResponses = remarkQueryEvent.queryRemarkByOrderId(orderEntity.getOrder_id());
		order.setRemarks(remarkResponses);
		//获取客源地信息
		String touristSource = orderExtendAttrEvent.getTouristSourceEvent(orderEntity.getTransaction_id());
		order.setTouristSource(touristSource);
		return order;
	}
}

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

import com.pzj.core.trade.order.engine.converter.AccountOrdersArgsConverter;
import com.pzj.core.trade.order.engine.event.RefundInfoEvent;
import com.pzj.core.trade.query.entity.AccountOrdersOrdersQueryModel;
import com.pzj.trade.financeCenter.request.AccountOrdersReqModel;
import com.pzj.trade.financeCenter.response.AccountMerchRespModel;
import com.pzj.trade.financeCenter.response.AccountOrdersRespModel;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.AccountOrdersEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderForAccountReadMapper;

/**
 * 
 * @author GLG 
 * @version $Id: AccountOrderQueryEngine.java, v 0.1 2017年3月14日 下午5:57:05 Administrator Exp $
 */
@Component(value = "accountOrderQueryEngine")
public class AccountOrderQueryEngine {

	@Resource(name = "orderForAccountReadMapper")
	private OrderForAccountReadMapper orderForAccountReadMapper;
	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "accountOrdersArgsConverter")
	private AccountOrdersArgsConverter accountOrdersArgsConverter;

	@Resource(name = "refundInfoEvent")
	private RefundInfoEvent refundInfoEvent;

	public List<AccountOrdersRespModel> queryOrders(AccountOrdersReqModel reqModel) {
		final AccountOrdersOrdersQueryModel queryModel = accountOrdersArgsConverter.convert(reqModel, null);

		List<AccountOrdersEntity> orderEntities = orderForAccountReadMapper.queryOrders(queryModel);
		if (orderEntities == null || orderEntities.size() == 0) {
			return null;
		}
		Set<String> paramTransactionIds = new HashSet<String>();
		for (final AccountOrdersEntity order : orderEntities) {
			paramTransactionIds.add(order.getTransaction_id());
		}
		//根据订单查询出所有的商品
		final List<MerchEntity> merchEntitys = merchReadMapper.getMerchByTransactionIds(new ArrayList<String>(paramTransactionIds));

		List<AccountOrdersRespModel> orders = new ArrayList<AccountOrdersRespModel>();

		for (AccountOrdersEntity orderEntity : orderEntities) {
			AccountOrdersRespModel order = generateOrderOutModel(orderEntity, merchEntitys);
			orders.add(order);
		}

		return orders;
	}

	private AccountOrdersRespModel generateOrderOutModel(AccountOrdersEntity orderEntity, List<MerchEntity> merchEntitys) {
		AccountOrdersRespModel orderModel = new AccountOrdersRespModel();
		orderModel.setCreateTime(orderEntity.getCreate_time());
		orderModel.setOrderId(orderEntity.getOrder_id());
		orderModel.setResellerId(orderEntity.getReseller_id());
		orderModel.setTotalAmount(BigDecimal.valueOf(orderEntity.getTotal_amount()));
		orderModel.setResellerId(orderEntity.getReseller_id());
		orderModel.setTransactionId(orderEntity.getTransaction_id());
		orderModel.setTotalNum(orderEntity.getTotal_num());
		orderModel.setSupplierId(orderEntity.getSupplier_id());
		orderModel.setThirdPayType(orderEntity.getThird_pay_type());
		orderModel.setOrderLevel(orderEntity.getOrder_level());
		//添加商品信息
		for (final MerchEntity me : merchEntitys) {
			if (orderModel.getTransactionId().equals(me.getOrder_id())) {
				final List<AccountMerchRespModel> merchEntities = orderModel.getMerchs();
				final AccountMerchRespModel merchEntity = convertToMerchOutModel(me);
				// 查询商品的退款信息
				List<RefundFlowResponse> refundFlowResponses = refundInfoEvent.getRefundFlow(orderEntity.getOrder_id(), me.getMerch_id(),
						orderEntity.getVersion(), orderEntity.getTransaction_id(), me.getRoot_merch_id());
				merchEntity.setRefundInfos(refundFlowResponses);
				merchEntities.add(merchEntity);
			}
		}
		return orderModel;
	}

	private AccountMerchRespModel convertToMerchOutModel(MerchEntity merch) {
		AccountMerchRespModel accountMerchRespModel = new AccountMerchRespModel();
		accountMerchRespModel.setSupProductName(merch.getMerch_name());
		accountMerchRespModel.setTotalNum(merch.getTotal_num());
		accountMerchRespModel.setProductId(merch.getProduct_id());
		accountMerchRespModel.setVisitStartTime(merch.getStart_time());
		accountMerchRespModel.setVisitEndTime(merch.getExpire_time());
		accountMerchRespModel.setSkuProductName(merch.getSku_name());
		return accountMerchRespModel;
	}

}

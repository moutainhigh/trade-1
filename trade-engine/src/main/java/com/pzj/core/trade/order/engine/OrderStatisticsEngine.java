/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.trade.financeCenter.request.OrderStatisticsReqModel;
import com.pzj.trade.financeCenter.response.OrderStatisticsRespModel;
import com.pzj.trade.order.entity.OrderCountEntity;
import com.pzj.trade.order.read.OrderForResellerReadMapper;
import com.pzj.trade.order.read.OrderForSupplierReadMapper;

/**
 * 
 * @author Administrator
 * @version $Id: OrderStatisticsEngine.java, v 0.1 2017年4月12日 下午1:51:21 Administrator Exp $
 */
@Component(value = "orderStatisticsEngine")
public class OrderStatisticsEngine {
	@Resource(name = "orderForSupplierReadMapper")
	private OrderForSupplierReadMapper orderForSupplierReadMapper;
	@Resource(name = "orderForResellerReadMapper")
	private OrderForResellerReadMapper orderForResellerReadMapper;

	public OrderStatisticsRespModel queryOrderStatistics(OrderStatisticsReqModel orderStatisticsReqModel) {
		OrderStatisticsRespModel orderStatisticsRespModel = new OrderStatisticsRespModel();
		//查询出分销商所有的供应订单金额（初始分销商除外）
		OrderCountEntity supplierOrderCountEntity = orderForResellerReadMapper.queryOrderAmountReseller(orderStatisticsReqModel.getResellerId());

		BigDecimal supplierAmount = new BigDecimal(0);
		BigDecimal resellerAmount = new BigDecimal(0);
		double differencePayAmount = 0;
		//查询出分销商所有的分销订单金额（初始供应商除外）
		OrderCountEntity resellerOrderCountEntity = orderForSupplierReadMapper.queryOrderAmountSupplierId(orderStatisticsReqModel.getResellerId());
		if (supplierOrderCountEntity != null) {
			supplierAmount = supplierOrderCountEntity.getAmount();
		}
		if (resellerOrderCountEntity != null) {
			resellerAmount = resellerOrderCountEntity.getAmount();
		}
		if (supplierAmount.subtract(resellerAmount).doubleValue() > 0) {
			//供应订单金额-分销订单金额之差就是需要补差的金额
			differencePayAmount = supplierAmount.subtract(resellerAmount).doubleValue();
		}
		orderStatisticsRespModel.setDifferencePayTotalAmount(differencePayAmount);
		return orderStatisticsRespModel;
	}
}

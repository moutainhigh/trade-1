/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.query;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.export.entity.SaaSOrderExportEntity;
import com.pzj.core.trade.order.engine.converter.ResellerOrderListArgsConverter;
import com.pzj.core.trade.order.engine.converter.SupplierOrderQueryArgsConverter;
import com.pzj.core.trade.query.entity.ResellerOrdersQueryModel;
import com.pzj.core.trade.query.entity.SupplierOrdersQueryModel;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.read.OrderForResellerReadMapper;
import com.pzj.trade.order.read.OrderForSupplierReadMapper;
import com.pzj.trade.order.vo.PlatformOrderListVO;

/**
 * @author  Administrator
 * @version  $Id: QueryExportOrderEnvet.java, v 0.1 2017年2月8日 下午5:26:05 Administrator Exp $
 */
@Component
public class SaaSExportQuery {
	@Resource(name = "supplierOrderQueryArgsConverter")
	private SupplierOrderQueryArgsConverter supplierOrderQueryArgsConverter;

	@Resource(name = "resellerOrderListArgsConverter")
	private ResellerOrderListArgsConverter resellerOrderListArgsConverter;

	@Autowired
	private OrderForSupplierReadMapper orderForSupplierReadMapper;

	@Autowired
	private OrderForResellerReadMapper orderForResellerReadMapper;

	/**
	 * 
	 */
	public ArrayList<SaaSOrderExportEntity> queryOrdersForSaaS(String condition, int type) {
		if (type == 2) {
			return queryOrdersForSaaSReseller(condition);
		} else {
			return queryOrdersForSaaSSupplier(condition);
		}
	}

	private ArrayList<SaaSOrderExportEntity> queryOrdersForSaaSSupplier(String condition) {
		PlatformOrderListVO orderVO = JSONConverter.toBean(condition, PlatformOrderListVO.class);
		final SupplierOrdersQueryModel queryModel = supplierOrderQueryArgsConverter.convert(orderVO, null);
		return orderForSupplierReadMapper.queryOrderExports(queryModel);
	}

	private ArrayList<SaaSOrderExportEntity> queryOrdersForSaaSReseller(String condition) {
		ResellerOrdersReqModel orderVO = JSONConverter.toBean(condition, ResellerOrdersReqModel.class);
		final ResellerOrdersQueryModel queryModel = resellerOrderListArgsConverter.convert(orderVO, null);
		return orderForResellerReadMapper.queryOrderExports(queryModel);
	}

}
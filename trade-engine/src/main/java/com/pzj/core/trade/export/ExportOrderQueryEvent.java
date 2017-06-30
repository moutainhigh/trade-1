/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.export.entity.OrderExportExcelEntity;
import com.pzj.core.trade.order.engine.converter.PlatformOrdersQueryArgsConverter;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.order.entity.OrderListParameter;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.PlatformOrderListVO;

/**
 * @author  Administrator
 * @version  $Id: QueryExportOrderEnvet.java, v 0.1 2017年2月8日 下午5:26:05 Administrator Exp $
 */
@Service
public class ExportOrderQueryEvent {
	/**  */
	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private PlatformOrdersQueryArgsConverter platformOrdersQueryArgsConverter;

	/**
	 * 
	 */
	ArrayList<OrderExportExcelEntity> queryOrdersByCondition(String condition) {
		PlatformOrderListVO orderVO = JSONConverter.toBean(condition, PlatformOrderListVO.class);
		OrderListParameter queryModel = platformOrdersQueryArgsConverter.convert(orderVO, null);
		return orderReadMapper.getExportOrdersByCondition(queryModel);
	}

}
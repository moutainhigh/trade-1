/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderExtendAttrEntity;

/**
 *
 * @author Administrator
 * @version $Id: OrderExtendAttrMapper.java, v 0.1 2016年10月27日 下午6:18:06 Administrator Exp $
 */
public interface OrderExtendAttrReadMapper {

	/*
	 * 查询填单项
	 */
	public List<OrderExtendAttrEntity> queryOrderExtendAttrListByParam(@Param(value = "extendAttr") OrderExtendAttrEntity extendAttr);

}

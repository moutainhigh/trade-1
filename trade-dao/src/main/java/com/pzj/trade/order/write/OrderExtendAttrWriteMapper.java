/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pzj.trade.order.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderExtendAttrEntity;

/**
 *
 * @author Administrator
 * @version $Id: OrderExtendAttrWriteMapper.java, v 0.1 2016年10月27日 下午6:18:59 Administrator Exp $
 */
public interface OrderExtendAttrWriteMapper {
	/**
	 * 增加填单项
	 * 
	 * @param list
	 */
	public void insert(@Param(value = "list") List<OrderExtendAttrEntity> list);

	/**
	 * 修改填单项
	 * 
	 * @param transaction_id
	 * @param list
	 */
	public void updateByTransactionIdAndKey(@Param(value = "list") List<OrderExtendAttrEntity> list);
}

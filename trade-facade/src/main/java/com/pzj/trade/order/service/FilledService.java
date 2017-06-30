/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.order.model.OrderFillModel;
import com.pzj.trade.order.model.TouristEditInModel;

/**
 * 
 * @author Administrator
 * @version $Id: OrderUpdateService.java, v 0.1 2017年2月17日 下午3:55:25 Administrator Exp $
 */
public interface FilledService {
	/**
	 * 更新订单的填单项
	 * 
	 * @return
	 */
	public Result<Boolean> updateFilledModel(OrderFillModel order, ServiceContext context);

	/**
	 * 修改游客信息.
	 * @param inModel
	 * @return
	 */
	Result<Boolean> updateTourist(TouristEditInModel inModel);
}

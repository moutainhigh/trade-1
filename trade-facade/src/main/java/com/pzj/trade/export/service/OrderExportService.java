/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.export.model.ExportRequestModel;

/**
 * 导出订单列表申请
 * @author Administrator
 * @version $Id: OrderExportService.java, v 0.1 2017年2月6日 下午4:05:37 Administrator Exp $
 */
/**
 * 
 * @author Administrator
 * @version $Id: OrderExportService.java, v 0.1 2017年4月11日 上午10:45:02 Administrator Exp $
 */
public interface OrderExportService {
	public Result<Boolean> export(ExportRequestModel exportRequestModel, ServiceContext context);

	/**
	 * 导出Saa订单
	 * @param exportRequestModel
	 * @param context
	 * @return
	 */
	public Result<Boolean> exportForSaaS(ExportRequestModel exportRequestModel, ServiceContext context);

}

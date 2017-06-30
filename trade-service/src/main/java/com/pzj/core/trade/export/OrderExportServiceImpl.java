/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.export.model.ExportRequestModel;
import com.pzj.trade.export.service.OrderExportService;

/**
 *
 * @author Administrator
 * @version $Id: OrderExportServiceImpl.java, v 0.1 2017年2月7日 下午2:50:47 Administrator Exp $
 */
@Service(value = "orderExportService")
public class OrderExportServiceImpl implements OrderExportService {

	private final static Logger logger = LoggerFactory.getLogger(OrderExportServiceImpl.class);
	@Autowired
	private OrderExportEngine orderExportEngine;

	@Autowired
	private ExportOrderAndUploadEnvnt exportOrderAndUploadEnvnt;

	@Autowired
	private ExportOrderForSaaSSupplierEvent exportOrderForSaaSSupplierEvent;

	@Override
	public Result<Boolean> export(final ExportRequestModel exportRequestModel, final ServiceContext context) {
		logger.info("导出文件参数:" + "exportRequestModel:" + JSONConverter.toJson(exportRequestModel) + "context:"
				+ JSONConverter.toJson(context));
		if (Check.NuNObject(exportRequestModel) || Check.NuNObject(exportRequestModel.getSupplierReqModel())) {
			return new Result<Boolean>(14001, "参数为空");
		}

		final int export_id = orderExportEngine.export(exportRequestModel);
		new Thread() {
			@Override
			public void run() {
				logger.info("触发导出并上传");

				exportOrderAndUploadEnvnt.export(export_id);
			}
		}.start();

		return new Result<Boolean>();
	}

	@Override
	public Result<Boolean> exportForSaaS(ExportRequestModel exportRequestModel, ServiceContext context) {
		logger.info("SaaS导出文件参数:" + "exportRequestModel:" + JSONConverter.toJson(exportRequestModel) + "context:"
				+ JSONConverter.toJson(context));
		if (Check.NuNObject(exportRequestModel) || Check.NuNObject(exportRequestModel.getType())) {
			return new Result<Boolean>(14001, "SaaS导出文件参数为空");
		}
		if (exportRequestModel.getType() == 1 && exportRequestModel.getSupplierReqModel() == null) {
			return new Result<Boolean>(14002, "SaaS自营导出文件参数为空");
		}
		if (exportRequestModel.getType() == 2 && exportRequestModel.getResellerReqModel() == null) {
			return new Result<Boolean>(14002, "SaaS代售导出文件参数为空");
		}

		final int export_id = orderExportEngine.export(exportRequestModel);
		final int type = exportRequestModel.getType();
		new Thread() {
			@Override
			public void run() {
				logger.info("触发SaaS导出订单并上传");
				exportOrderForSaaSSupplierEvent.export(export_id, type);
			}
		}.start();

		return new Result<Boolean>();
	}

}

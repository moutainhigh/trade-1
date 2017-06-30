/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.export.entity.OrderExportEntity;
import com.pzj.core.trade.export.write.OrderExportWriteMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.trade.export.model.ExportRequestModel;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExportEngine.java, v 0.1 2017年2月7日 下午3:17:27 Administrator Exp $
 */
@Component
public class OrderExportEngine {

	private final static Logger logger = LoggerFactory.getLogger(OrderExportEngine.class);

	@Autowired
	private OrderExportWriteMapper orderExportWriteMapper;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public int export(ExportRequestModel exportRequestModel) {

		OrderExportEntity orderExportEntity = asseblyExportEntity(exportRequestModel);
		//先插入申请记录
		orderExportWriteMapper.insertOrderExport(orderExportEntity);

		logger.info("导出文件的export_id:" + orderExportEntity.getExport_id());
		final int export_id = orderExportEntity.getExport_id();
		return export_id;
	}

	/**
	 * 
	 * @param exportRequestModel
	 * @return
	 */
	private OrderExportEntity asseblyExportEntity(ExportRequestModel exportRequestModel) {
		OrderExportEntity orderExportEntity = new OrderExportEntity();
		String param = "";
		if (exportRequestModel.getType() == 2) {
			param = JSONConverter.toJson(exportRequestModel.getResellerReqModel());
		} else {
			param = JSONConverter.toJson(exportRequestModel.getSupplierReqModel());
		}
		orderExportEntity.setParam(param);
		orderExportEntity.setCreate_by(exportRequestModel.getOperator());

		String file_name = exportRequestModel.getPage_name();
		orderExportEntity.setFile_name(file_name + ".xlsx");
		return orderExportEntity;
	}

}

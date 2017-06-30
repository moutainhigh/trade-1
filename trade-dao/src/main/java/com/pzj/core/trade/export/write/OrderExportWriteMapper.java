/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.write;

import com.pzj.core.trade.export.entity.OrderExportEntity;

/**
 * 
 * @author Administrator
 * @version $Id: OrderExportReadMapper.java, v 0.1 2017年2月7日 下午3:44:31 Administrator Exp $
 */
public interface OrderExportWriteMapper {

	void insertOrderExport(OrderExportEntity orderExportEntity);

	void updateOrderExportStatus(OrderExportEntity orderExportEntity);
}

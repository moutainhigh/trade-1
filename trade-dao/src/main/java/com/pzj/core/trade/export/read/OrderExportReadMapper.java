/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.export.entity.OrderExportEntity;
import com.pzj.core.trade.export.entity.QueryOrderExportLogParam;
import com.pzj.trade.export.model.OrderExportVerifyRepModel;
import com.pzj.trade.export.model.OrderExportVerifyReqModel;

/**
 *
 * @author Administrator
 * @version $Id: OrderExportReadMapper.java, v 0.1 2017年2月7日 下午3:44:31 Administrator Exp $
 */
public interface OrderExportReadMapper {

	OrderExportEntity getExportById(@Param("id") int id);

	/**
	 * 根据参数查询满足条件的订单导出日志(按时间降序排列)
	 * @author DongChunfu
	 *
	 * @param param 分页查询参数
	 * @return
	 */
	List<OrderExportEntity> queryByParam(@Param(value = "param") QueryOrderExportLogParam param);

	/**
	 * 根据参数查询满足条件的订单导出日志总数
	 * @author DongChunfu
	 *
	 * @param param 分页查询参数
	 * @return 总条数
	 */
	int countByParam(@Param(value = "param") QueryOrderExportLogParam param);

	/**
	 * 根据ID查询导出验证
	 *
	 * @param param
	 * @return
	 */
	OrderExportVerifyRepModel queryExportVerifyById(@Param(value = "param") final OrderExportVerifyReqModel param);
}

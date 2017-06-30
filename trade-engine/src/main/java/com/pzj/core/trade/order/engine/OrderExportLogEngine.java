package com.pzj.core.trade.order.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.export.entity.OrderExportEntity;
import com.pzj.core.trade.export.entity.QueryOrderExportLogParam;
import com.pzj.core.trade.export.read.OrderExportReadMapper;

/**
 * 订单导出日志引擎
 *
 * @author DongChunfu
 * @date 2017年2月6日
 */
@Component(value = "orderExportLogEngine")
public class OrderExportLogEngine {

	@Autowired
	private OrderExportReadMapper orderExportReadMapper;

	/**根据参数查询满足条件的订单导出日志(按时间降序排列)*/
	public List<OrderExportEntity> queryPage(final QueryOrderExportLogParam qureyParam) {
		return orderExportReadMapper.queryByParam(qureyParam);
	}
}

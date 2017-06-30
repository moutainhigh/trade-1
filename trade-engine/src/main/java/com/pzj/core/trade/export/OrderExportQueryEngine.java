package com.pzj.core.trade.export;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.export.entity.OrderExportEntity;
import com.pzj.core.trade.export.entity.QueryOrderExportLogParam;
import com.pzj.core.trade.export.read.OrderExportReadMapper;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.export.model.OrderExportRepModel;
import com.pzj.trade.export.model.OrderExportVerifyRepModel;
import com.pzj.trade.export.model.OrderExportVerifyReqModel;

/**
 * 订单导出查询引擎
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
@Component(value = "orderExportQueryEngine")
public class OrderExportQueryEngine {

	@Autowired
	private OrderExportReadMapper orderExportReadMapper;

	public QueryResult<OrderExportRepModel> queryPage(final QueryOrderExportLogParam param) {

		final QueryResult<OrderExportRepModel> repQueryResult = new QueryResult<OrderExportRepModel>(
				param.getPageIndex(), param.getPageSize());

		final int total = orderExportReadMapper.countByParam(param);
		repQueryResult.setTotal(total);

		if (total == 0) {
			return repQueryResult;
		}

		final List<OrderExportEntity> queryResult = orderExportReadMapper.queryByParam(param);
		repQueryResult.setRecords(resultConvert(queryResult));
		return repQueryResult;
	}

	private List<OrderExportRepModel> resultConvert(final List<OrderExportEntity> queryResult) {
		final List<OrderExportRepModel> convertRecords = new ArrayList<OrderExportRepModel>(queryResult.size());

		for (final OrderExportEntity exportEntity : queryResult) {
			final OrderExportRepModel repModel = new OrderExportRepModel();
			repModel.setId(exportEntity.getExport_id());
			repModel.setCreateTime(exportEntity.getCreate_time());
			repModel.setExportState(exportEntity.getExport_state());
			repModel.setFileName(exportEntity.getFile_name());

			convertRecords.add(repModel);
		}

		return convertRecords;
	}

	public OrderExportVerifyRepModel queryExportVerifyById(final OrderExportVerifyReqModel param) {
		return orderExportReadMapper.queryExportVerifyById(param);
	}
}

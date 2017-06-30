package com.pzj.core.trade.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.order.engine.DeliveryEngine;
import com.pzj.framework.context.Result;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.order.model.DeliveryDetailModel;
import com.pzj.trade.order.service.DeliveryQueryService;

/**
 * Created by fanggang on 2016/11/4.
 */
@Service(value = "deliveryQueryService")
public class DeliveryQueryServiceImpl implements DeliveryQueryService {

	@Autowired
	private DeliveryEngine deliveryEngine;

	@Override
	public Result<QueryResult<DeliveryDetailModel>> queryOrderDeliveryDetail(List<String> orderIDs) {
		List<DeliveryDetailModel> deliveryDetailModels = deliveryEngine.queryOrderDelivery(orderIDs);
		Result<QueryResult<DeliveryDetailModel>> result = new Result<>();
		int total = deliveryDetailModels != null ? deliveryDetailModels.size() : 0;
		QueryResult<DeliveryDetailModel> queryResult = new QueryResult<>(1, 1);
		result.setData(queryResult);
		queryResult.setTotal(total);
		queryResult.setRecords(deliveryDetailModels);
		return result;
	}
}

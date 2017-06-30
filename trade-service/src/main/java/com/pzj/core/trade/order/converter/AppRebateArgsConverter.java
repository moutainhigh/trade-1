package com.pzj.core.trade.order.converter;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.AppRebateOrdersOrdersQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.model.AppRebateOrdersReqModel;

/**
 * app返利订单列表查询参数转换器.
 * @author GLG
 *
 */
@Component(value = "appRebateArgsConverter")
public class AppRebateArgsConverter implements ObjectConverter<AppRebateOrdersReqModel, Void, AppRebateOrdersOrdersQueryModel> {

	@Override
	public AppRebateOrdersOrdersQueryModel convert(AppRebateOrdersReqModel reqModel, Void y) {
		if (reqModel == null) {
			return null;
		}

		AppRebateOrdersOrdersQueryModel queryModel = convert(reqModel);
		//orderForTicketSellerReadMapper.queryOrdersByScrollData(queryModel, reqModel.getPageIndex(), reqModel.getPageSize());
		return queryModel;
	}

	/**
	 * 售票员订单列表查询模型转换.
	 * @param reqModel
	 * @return
	 */
	private AppRebateOrdersOrdersQueryModel convert(AppRebateOrdersReqModel reqModel) {
		AppRebateOrdersOrdersQueryModel queryModel = new AppRebateOrdersOrdersQueryModel();
		queryModel.setOrder_id(reqModel.getOrderId());
		queryModel.setRebate_form_type(reqModel.getRebateFormtype());
		queryModel.setCreate_end_time(reqModel.getCreateEndTime());
		queryModel.setCreate_start_time(reqModel.getCreateStartTime());
		queryModel.setReseller_id(reqModel.getResellerId());
		queryModel.setOrder_ids(reqModel.getOrderIds());
		queryModel.setSupName(reqModel.getSupName());
		queryModel.setOrderStatus(reqModel.getOrderStatus());
		queryModel.setOrder_status_list(reqModel.getOrderStatusList());

		return queryModel;
	}

}

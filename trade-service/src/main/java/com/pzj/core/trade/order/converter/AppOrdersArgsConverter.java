package com.pzj.core.trade.order.converter;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.AppOrdersOrdersQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.model.AppOrdersReqModel;

/**
 * app账户余额订单列表查询参数转换器.
 * @author GLG
 *
 */
@Component(value = "appOrdersArgsConverter")
public class AppOrdersArgsConverter implements ObjectConverter<AppOrdersReqModel, Void, AppOrdersOrdersQueryModel> {

	@Override
	public AppOrdersOrdersQueryModel convert(AppOrdersReqModel reqModel, Void y) {
		if (reqModel == null) {
			return null;
		}

		AppOrdersOrdersQueryModel queryModel = convert(reqModel);
		return queryModel;
	}

	/**
	 * 售票员订单列表查询模型转换.
	 * @param reqModel
	 * @return
	 */
	private AppOrdersOrdersQueryModel convert(AppOrdersReqModel reqModel) {
		AppOrdersOrdersQueryModel queryModel = new AppOrdersOrdersQueryModel();
		queryModel.setOrderIds(reqModel.getOrderIds());
		queryModel.setReseller_id(reqModel.getReseller_id());
		queryModel.setSup_name(reqModel.getSupName());
		return queryModel;
	}

}

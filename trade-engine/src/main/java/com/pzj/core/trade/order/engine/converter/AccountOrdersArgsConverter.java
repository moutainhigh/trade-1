package com.pzj.core.trade.order.engine.converter;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.query.entity.AccountOrdersOrdersQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.financeCenter.request.AccountOrdersReqModel;

/**
 * app账户余额订单列表查询参数转换器.
 * @author GLG
 *
 */
@Component(value = "accountOrdersArgsConverter")
public class AccountOrdersArgsConverter implements ObjectConverter<AccountOrdersReqModel, Void, AccountOrdersOrdersQueryModel> {

	@Override
	public AccountOrdersOrdersQueryModel convert(AccountOrdersReqModel reqModel, Void y) {
		if (reqModel == null) {
			return null;
		}

		AccountOrdersOrdersQueryModel queryModel = convert(reqModel);
		return queryModel;
	}

	/**
	 * 售票员订单列表查询模型转换.
	 * @param reqModel
	 * @return
	 */
	private AccountOrdersOrdersQueryModel convert(AccountOrdersReqModel reqModel) {
		AccountOrdersOrdersQueryModel queryModel = new AccountOrdersOrdersQueryModel();
		queryModel.setTransaction_ids(reqModel.getTransactionIds());
		queryModel.setSupplier_id(reqModel.getSupplierId());
		queryModel.setOrder_ids(reqModel.getOrderId());
		return queryModel;
	}

}

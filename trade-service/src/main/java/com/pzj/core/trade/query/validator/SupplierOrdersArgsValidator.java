package com.pzj.core.trade.query.validator;

import org.springframework.stereotype.Component;

import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.SupplierOrdersReqModel;

/**
 * 供应商订单列表请求参数验证器.
 * @author YRJ
 *
 */
@Component(value = "supplierOrdersArgsValidator")
public class SupplierOrdersArgsValidator implements ObjectConverter<SupplierOrdersReqModel, Void, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(SupplierOrdersReqModel reqModel, Void n) {
		if (Check.NuNObject(reqModel)) {
			return new Result<Boolean>(10401, "供应商订单列表查询参数错误.");
		}

		if (reqModel.getSupplier_id() <= 0) {
			return new Result<Boolean>(10401, "供应商订单列表参数[" + reqModel.getSupplier_id() + "]错误.");
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

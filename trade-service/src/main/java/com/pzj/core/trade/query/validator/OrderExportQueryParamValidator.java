package com.pzj.core.trade.query.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.export.engine.exception.OrderExportParamException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.export.model.OrderExportQueryReqModel;

/**
 * 订单导出查询请求参数校验
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
@Component(value = "orderExportQueryParamValidator")
public class OrderExportQueryParamValidator
		implements ObjectConverter<OrderExportQueryReqModel, Void, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final OrderExportQueryReqModel reqModel, final Void v) {

		if (Check.NuNObject(reqModel)) {
			throw new OrderExportParamException(10901, "订单导出查询参数不得为空.");
		}

		final String createBy = reqModel.getCreateBy();
		if (Check.NuNString(createBy)) {
			throw new OrderExportParamException(10902, "订单导出查询参数[createBy]不符合要求.");
		}

		return new Result<Boolean>(Boolean.TRUE);
	}

}

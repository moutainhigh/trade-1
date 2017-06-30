package com.pzj.core.trade.order.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.agent.engine.exception.AgentOrderException;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.model.OrderRemarkModel;

/**
 * 代下单请求参数验证器.
 * @author YRJ
 *
 */
@Component(value = "orderRemarkArgsValidator")
public class OrderRemarkArgsValidator implements ObjectConverter<OrderRemarkModel, Void, Void> {

	@Override
	public Void convert(OrderRemarkModel remark, Void arg1) {
		if (Check.NuNObject(remark)) {
			throw new AgentOrderException(10401, "修改备注请求参数为空.");
		}

		if (Check.NuNObject(remark.getOrder_id())) {
			throw new AgentOrderException(10401, "修改备注失败, 订单[" + remark.getOrder_id() + "]" + "].");
		}

		return null;
	}
}

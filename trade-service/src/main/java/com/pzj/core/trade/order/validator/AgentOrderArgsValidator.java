package com.pzj.core.trade.order.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.agent.engine.exception.AgentOrderException;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.vo.AgentOrderVO;

/**
 * 代下单请求参数验证器.
 * @author YRJ
 *
 */
@Component(value = "agentOrderArgsValidator")
public class AgentOrderArgsValidator implements ObjectConverter<AgentOrderVO, Void, Void> {

	@Override
	public Void convert(AgentOrderVO agent, Void arg1) {
		if (Check.NuNObject(agent)) {
			throw new AgentOrderException(10401, "代下单请求参数为空.");
		}

		if (Check.NuNObject(agent.getOrder_id(), agent.getAgent_order_id())) {
			throw new AgentOrderException(10401, "代下单失败, 订单[" + agent.getOrder_id() + "], 第三方订单["
					+ agent.getAgent_order_id() + "].");
		}

		return null;
	}

}

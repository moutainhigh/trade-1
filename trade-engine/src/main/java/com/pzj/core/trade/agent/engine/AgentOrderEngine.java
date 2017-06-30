package com.pzj.core.trade.agent.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.trade.order.entity.AgentOrderEntity;
import com.pzj.trade.order.entity.AgentOrderResponse;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.vo.AgentOrderVO;
import com.pzj.trade.order.write.OrderWriteMapper;

/**
 * 代下单执行引擎.
 * @author YRJ
 *
 */
@Component(value = "agentOrderEngine")
public class AgentOrderEngine {

	private final static Logger logger = LoggerFactory.getLogger(AgentOrderEngine.class);

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	/**
	 * 根据指定的订单号查询对应的第三方订单信息.
	 * @param order_id
	 * @return
	 */
	public AgentOrderResponse queryAgentOrderByOrderId(String order_id) {
		AgentOrderEntity agent = orderReadMapper.queryAgentOrderByOrderId(order_id);
		if (agent == null) {
			return null;
		}
		AgentOrderResponse response = convert(agent);
		return response;
	}

	@Transactional(value = "trade.transactionManager", propagation = Propagation.SUPPORTS, timeout = 2)
	public void doHandler(AgentOrderVO reqModel) {
		AgentOrderEntity agent = orderWriteMapper.queryAgentOrderByOrderId(reqModel.getOrder_id());
		if (agent != null) {
			logger.warn("订单[" + reqModel.getOrder_id() + "], 已存在第三方单号[" + agent.getAgent_order_id() + "], 以新第三方单号["
					+ reqModel.getAgent_order_id() + "]替换.");

			orderWriteMapper.updateAgentOrderByOrderId(reqModel.getOrder_id(), reqModel.getAgent_order_id(),
					reqModel.getOperator_id());
			return;
		}

		agent = generateAgentOrderEntity(reqModel);
		orderWriteMapper.insertAgentOrder(agent);
		orderWriteMapper.updateAgentOrderFlagByOrderId(reqModel.getOrder_id(), 3);
	}

	/**
	 * 构建第三方订单号信息.
	 * @param reqModel
	 * @return
	 */
	private AgentOrderEntity generateAgentOrderEntity(AgentOrderVO reqModel) {
		AgentOrderEntity agent = new AgentOrderEntity();
		agent.setAgent_order_id(reqModel.getAgent_order_id());
		agent.setOrder_id(reqModel.getOrder_id());
		agent.setIs_auto(reqModel.getIsAuto() ? 2 : 1);
		agent.setOperator_id(reqModel.getOperator_id());
		agent.setOperator_reason(reqModel.getOperator_reason());
		return agent;
	}

	/**
	 * 构建检索响应结果.
	 * @param agent
	 * @return
	 */
	private AgentOrderResponse convert(AgentOrderEntity agent) {
		AgentOrderResponse resp = new AgentOrderResponse();
		resp.setOrder_id(agent.getOrder_id());
		resp.setAgent_order_id(agent.getAgent_order_id());
		resp.setOperator_id(agent.getOperator_id());
		resp.setOperator_reason(agent.getOperator_reason());
		resp.setCreate_time(agent.getCreate_time());
		return resp;
	}
}

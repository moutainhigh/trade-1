package com.pzj.core.trade.ack.engine;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.ack.engine.exception.AgentOrderNotExistException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.AgentOrderEntity;
import com.pzj.voucher.entity.VoucherDockInfoEntity;

/**
 * 提供对接二次确认执行引擎.
 * @author YRJ
 *
 */
@Component(value = "dockAckEngine")
public class DockAckEngine extends OrderAckEngine {

	@Override
	protected AgentOrderEntity queryAgentOrderByOrderId(String orderId) {
		AgentOrderEntity agent = orderWriteMapper.queryAgentOrderByOrderId(orderId);
		if (Check.NuNObject(agent)) {
			throw new AgentOrderNotExistException(10401, agent.getAgent_order_id());
		}
		return agent;
	}

	/**
	 * 构建凭证和第三方关系对象.
	 * @param activationVoucherParamVO
	 * @return
	 */
	@Override
	protected VoucherDockInfoEntity generateAgentVoucher(String transactionId, AgentOrderEntity agent, String thirdCode) {
		VoucherDockInfoEntity voucherDockInfoEntity = new VoucherDockInfoEntity();
		voucherDockInfoEntity.setTransactionId(transactionId);
		voucherDockInfoEntity.setExternalOrderId(agent.getAgent_order_id());
		voucherDockInfoEntity.setAuxiliaryCode(thirdCode);
		return voucherDockInfoEntity;
	}

	//	@Override
	//	protected void confirm(List<MerchEffecTimeEntity> merchs) {
	//	}
}

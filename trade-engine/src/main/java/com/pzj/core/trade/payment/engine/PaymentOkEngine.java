package com.pzj.core.trade.payment.engine;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.context.utils.MoneyUtils;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.payment.engine.exception.PaymentPayErrorException;
import com.pzj.core.trade.payment.engine.handler.ChildOrderFreezeFlowHandler;
import com.pzj.core.trade.payment.engine.handler.FreezeFlowHandler;
import com.pzj.core.trade.sms.engine.handle.saas.SaasPaymentMakeUpHandle;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;

/**
 * 支付成功处理引擎.
 * @author YRJ
 *
 */
@Component(value = "paymentOkEngine")
public class PaymentOkEngine {

	@Resource(name = "orderWriteMapper")
	private OrderWriteMapper orderWriteMapper;

	@Resource(name = "freezeFlowHandler")
	private FreezeFlowHandler freezeFlowHandler;

	@Resource(name = "freezeFlowWriteMapper")
	private FreezeFlowWriteMapper freezeFlowWriteMapper;

	@Resource(name = "childOrderFreezeFlowHandler")
	private ChildOrderFreezeFlowHandler childOrderFreezeFlowHandler;
	@Autowired
	private SaasPaymentMakeUpHandle saasPaymentMakeUpHandle;

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 2)
	public boolean doSuccess(final OrderEntity parent, final Date payDate, final boolean isMakeUp) {
		final OrderEntity order = orderWriteMapper.getOrderListByPorderId(parent.getOrder_id());
		if (order == null) {
			return true;
		}
		if (order.getOrder_status() != OrderStatusEnum.Unpaid.getValue()) {
			throw new PaymentPayErrorException(10205, "支付失败, 订单[" + order.getOrder_id() + "]状态值[" + order.getOrder_status()
					+ "]");
		}
		//判断当前的子订单支付行为是否是补差支付行为
		if (!isMakeUp && order.getPay_way() != PayWayEnum.AFTER.getPayWay()
				&& parent.getTotal_amount() < order.getTotal_amount()) {
			orderWriteMapper.updateChildOrderMakeUpPayTime(order.getOrder_id(), payDate);
			//发送补差消息
			saasPaymentMakeUpHandle.sendSMS(order, order.getTotal_amount(),
					MoneyUtils.getMoenyNumber(order.getTotal_amount() - parent.getTotal_amount()));
			return false;
		}
		final FreezeFlowEntity flow = childOrderFreezeFlowHandler.generateFreezeFlow(parent, order);
		if (!Check.NuNObject(flow)) {
			flow.setFreeze_state(PayFlowStateEnum.PaySuccess.getKey());
			freezeFlowWriteMapper.insertFreezeFlow(flow);
		}
		orderWriteMapper.updateOrderPayState(order.getOrder_id(), null, 0, order.getPay_way(), 2, payDate);
		return doSuccess(order, payDate, false);
	}

}

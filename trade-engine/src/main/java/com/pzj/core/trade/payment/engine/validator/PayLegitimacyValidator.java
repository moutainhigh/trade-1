package com.pzj.core.trade.payment.engine.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.OrderNotExistException;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;

/**
 * 判断订单是否可付款.
 * @author YRJ
 *
 */
@Component(value = "payLegitimacyValidator")
public class PayLegitimacyValidator implements ObjectConverter<String, OrderEntity, Result<Boolean>> {

	@Override
	public Result<Boolean> convert(final String orderId, final OrderEntity order) {

		if (Check.NuNObject(order)) {
			throw new OrderNotExistException(10201, "申请支付, 订单[" + orderId + "], 不存在, 不予付款.");
		}

		final OrderStatusEnum status = OrderStatusEnum.getOrderStatus(order.getOrder_status());
		if (status != OrderStatusEnum.Unpaid) {
			throw new OrderNotExistException(10201, "申请支付, 订单[" + orderId + "], 状态为[" + status.getMsg() + "], 此时不可发起支付.");
		}

		final int payState = order.getPay_state();
		if (payState == 2) {//当订单状态为待支付, 且支付状态为2的情况, 代表主订单已付款成功, 子订单付款流程中. 此时, 也不允许发起对主订单的付款.
			throw new OrderNotExistException(10201, "订单[" + orderId + "], 状态为[" + status.getMsg() + "], 付款状态为已支付, 请等待付款完毕.");
		}
		return new Result<Boolean>(Boolean.TRUE);
	}

}
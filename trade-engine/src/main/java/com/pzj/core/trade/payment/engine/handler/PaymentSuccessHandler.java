package com.pzj.core.trade.payment.engine.handler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.manger.TradePublisherFactory;
import com.pzj.core.trade.context.model.PaymentModel;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderConfirmEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.vo.PayCallbackVO;
import com.pzj.trade.payment.write.FreezeFlowWriteMapper;
import com.pzj.voucher.common.VoucherStateEnum;
import com.pzj.voucher.entity.VoucherEntity;

@Component("paymentSuccessHandler")
public class PaymentSuccessHandler {
	@Autowired
	private OrderPayAccountHandler orderPayAccountHandler;
	@Autowired
	private TradePublisherFactory tradePublisherFactory;
	@Autowired
	private OrderWriteMapper orderWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private VoucherWriteMapper voucherWriteMapper;
	@Autowired
	private VoucherReadMapper voucherReadMapper;
	@Autowired
	private FreezeFlowWriteMapper flowWriteMapper;

	public void doHandler(final OrderEntity order, final FreezeFlowEntity flow) {
		if (!Check.NuNObject(flow)) {
			orderPayAccountHandler.doAccountConfirm(order, flow);
			flowWriteMapper.updateFreezeFlowStatus(order.getOrder_id(), flow.getSign_id(),
					PayFlowStateEnum.PaySuccess.getKey());
			final int payway = getOrderPayWay(flow, order);
			String thirdCode = null;
			int thirdPayType = 0;
			final PayCallbackVO vo = getThirdPayCallback(flow);
			if (!Check.NuNObject(vo)) {
				thirdPayType = vo.getPayType();
				thirdCode = vo.getDealId();
			}
			orderWriteMapper.updateSaleOrderPayway(order.getOrder_id(), payway, thirdPayType, thirdCode);
		}
		orderWriteMapper.updateOrderStatusByTransactionId(order.getTransaction_id(), OrderStatusEnum.AlreadyPaid.getValue());
		int merchState = MerchStateEnum.CONSUMABLE.getState();
		if (order.getNeed_confirm() == OrderConfirmEnum.CONFIRMABLE.getValue()) {
			merchState = MerchStateEnum.WAIT_CONFIRM.getState();
		}
		merchWriteMapper.updateMerchStatusByTransactionId(order.getTransaction_id(), merchState);
		activationVoucher(order);
		publishPayTask(order.getOrder_id());
	}

	private void activationVoucher(final OrderEntity saleOrder) {
		//判断订单是否是需要进行二次确认
		if (saleOrder.getNeed_confirm() == OrderConfirmEnum.UNCONFIRM.getValue()) {
			final List<VoucherEntity> vouchers = voucherReadMapper.queryVoucherByTransactionId(saleOrder.getTransaction_id());
			for (final VoucherEntity voucher : vouchers) {
				voucherWriteMapper.updateVouchConfirmStatusById(voucher.getVoucherId(), VoucherStateEnum.AVAILABLE.getValue(),
						(Date) null);
			}
		}
	}

	/**
	 * 发布订单支付成功任务
	 *
	 * @param orderId
	 *            订单ID
	 */
	private void publishPayTask(final String orderId) {
		final PaymentModel paramModel = new PaymentModel();
		paramModel.setPaymentSceneType(2);
		paramModel.setSaleOrderId(orderId);
		tradePublisherFactory.publish(paramModel);
	}

	private int getOrderPayWay(final FreezeFlowEntity flow, final OrderEntity saleOrder) {
		if (Check.NuNObject(flow)) {
			return saleOrder.getPay_way();
		}
		if (flow.getThird_amount() == 0) {
			return PayWayEnum.BALANCE.getPayWay();
		} else {
			if (flow.getBalance_amount() > 0) {
				return PayWayEnum.MIXED.getPayWay();
			}
			final PayCallbackVO vo = getThirdPayCallback(flow);
			return vo.getPayType();
		}
	}

	private PayCallbackVO getThirdPayCallback(final FreezeFlowEntity flow) {
		if (Check.NuNObject(flow) || Check.NuNString(flow.getThird_content())) {
			return null;
		}
		return JSONConverter.toBean(flow.getThird_content(), PayCallbackVO.class);
	}
}

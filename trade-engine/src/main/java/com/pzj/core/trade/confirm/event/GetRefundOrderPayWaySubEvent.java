/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.confirm.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.entity.OrderRefundEntity;
import com.pzj.trade.order.entity.OrderTransferAccountsDetailEntity;
import com.pzj.trade.order.vo.OrderPayVO;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.read.FreezeFlowReadMapper;

/**
 * 获取退款单的支付方式
 *
 * @author DongChunfu
 * @version $Id: GetRefundOrderPayWaySubEvent.java, v 0.1 2017年6月2日 上午10:20:17 DongChunfu Exp $
 */
@Component(value = "getRefundOrderPayWaySubEvent")
public class GetRefundOrderPayWaySubEvent {

	@Autowired
	private FreezeFlowReadMapper freezeFlowReadMapper;

	public List<OrderPayVO> getPayWay(final OrderTransferAccountsDetailEntity baseData,
			final OrderTransferAccountsDetailEntity childOrderBaseData, final List<OrderRefundEntity> refundInfo,
			final boolean isRootOrder, final boolean isDirectUser, final boolean isParentOrder,
			final List<OrderRefundEntity> baseDateRefundInfo) {

		List<OrderRefundEntity> childOrderRefundInfo = null;
		if (!Check.NuNObject(childOrderBaseData)) {
			childOrderRefundInfo = OrderRefundEntity.fillterByOrderId(refundInfo, childOrderBaseData.getOrderId());
		}

		final double refundOrderAmount = isRootOrder ? calculateRefundAmount(baseDateRefundInfo)
				: Check.NuNObject(childOrderBaseData) ? calculateRefundAmount(baseDateRefundInfo)
						: calculateRefundAmount(childOrderRefundInfo);

		final List<OrderPayVO> orderPays = new ArrayList<>(2);
		final int payWay = baseData.getPayWay();
		final String refundId = refundInfo.get(0).getRefundId();
		final FreezeFlowEntity refundFlow = freezeFlowReadMapper.getFreezeFlowByRefundId(refundId);

		if (Check.NuNObject(refundFlow)) {
			if (!isParentOrder) {// 后付没有微店，末级用户没有支付方式，只收钱
				final OrderPayVO afterPay = new OrderPayVO();
				afterPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
				afterPay.setOrderPayWay(baseData.getPayWay());// 现金 或 签单
				orderPays.add(afterPay);
			}
		} else {
			if (!baseData.isParentOrder()) {//判定为子订单
				//补差金额
				final double compensationAmount = compensationAmount(baseData, childOrderBaseData);
				if (compensationAmount > 0) {
					if (childOrderBaseData.isMerchantOrder() && !isRootOrder) {
						final OrderPayVO basePay = new OrderPayVO();
						basePay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
						basePay.setOrderPayWay(PayWayEnum.ACCOUNT.getPayWay());
						orderPays.add(basePay);
					} else if (childOrderBaseData.isParentOrder() && !isRootOrder) {
						final OrderPayVO basePay = new OrderPayVO();
						basePay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
						basePay.setOrderPayWay(childOrderBaseData.getPayWay());
						orderPays.add(basePay);
					} else {
						if (compensationAmount < refundOrderAmount) {
							final OrderPayVO balancePay = new OrderPayVO();
							balancePay.setOrderPayAmount(refundAmountHandle(compensationAmount));
							balancePay.setOrderPayWay(PayWayEnum.BALANCE.getPayWay());
							orderPays.add(balancePay);

							final OrderPayVO accountOrderPay = new OrderPayVO();
							accountOrderPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount - compensationAmount));
							accountOrderPay.setOrderPayWay(PayWayEnum.ACCOUNT.getPayWay());
							orderPays.add(accountOrderPay);
						} else {
							final OrderPayVO balancePay = new OrderPayVO();
							balancePay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
							balancePay.setOrderPayWay(PayWayEnum.BALANCE.getPayWay());
							orderPays.add(balancePay);
						}
					}
				} else {

					final OrderPayVO accountOrderPay = new OrderPayVO();
					accountOrderPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));

					int refundPayWay = 0;
					if (isRootOrder) {
						refundPayWay = payWay == 0 ? 7 : payWay;
					} else {
						final int childOrderPayWay = childOrderBaseData.getPayWay();

						refundPayWay = childOrderBaseData.isParentOrder() ? childOrderPayWay
								: childOrderPayWay == 0 ? 7 : childOrderPayWay;

						if (childOrderBaseData.isMerchantOrder()) {
							refundPayWay = 7;
						}

					}

					if (refundPayWay == PayWayEnum.MIXED.getPayWay()) {
						final double balanceAmount = refundFlow.getBalance_amount();
						if (balanceAmount > 0) {
							final OrderPayVO balancePay = new OrderPayVO();
							balancePay.setOrderPayAmount(refundAmountHandle(balanceAmount));
							balancePay.setOrderPayWay(PayWayEnum.BALANCE.getPayWay());
							orderPays.add(balancePay);
						}
						if ((refundOrderAmount - balanceAmount) > 0) {
							final OrderPayVO thirdPay = new OrderPayVO();
							thirdPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount - balanceAmount));
							thirdPay.setOrderPayWay(childOrderBaseData.getThirdPayType());
							orderPays.add(thirdPay);
						}
					} else {
						accountOrderPay.setOrderPayWay(refundPayWay);
						orderPays.add(accountOrderPay);
					}
				}
			} else {// 当前基础订单（baseData）是父订单
				if (baseData.isMerchantOrder()) {
					if (!isParentOrder && !isDirectUser) {// 微店次末级用户订单支付方式修改
						final OrderPayVO accountOrderPay = new OrderPayVO();
						accountOrderPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
						accountOrderPay.setOrderPayWay(PayWayEnum.ACCOUNT.getPayWay());
						orderPays.add(accountOrderPay);
					}

					if (isParentOrder) {
						final OrderPayVO accountOrderPay = new OrderPayVO();
						accountOrderPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
						accountOrderPay.setOrderPayWay(baseData.getPayWay());
						orderPays.add(accountOrderPay);
					}
				} else if (!isParentOrder) {
					if (payWay == PayWayEnum.MIXED.getPayWay()) {// 非微店订单
						if (!isParentOrder) {
							final double balanceAmount = refundFlow.getBalance_amount();
							if (balanceAmount > 0D) {
								final OrderPayVO balancePay = new OrderPayVO();
								balancePay.setOrderPayAmount(refundAmountHandle(balanceAmount));
								balancePay.setOrderPayWay(PayWayEnum.BALANCE.getPayWay());
								orderPays.add(balancePay);
							}
							if ((refundOrderAmount - balanceAmount) > 0D) {
								final OrderPayVO thirdPay = new OrderPayVO();
								thirdPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount - balanceAmount));
								thirdPay.setOrderPayWay(baseData.getThirdPayType());
								orderPays.add(thirdPay);
							}
						}
					} else {
						final OrderPayVO orderPay = new OrderPayVO();
						orderPay.setOrderPayAmount(refundAmountHandle(refundOrderAmount));
						orderPay.setOrderPayWay(baseData.getPayWay());
						orderPays.add(orderPay);
					}
				}
			}
		}
		return orderPays;
	}

	/**
	 * 退款单显示金额为负值，
	 *
	 * @param amount
	 * @return
	 */
	private double refundAmountHandle(final double amount) {
		return 0 - amount;
	}

	/**
	 * 计算子订单补差金额
	 *
	 * @param baseData 非父订单
	 * @param childOrderBaseData 相对父订单
	 * @return 补差金额
	 */
	private double compensationAmount(final OrderTransferAccountsDetailEntity baseData,
			final OrderTransferAccountsDetailEntity childOrderBaseData) {
		final Double purchaseOrderAmount = baseData.getOrderAmount();
		final Double sellOrderAmount = childOrderBaseData.getOrderAmount();
		return purchaseOrderAmount > sellOrderAmount ? purchaseOrderAmount - sellOrderAmount : 0;
	}

	/**
	 * 计算退款总金额
	 * @author DongChunfu
	 *
	 * @param orderRefunds 订单退款记录
	 * @return 退款总金额
	 */
	private double calculateRefundAmount(final List<OrderRefundEntity> orderRefunds) {
		if (Check.NuNCollections(orderRefunds)) {
			return 0D;
		}
		double totalRefundAmount = 0D;
		for (final OrderRefundEntity orderRefund : orderRefunds) {
			totalRefundAmount += orderRefund.getRefundNum() * orderRefund.getRefundPrice();
		}
		return totalRefundAmount;
	}

}

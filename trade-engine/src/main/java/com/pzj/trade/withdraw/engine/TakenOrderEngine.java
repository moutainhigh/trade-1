package com.pzj.trade.withdraw.engine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.SalePortEnum;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.payment.common.PayFlowStateEnum;
import com.pzj.trade.payment.common.ReceiveTypeEnum;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.entity.TakenOrderEntity;
import com.pzj.trade.payment.read.FreezeFlowReadMapper;
import com.pzj.trade.payment.write.CashPostalWriteMapper;
import com.pzj.trade.withdraw.common.TakenStateEnum;
import com.pzj.trade.withdraw.exception.WithdrawException;

@Component("takenOrderEngine")
public class TakenOrderEngine {
	private static final Logger logger = LoggerFactory.getLogger(TakenOrderEngine.class);

	@Autowired
	private CashPostalWriteMapper cashPostalWriteMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private FreezeFlowReadMapper freezeFlowReadMapper;

	@Transactional(value = "trade.transactionManager")
	public void insertDrawing(final String transactionId) {

		final OrderEntity order = orderWriteMapper.getOrderEntityNotLock(transactionId);

		if (SalePortEnum.isMicShop(order.getSale_port())) {
			return;
		}

		if (!thirdPartPaied(order.getPay_way())) {
			return;
		}

		final TakenOrderEntity takenOrder = cashPostalWriteMapper.getTakenOrderEntityNotlock(transactionId,
				order.getReseller_id());
		if (!Check.NuNObject(takenOrder)) {
			return;
		}

		// 查询支付流水，判断这次交易至今可提现金额
		final List<FreezeFlowEntity> payFlows = freezeFlowReadMapper.getFreezeFlowsByOrderId(transactionId);

		final double withdrawableAmount = withdrawableAmount(payFlows);
		if (withdrawableAmount > 0) {
			final TakenOrderEntity takenEntity = new TakenOrderEntity();
			takenEntity.setAccount_id(order.getPayer_id());
			takenEntity.setOrder_id(transactionId);
			takenEntity.setPay_type(order.getThird_pay_type());
			takenEntity.setCan_postal_money(withdrawableAmount);
			takenEntity.setTaken_status(TakenStateEnum.canTaken.getKey());
			logger.debug("update merches info after confirm,takenEntity:{}", takenEntity);
			cashPostalWriteMapper.insertTakenEntity(takenEntity);
		}
	}

	private boolean thirdPartPaied(final int payWay) {
		return (PayWayEnum.ALIPAY.getPayWay() == payWay || PayWayEnum.WECHART.getPayWay() == payWay
				|| PayWayEnum.MIXED.getPayWay() == payWay);
	}

	private double withdrawableAmount(final List<FreezeFlowEntity> payFlows) {

		double payAmount = 0D;
		double refundAmout = 0D;
		for (final FreezeFlowEntity payFlow : payFlows) {
			if (payFlow.getReceive_type() == ReceiveTypeEnum.Payment.getValue()) {
				if (payFlow.getFreeze_state() == PayFlowStateEnum.Paying.getKey()) {
					throw new WithdrawException(10601, "支付冻结流水记录未完成，不可进行提现计算操作");
				}
				payAmount = payFlow.getThird_amount();
			}

			if (payFlow.getReceive_type() == ReceiveTypeEnum.Refund.getValue()) {
				if (payFlow.getFreeze_state() == PayFlowStateEnum.Paying.getKey()) {
					throw new WithdrawException(10601, "退款冻结流水记录未完成，不可进行提现计算操作");
				}

				if (payFlow.getFreeze_state() == PayFlowStateEnum.PaySuccess.getKey()) {
					refundAmout += payFlow.getThird_amount();
				}

			}
		}

		return payAmount - refundAmout;
	}
}

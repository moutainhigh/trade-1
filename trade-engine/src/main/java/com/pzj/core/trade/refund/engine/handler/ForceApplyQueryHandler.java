package com.pzj.core.trade.refund.engine.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.OrderNotExistException;
import com.pzj.core.trade.refund.engine.RefundOperLogEngine;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundFlowAuditStateEnum;
import com.pzj.core.trade.refund.engine.common.RefundUserTypeEnum;
import com.pzj.core.trade.refund.engine.exception.RefundAuditResultException;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.entity.RefundOperLog;
import com.pzj.core.trade.refund.read.RefundApplyInfoReadMapper;
import com.pzj.core.trade.refund.read.RefundApplyReadMapper;
import com.pzj.core.trade.refund.read.RefundOperLogReadMapper;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.payment.entity.FreezeFlowEntity;
import com.pzj.trade.payment.read.FreezeFlowReadMapper;
import com.pzj.trade.refund.vo.ForceRefundApplyVO;
import com.pzj.trade.refund.vo.MerchVO;

/**
 * 强制退款查询处理事件
 *
 * @author DongChunfu
 * @date 2016年12月14日
 */
@Component(value = "forceApplyQueryHandler")
public class ForceApplyQueryHandler {

	@Autowired
	private RefundApplyReadMapper refundApplyReadMapper;

	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private RefundApplyInfoReadMapper refundRefuseInfoReadMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private RefundOperLogReadMapper refundOperLogReadMapper;

	@Resource(name = "refundOperLogEngine")
	private RefundOperLogEngine refundOperLogEngine;

	@Autowired
	private FreezeFlowReadMapper freezeFlowReadMapper;

	public ForceRefundApplyVO queryByRefundId(final String refundId) {

		final ForceRefundApplyVO vo = new ForceRefundApplyVO();

		final RefundApplyEntity refundApply = refundApplyReadMapper.queryRefundApplyByRefundId(refundId);
		convert(vo, refundApply, refundId);

		List<RefundFlowEntity> flows = merchRefundFlowWriteMapper.queryRefundFlowsByRefundId(refundId);
		flows = RefundFlowEntity.filterFlows(flows, RefundUserTypeEnum.resellerRefund.getKey());
		convert(vo, flows);
		final String orderId = flows.get(0).getOrder_id();

		final OrderEntity order = orderReadMapper.getOrderByIdForForceRefund(orderId);
		if (order == null) {
			throw new OrderNotExistException(RefundErrorCode.ORDER_NOT_FOUND_ERROR_CODE, "订单不存在.");
		}
		convert(vo, order);

		final String reason = refundRefuseInfoReadMapper.queryReasonByRefundId(refundId);
		vo.setRefuseReason(reason);

		return vo;
	}

	private void convert(final ForceRefundApplyVO vo, final RefundApplyEntity refundApply, final String refundId) {
		vo.setRefundId(refundApply.getRefundId());
		vo.setApplierId(refundApply.getApplierId());

		final Date date = refundOperLogReadMapper.queryApplyDate(RefundFlowAuditStateEnum.FINANCE_AUDIT.getState(), refundId);
		vo.setApplyDate(date);

		final RefundOperLog log = refundOperLogReadMapper.queryDateByStates(RefundFlowAuditStateEnum.FINANCE_AUDIT.getState(),
				RefundFlowAuditStateEnum.FINANCE_AUDIT.getState(), refundId);
		if (null != log) {
			vo.setAuditDate(log.getCreateTime());
			vo.setAuditor(log.getOperatorId());
		}

		if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.PLATFORM_AUDIT.getState()) {
			throw new RefundAuditResultException(RefundErrorCode.REFUND_STATE_AUDIT_PARTY_ERROR_CODE, "数据筛选错误");
		} else if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.FINANCE_AUDIT.getState()) {
			vo.setAuditState(1);
		} else if (refundApply.getRefundAuditState() == RefundFlowAuditStateEnum.REFUSED.getState()) {

			final int num = refundOperLogEngine.queryOpLog(RefundFlowAuditStateEnum.FINANCE_AUDIT.getState(), RefundFlowAuditStateEnum.REFUSED.getState(),
					refundId);
			if (num == 0) {
				vo.setAuditState(2);
			}
			vo.setAuditState(3);
		} else {
			vo.setAuditState(2);
		}

	}

	private void convert(final ForceRefundApplyVO vo, final List<RefundFlowEntity> refundFlows) {

		Double refundAmound = 0D;
		final List<MerchVO> merchVOs = new ArrayList<>(refundFlows.size());
		for (final RefundFlowEntity flow : refundFlows) {
			final Double refund_price = flow.getRefund_price();
			final Integer refund_num = flow.getRefund_num();
			refundAmound += refund_price * refund_num;
			final MerchVO merchVO = new MerchVO();
			final MerchEntity merch = merchReadMapper.getMerchByMerchId(flow.getMerch_id());
			merchVO.setMerchName(merch.getMerch_name());
			merchVO.setRefundNum(flow.getRefund_num());
			merchVO.setPrice(flow.getRefund_price());
			merchVO.setProduct_id(merch.getProduct_id());
			final BigDecimal bd = new BigDecimal(flow.getRefund_price() * flow.getRefund_num());
			merchVO.setTotalPrice(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

			merchVOs.add(merchVO);
		}
		vo.setMerches(merchVOs);
		vo.setRefundAmound(refundAmound);

	}

	private void convert(final ForceRefundApplyVO vo, final OrderEntity sellOrder) {
		final String orderId = sellOrder.getOrder_id();
		vo.setOrderId(orderId);

		final int thirdPayType = sellOrder.getThird_pay_type();

		if (thirdPayType == 1 || thirdPayType == 2) {
			final FreezeFlowEntity payFlow = freezeFlowReadMapper.getPayFreezeFlowByOrderId(orderId);
			final double balanceAmount = payFlow.getBalance_amount();
			if (balanceAmount == 0D) {
				vo.setPayType(thirdPayType);
			} else {
				vo.setPayType(10 + thirdPayType);
			}
		} else {
			vo.setPayType(thirdPayType);
		}

		vo.setPayAmound(sellOrder.getTotal_amount());
		vo.setPaierId(sellOrder.getPayer_id());

		if (sellOrder.getIs_dock() == 1) {
			vo.setDockAudit(1);
		}

	}

}

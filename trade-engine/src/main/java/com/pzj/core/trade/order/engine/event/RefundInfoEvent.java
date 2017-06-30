/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.read.RefundApplyReadMapper;
import com.pzj.core.trade.refund.read.RefundFlowReadMapper;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.order.entity.RefundFlowResponse;

/**
 * 
 * @author Administrator
 * @version $Id: RefundInfoEvent.java, v 0.1 2017年3月23日 下午5:48:09 Administrator Exp $
 */
@Component(value = "refundInfoEvent")
public class RefundInfoEvent {
	@Resource(name = "refundApplyReadMapper")
	private RefundApplyReadMapper refundApplyReadMapper;

	@Resource(name = "refundFlowReadMapper")
	private RefundFlowReadMapper refundFlowReadMapper;

	public List<RefundFlowResponse> getRefundFlow(final String order_id, final String merch_id, final Integer version, final String transactionId,
			final String rootMerchId) {
		List<RefundFlowEntity> refundFlowEntityList = null;
		if (version == 0) {
			refundFlowEntityList = refundFlowReadMapper.getRefundFlows(transactionId, rootMerchId, 2);
		} else {
			refundFlowEntityList = refundFlowReadMapper.getRefundFlowsByOrderId(order_id, merch_id);
		}

		return assembleRefundInfo(refundFlowEntityList);
	}

	//	public List<RefundFlowResponse> getRefundFlow(final String order_id, final String merch_id, final int version) {
	//		List<RefundFlowEntity> refundFlowEntityList = null;
	//		refundFlowEntityList = refundFlowReadMapper.getRefundFlows(order_id, merch_id, 2);
	//		return assembleRefundInfo(refundFlowEntityList);
	//	}

	public List<RefundFlowResponse> getRefundFlowByRefundId(String refundId) {
		final List<RefundFlowEntity> RefundFlowEntityList = refundFlowReadMapper.queryRefundFlowByRefundId(refundId);
		return assembleRefundInfo(RefundFlowEntityList);
	}

	private List<RefundFlowResponse> assembleRefundInfo(final List<RefundFlowEntity> RefundFlowEntityList) {
		final List<RefundFlowResponse> refundFlowResponses = new ArrayList<RefundFlowResponse>();
		for (final RefundFlowEntity refundEntity : RefundFlowEntityList) {
			final RefundFlowResponse refundResponse = new RefundFlowResponse();
			refundResponse.setRefund_id(refundEntity.getRefund_id());
			refundResponse.setOrder_id(refundEntity.getOrder_id());
			refundResponse.setMerchandise_id(refundEntity.getMerch_id());
			refundResponse.setRefund_num(refundEntity.getRefund_num());
			final RefundApplyEntity refundApplyEntity = refundApplyReadMapper.queryRefundApplyByRefundIdByFlow(refundEntity.getRefund_id());
			if (refundApplyEntity != null) {
				refundResponse.setRefund_state(refundApplyEntity.getRefundState());
				refundResponse.setRefundAuditState(refundApplyEntity.getRefundAuditState());
				refundResponse.setApplier_id(refundApplyEntity.getApplierId());
				refundResponse.setIs_force(refundApplyEntity.getIsForce());
				refundResponse.setInitParty(refundApplyEntity.getInitParty());
			}
			refundResponse.setRefund_price(refundEntity.getRefund_price());
			refundResponse.setAmount(refundEntity.getRefund_num() * refundEntity.getRefund_price());
			refundResponse.setRefundRuleType(refundEntity.getRefund_rule_type());
			refundResponse.setCreate_time(refundEntity.getCreate_time());
			refundResponse.setRefund_type(refundEntity.getRefund_type());
			refundResponse.setUpdate_time(refundEntity.getUpdate_time());
			refundFlowResponses.add(refundResponse);
		}
		return refundFlowResponses;
	}

}

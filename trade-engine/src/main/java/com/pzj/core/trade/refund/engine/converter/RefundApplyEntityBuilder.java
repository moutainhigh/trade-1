package com.pzj.core.trade.refund.engine.converter;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.IsPartyRefundEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.exception.RefundArgsException;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.refund.model.RefundApplyReqModel;

@Component
public class RefundApplyEntityBuilder {

	public RefundApplyEntity generateRefundApplyEntity(final RefundApplyReqModel reqModel, final String refundId,
			final OrderEntity order, final List<RefundApplyMerchModel> refundMerches, final Date refundApplyTime) {
		RefundApplyEntity refundApply = new RefundApplyEntity();
		refundApply.setApplierId(reqModel.getInitiatorId());
		refundApply.setTransactionId(order.getTransaction_id());
		refundApply.setApplySaleOrderStatus(order.getOrder_status());
		refundApply.setInitParty(reqModel.getInitParty());
		refundApply.setIsParty(getRefundParty(reqModel, order, refundMerches));
		refundApply.setIsForce(reqModel.getRefundType());
		refundApply.setRefundId(refundId);
		return refundApply;
	}

	private int getRefundParty(final RefundApplyReqModel reqModel, final OrderEntity order,
			final List<RefundApplyMerchModel> refundMerches) {
		int applyRefundNum = 0;
		for (RefundApplyMerchModel merch : refundMerches) {
			applyRefundNum += merch.getApplyNum();
		}
		if (order.getTotal_num() == applyRefundNum) {
			return IsPartyRefundEnum.totalRefund.getKey();
		} else if (order.getTotal_num() > applyRefundNum) {
			return IsPartyRefundEnum.partRefund.getKey();
		} else {
			throw new RefundArgsException(RefundErrorCode.REFUND_NUM_ERROR_CODE, "退款申请数量与订单可退数量异常");
		}
	}
}

package com.pzj.core.trade.refund.engine.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.RefundingEnum;
import com.pzj.core.trade.refund.engine.model.RefundApplyMerchModel;
import com.pzj.trade.merch.model.MerchRefundModel;
import com.pzj.trade.order.common.MerchStateEnum;

@Component
public class RefundMerchUpdateBuilder {

	/**
	 * 更新订单商品信息的数量与金额
	 * @param refundMerches
	 * @param isForce
	 */
	public List<MerchRefundModel> generateRefundMerchModels(final List<RefundApplyMerchModel> refundMerches, final int isForce) {
		List<MerchRefundModel> merchModels = new ArrayList<MerchRefundModel>();
		for (RefundApplyMerchModel merch : refundMerches) {
			MerchRefundModel refundModel = new MerchRefundModel();
			refundModel.setMerchId(merch.getMerchId());
			refundModel.setRefundNum(merch.getRefundNum() + merch.getApplyNum());
			refundModel.setMerchState(merch.getMerchState());
			if (merch.getMerchState() == MerchStateEnum.CONSUMED.getState()
					|| merch.getMerchState() == MerchStateEnum.FINISHED.getState()) {
				refundModel.setCheckNum(merch.getCheckedNum() - merch.getApplyNum());
			}
			refundModel.setRefundAmount(merch.getRefundAmount() + merch.getMerchCalculateModel().refundPrice(merch.getPrice())
					* merch.getApplyNum());
			refundModel.setIsRefunding(RefundingEnum.REFUNDING.getValue());
			refundModel.setRefundingNum(merch.getRefundingNum() + merch.getApplyNum());
			merchModels.add(refundModel);
		}
		return merchModels;
	}
}

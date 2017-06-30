/**
 * 
 */
package com.pzj.core.trade.refund.engine.handler;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.trade.order.entity.OrderEntity;

/**
 * 判断是否为部分退款
 *
 * @author DongChunfu
 * @date 2016年12月6日
 */
@Component(value = "judgeWetherPartRefundHandler")
public class JudgeWetherPartRefundHandler {

	public int judge(final OrderEntity orderEntity, final RefundMerchModel[] merchModels) {

		final int total_num = orderEntity.getTotal_num();
		int targetRefund_num = 0;
		for (final RefundMerchModel refundMerchModel : merchModels) {
			targetRefund_num += refundMerchModel.getApplyNum();
		}
		if (total_num == targetRefund_num) {
			return 0;
		}
		return 1;
	}
}

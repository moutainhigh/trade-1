package com.pzj.core.trade.refund.engine.validator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.model.RefundRuleLimit;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.trade.order.vo.RefundMerchandiseVO;

/**
 * 退款规则效验
 * @author kangzl
 *
 */
@Component
public class RefundApplyRuleValidator {
	/**
	 * 效验退款规则
	 */
	public void vldRefundRule(final List<RefundMerchandiseVO> refundMerches, final int isParty,
			final Map<String, RefundMerchRequiredEntity> merchCache, final Map<Long, RefundRuleLimit> refundRuleCache) {
		for (RefundMerchandiseVO vo : refundMerches) {
			RefundMerchRequiredEntity merch = merchCache.get(vo.getMerchId());
			RefundRuleLimit ruleLimit = refundRuleCache.get(merch.getProductId());
		}
	}

	private void checkCanRefund(final RefundMerchRequiredEntity merch, final RefundRuleLimit ruleLimit, final int isParty) {
		Date refundPoint = null;
		if (ruleLimit.getRefundDateType() == 1) {
			refundPoint = merch.getStartTime();
		} else {
			refundPoint = merch.getExpireTime();
		}

	}
}

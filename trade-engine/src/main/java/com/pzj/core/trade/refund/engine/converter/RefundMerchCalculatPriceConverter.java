package com.pzj.core.trade.refund.engine.converter;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.engine.common.MerchTypeEnum;
import com.pzj.core.trade.refund.engine.common.RefundErrorCode;
import com.pzj.core.trade.refund.engine.common.RefundRuleTypeEnum;
import com.pzj.core.trade.refund.engine.exception.MerchCalculatPriceException;
import com.pzj.core.trade.refund.engine.exception.RefundFeeTypeNotExitException;
import com.pzj.core.trade.refund.engine.model.RefundMerchModel;
import com.pzj.core.trade.refund.engine.model.RefundRuleLimit;

@Component(value = "refundMerchCalculatPriceConverter")
public class RefundMerchCalculatPriceConverter {

	private final static Logger logger = LoggerFactory.getLogger(RefundMerchCalculatPriceConverter.class);

	public Double convert(final RefundMerchModel merchModel, final Date refundDate, final RefundRuleLimit refundRuleLimit,
			final int refundRuleType, final int merchType) {

		if (refundRuleType == RefundRuleTypeEnum.NO_RULE.getType() || refundRuleLimit == null) {// 未执行退款规则
			if (!merchModel.getMerchId().equals(merchModel.getRootMerchId())) {
				return 0D;
			}
			return merchModel.getPrice();
		}

		if (refundRuleType == RefundRuleTypeEnum.BEFORE.getType()) {
			if (merchType == MerchTypeEnum.SELL.getType()) {// 销售商品
				return refundPrice(refundRuleLimit.getPrerefundDistributorFeetype(),
						refundRuleLimit.getPrerefundDistributorFeevalue(), merchModel.getPrice());
			}
			if (merchType == MerchTypeEnum.PURCHASE.getType()) {// 采购商品
				return refundPrice(refundRuleLimit.getPrerefundSupplierFeetype(),
						refundRuleLimit.getPrerefundSupplierFeevalue(), merchModel.getPrice());
			}
		}

		if (refundRuleType == RefundRuleTypeEnum.AFTER.getType()) {
			if (merchType == MerchTypeEnum.SELL.getType()) {// 销售商品
				return refundPrice(refundRuleLimit.getProrefundDistributorFeetype(),
						refundRuleLimit.getProrefundDistributorFeevalue(), merchModel.getPrice());
			}
			if (merchType == MerchTypeEnum.PURCHASE.getType()) {// 采购商品
				return refundPrice(refundRuleLimit.getProrefundSupplierFeetype(),
						refundRuleLimit.getProrefundSupplierFeevalue(), merchModel.getPrice());
			}
		}

		logger.error("商品运价异常.");
		throw new MerchCalculatPriceException(RefundErrorCode.MERCH_CALCULAT_PRICE_ERROR_CODE, "商品运价异常");
	}

	private Double refundPrice(final Integer feetype, final Integer feevalue, final Double price) {

		// 1,固定比例
		if (feetype == 1) {
			final BigDecimal bd = new BigDecimal(feevalue / 10000D * price);
			return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		// 2,固定金额
		if (feetype == 2) {
			return feevalue / 100D;
		}

		// 3,全额退款
		if (feetype == 3) {
			return price;
		}

		// 4,不退款
		if (feetype == 4) {
			return 0D;
		}

		throw new RefundFeeTypeNotExitException(RefundErrorCode.REFUND_FEE_TYPE_ERROR_CODE, "退款类型[" + feetype + "]不存");
	}

}

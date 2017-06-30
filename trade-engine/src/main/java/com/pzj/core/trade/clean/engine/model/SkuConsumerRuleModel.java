package com.pzj.core.trade.clean.engine.model;

import com.pzj.core.sku.common.constants.SkuProductGlobal;

/**
 * 产品的核销规则模型
 * @author kangzl
 *
 */
public class SkuConsumerRuleModel {
	//一小时的毫秒数.
	private static final long HOUR_STAMP = 60 * 60 * 1000;
	//一天的毫秒数.
	private static final long DAY_STAMP = 24 * HOUR_STAMP;

	private final long skuId;
	/**
	 * 核销凭证类型
	 */
	private final int vouType;

	private final SkuNormalConsumerModel skuNormalRuleModel;
	private final SkuOverdueConsumerModel skuOverdueRuleModel;

	public SkuConsumerRuleModel(long skuId, int vouType, SkuNormalConsumerModel skuNormalConsumerModel,
			SkuOverdueConsumerModel skuOverdueConsumerModel) {
		this.skuId = skuId;
		this.vouType = vouType;
		this.skuNormalRuleModel = skuNormalConsumerModel;
		this.skuOverdueRuleModel = skuOverdueConsumerModel;
	}

	/**
	 * 判断商品在核销时间点是否逾期
	 * @param confirmTime
	 * @param effectTime
	 * @return
	 */
	public int getMerchSettlementType(long confirmTime, long effectTime) {
		//正常清算. 产品凭证类型为联系人信息时, 无逾期清算概念, 都按照正常清算计算.
		if (this.vouType == 1 || this.vouType == 0) {
			return 1;
		}
		//逾期清算. 产品类型不为联系人信息时, 当超过游玩有效期之后, 清算类型为逾期清算.
		if (confirmTime < effectTime) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * 获取商品的清算时间点
	 * @param consumerTime
	 * @param effectTime
	 * @return
	 */
	public long getMerchCleanTime(long consumerTime, long effectTime) {
		//判断商品是否逾期
		if (this.getMerchSettlementType(consumerTime, effectTime) == 1) {
			//判断是否是核销后自动清算
			if (this.skuNormalRuleModel.getVerificationRuleType() == SkuProductGlobal.VerificationTimeTypeEnum.VERIFICATION_TYPE_CHECKIN
					.getValue()) {
				return consumerTime;
			}
			return consumerTime
					+ computeInterval(this.skuNormalRuleModel.getFixedVerificationTime(),
							this.skuNormalRuleModel.getFixedVerificationUnit());
		} else {
			return effectTime
					+ computeInterval(this.skuOverdueRuleModel.getOverdueVerificationTime(),
							this.skuOverdueRuleModel.getOverdueVerificationType());
		}
	}

	/**
	 * 获取商品的逾期清算金额
	 * @return
	 */
	public double getMerchOverdueCleanPrice(double price) {
		return convertCleanPrice(price, this.skuOverdueRuleModel.getOverdueVerificationFeevalue(),
				this.skuOverdueRuleModel.getOverdueVerificationFeetype());
	}

	public long getSkuId() {
		return skuId;
	}

	/**
	 * 计算的结算金额
	 * @param price
	 * @param feevalue
	 * @param feetype
	 * @return
	 */
	private double convertCleanPrice(double price, double feevalue, int feetype) {

		//固定比例
		if (SkuProductGlobal.VerificationMoneyTypeEnum.VERIFICATION_MONEY_TYPE_RADIO.getValue() == feetype) {
			return price * feevalue / 10000d;
			//固定金额
		} else if (SkuProductGlobal.VerificationMoneyTypeEnum.VERIFICATION_MONEY_TYPE_VALUE.getValue() == feetype) {
			return feevalue / 100d;
			//全额
		} else if (SkuProductGlobal.VerificationMoneyTypeEnum.VERIFICATION_MONEY_TYPE_ALL.getValue() == feetype) {
			return price;
			//不结算
		} else {
			return 0.0;
		}
	}

	/**
	 * 将产品设置的清算规则转换为毫秒值.
	 * @param verifyTime
	 * @param verifyUnit
	 * @return
	 */
	private long computeInterval(int verifyTime, int verifyUnit) {
		long interval = DAY_STAMP * verifyTime;
		if (verifyUnit == SkuProductGlobal.DATE_UNIT_HOUR) {
			interval = HOUR_STAMP * verifyTime;
		}
		return interval;
	}

}

package com.pzj.core.trade.clean.engine.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.clean.engine.converter.MerchNormalCleanRuleConvert;
import com.pzj.core.trade.clean.engine.converter.MerchOverdueCleanRuleConvert;
import com.pzj.core.trade.clean.engine.model.SkuConsumerRuleModel;
import com.pzj.core.trade.clean.engine.model.SkuNormalConsumerModel;
import com.pzj.core.trade.clean.engine.model.SkuOverdueConsumerModel;

@Component("merchCleanConvert")
public class MerchCleanConvert {
	@Autowired
	private MerchNormalCleanRuleConvert merchNormalCleanRuleConvert;
	@Autowired
	private MerchOverdueCleanRuleConvert merchOverdueCleanRuleConvert;

	/**
	 * 获取sku核销规则信息
	 * @param skuId
	 * @param voutype
	 * @return
	 */
	protected SkuConsumerRuleModel getSkuConsumerRule(long skuId, int vourtype) {
		SkuNormalConsumerModel normalRule = merchNormalCleanRuleConvert.convert(skuId);
		SkuOverdueConsumerModel overdueRule = merchOverdueCleanRuleConvert.convert(skuId);
		return new SkuConsumerRuleModel(skuId, vourtype, normalRule, overdueRule);
	}

	/**
	 * 将核销规则信息放入缓存中
	 * @param skuId
	 * @param skuConsumerRuleCache
	 * @param ruleModel
	 */
	public void addConsumerRuleToCach(long skuId, int vourType, Map<Long, SkuConsumerRuleModel> skuConsumerRuleCache) {
		if (skuConsumerRuleCache.get(skuId) != null) {
			return;
		}
		skuConsumerRuleCache.put(skuId, getSkuConsumerRule(skuId, vourType));
	}
}

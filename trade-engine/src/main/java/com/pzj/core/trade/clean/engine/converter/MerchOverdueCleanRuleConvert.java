package com.pzj.core.trade.clean.engine.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.OverdueVerificationEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.trade.clean.engine.exception.MerchCleanException;
import com.pzj.core.trade.clean.engine.model.SkuOverdueConsumerModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;

/**
 * 获取sku逾期核销的相关规则信息
 * @author kangzl
 *
 */
@Component
public class MerchOverdueCleanRuleConvert {

	@Autowired
	private IProductRuleService productRuleService;

	public SkuOverdueConsumerModel convert(long skuId) {
		List<ProductRuleGroupEnum> gropenums = new ArrayList<ProductRuleGroupEnum>();
		gropenums.add(ProductRuleGroupEnum.PRODUCT_RULE_OVER_DUE);
		Result<ProductRuleOutModel> result = productRuleService.getBySkuId(skuId, gropenums);
		if (!result.isOk()) {
			throw new MerchCleanException(10411, "获取sku的逾期清算规则信息失败,skuId:" + skuId);
		}
		ProductRuleOutModel ruleBaseModel = result.getData();
		if (Check.NuNObject(ruleBaseModel)) {
			throw new MerchCleanException(10411, "获取sku的逾期清算规则信息失败,skuId:" + skuId);
		}
		SkuOverdueConsumerModel repModel = new SkuOverdueConsumerModel();
		disposeNodes(ruleBaseModel, repModel);
		return repModel;
	}

	/**
	 * 迭代处理规则节点信息
	 * @param ruleBaseModel
	 * @param repModel
	 */
	private void disposeNodes(final ProductRuleOutModel ruleBaseModel, SkuOverdueConsumerModel repModel) {
		if (Check.NuNObject(ruleBaseModel)) {
			return;
		}
		if (!Check.NuNObject(ruleBaseModel.getItemRule())) {
			String ruleName = ruleBaseModel.getItemRule().getName();
			int value = ruleBaseModel.getValue();
			initModelValue(ruleName, value, repModel);
		}
		List<ProductRuleOutModel> ruleNodes = ruleBaseModel.getNodes();
		if (!Check.NuNCollections(ruleNodes)) {
			for (ProductRuleOutModel node : ruleNodes) {
				disposeNodes(node, repModel);
			}
		}
	}

	/**
	 * 将规则信息初始化至model中
	 * @param ruleName
	 * @param value
	 * @param repModel
	 */
	private void initModelValue(String ruleName, int value, SkuOverdueConsumerModel repModel) {
		if (OverdueVerificationEnum.overdueVerificationFeetype.getName().equals(ruleName)) {
			repModel.setOverdueVerificationFeetype(value);
		} else if (OverdueVerificationEnum.overdueVerificationFeevalue.getName().equals(ruleName)) {
			repModel.setOverdueVerificationFeevalue(value);
		} else if (OverdueVerificationEnum.overdueVerificationTime.getName().equals(ruleName)) {
			repModel.setOverdueVerificationTime(value);
		} else if (OverdueVerificationEnum.overdueVerificationType.getName().equals(ruleName)) {
			repModel.setOverdueVerificationType(value);
		}
	}
}

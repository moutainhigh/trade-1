package com.pzj.core.trade.clean.engine.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.product.common.global.ProductRuleGroupEnum;
import com.pzj.core.product.common.global.ProductRuleItem.VerificationRuleEnum;
import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.service.IProductRuleService;
import com.pzj.core.trade.clean.engine.exception.MerchCleanException;
import com.pzj.core.trade.clean.engine.model.SkuNormalConsumerModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;

/**
 * 商品核销清算规则
 * @author kangzl
 *
 */
@Component
public class MerchNormalCleanRuleConvert {

	@Autowired
	private IProductRuleService productRuleService;

	public SkuNormalConsumerModel convert(long skuId) {

		List<ProductRuleGroupEnum> gropenums = new ArrayList<ProductRuleGroupEnum>();
		gropenums.add(ProductRuleGroupEnum.PRODUCT_RULE_VERIFACTION_RULE);
		Result<ProductRuleOutModel> result = productRuleService.getBySkuId(skuId, gropenums);
		if (!result.isOk()) {
			throw new MerchCleanException(10410, "获取sku的核销规则信息失败,skuId:" + skuId);
		}
		ProductRuleOutModel ruleBaseModel = result.getData();
		if (Check.NuNObject(ruleBaseModel)) {
			throw new MerchCleanException(10410, "获取sku的核销规则信息失败,skuId:" + skuId);
		}

		SkuNormalConsumerModel repModel = new SkuNormalConsumerModel();
		disposeNodes(ruleBaseModel, repModel);
		return repModel;
	}

	/**
	 * 迭代处理规则节点信息
	 * @param ruleBaseModel
	 * @param repModel
	 */
	private void disposeNodes(final ProductRuleOutModel ruleBaseModel, SkuNormalConsumerModel repModel) {
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
	private void initModelValue(String ruleName, int value, SkuNormalConsumerModel repModel) {
		if (VerificationRuleEnum.verificationRuleType.getName().equals(ruleName)) {
			repModel.setVerificationRuleType(value);
		} else if (VerificationRuleEnum.fixedVerificationTime.getName().equals(ruleName)) {
			repModel.setFixedVerificationTime(value);
		} else if (VerificationRuleEnum.fixedVerificationType.getName().equals(ruleName)) {
			repModel.setFixedVerificationType(value);
		} else if (VerificationRuleEnum.fixedVerificationUnit.getName().equals(ruleName)) {
			repModel.setFixedVerificationUnit(value);
		}
	}
}

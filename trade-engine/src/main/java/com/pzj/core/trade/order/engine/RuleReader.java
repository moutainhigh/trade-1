package com.pzj.core.trade.order.engine;

import java.util.List;

import com.pzj.core.product.common.model.response.ProductRuleOutModel;
import com.pzj.core.product.common.model.response.StrategyRuleOutModel;
import com.pzj.core.product.common.model.response.StrategyRuleSkuOutModel;
import com.pzj.framework.toolkit.Check;

public final class RuleReader {

	/**
	 * 依据规则名称从政策规则定义列表中获取符合的规则.
	 * @param ruleName
	 * @param ruleModels
	 * @return
	 */
	public static ProductRuleOutModel readProdRuleInstByName(String ruleName, ProductRuleOutModel ruleRootModel) {
		if (Check.NuNObject(ruleRootModel)) {
			return null;
		}

		if(!Check.NuNObject(ruleRootModel.getItemRule()) && ruleName.equals(ruleRootModel.getItemRule().getName())){
			return ruleRootModel;
		}
		List<ProductRuleOutModel> childRuleNodels=ruleRootModel.getNodes();
		for (ProductRuleOutModel ruleModel : childRuleNodels) {
			if(!Check.NuNObject(ruleModel.getItemRule()) && ruleName.equals(ruleModel.getItemRule().getName())){
				return ruleModel;
			}
			ProductRuleOutModel rule = readProdRuleInstByName(ruleName, ruleModel);
			if (!Check.NuNObject(rule)) {
				return rule;
			}
		}
		return null;
	}

	/**
	 * 依据规则名称从政策规则定义列表中获取符合的规则.
	 * @param ruleName
	 * @param ruleModels
	 * @return
	 */
	public static StrategyRuleOutModel readStrategyRuleInstByName(String ruleName, List<StrategyRuleOutModel> ruleModels) {
		if (ruleModels == null) {
			return null;
		}

		for (StrategyRuleOutModel ruleModel : ruleModels) {
			if (ruleModel.getItemRule().getName().equals(ruleName)) {
				return ruleModel;
			}

			StrategyRuleOutModel rule = readStrategyRuleInstByName(ruleName, ruleModel.getNodes());
			if (rule != null) {
				return rule;
			}
		}
		return null;
	}

	private RuleReader() {
		throw new AssertionError("Uninstantiable class.");
	}

	/**
	 * 依据规则名称从政策规则定义列表中获取符合的规则.
	 * @param ruleName
	 * @param ruleModels
	 * @return
	 */
	public static StrategyRuleSkuOutModel readMultiStrategyRuleInstByName(String ruleName,
			List<StrategyRuleSkuOutModel> ruleModels) {
		if (ruleModels == null) {
			return null;
		}

		for (StrategyRuleSkuOutModel ruleModel : ruleModels) {
			if (ruleModel.getItemRule().getName().equals(ruleName)) {
				return ruleModel;
			}

			StrategyRuleSkuOutModel rule = readMultiStrategyRuleInstByName(ruleName, ruleModel.getNodes());
			if (rule != null) {
				return rule;
			}
		}
		return null;
	}
}

package com.pzj.core.trade.book.validator;

import java.util.List;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.SparpreisOrderRuleCEnum;
import com.pzj.trade.book.common.SparpreisOrderRuleTEnum;
import com.pzj.trade.book.model.SparpreisQOrderRuleModel;

public enum SparpreisQOrderRuleValidater {
	INTANCE;
	public void validSparpreisQOrderRule(List<SparpreisQOrderRuleModel> rules) {
		if (!Check.NuNCollections(rules)) {
			for (SparpreisQOrderRuleModel rule : rules) {
				validSparpreisQOrderRule(rule);
			}
		}
	}

	public void validSparpreisQOrderRule(SparpreisQOrderRuleModel rule) {
		if (Check.NuNObject(rule)) {
			return;
		}
		if (!Check.NuNObject(rule.getSparpreisOrderRuleC())
				&& !SparpreisOrderRuleCEnum.isValidOrderColumn(rule.getSparpreisOrderRuleC())) {
			throw new BookException("查询特价票、免票排序规则参数有误，排序列错误[" + rule.getSparpreisOrderRuleC() + "]");
		}
		if (!Check.NuNObject(rule.getSparpreisOrderRuleT())
				&& !SparpreisOrderRuleTEnum.isValidOrderType(rule.getSparpreisOrderRuleT())) {
			throw new BookException("查询特价票、免票排序规则参数有误，排序类型错误[" + rule.getSparpreisOrderRuleT() + "]");
		}

	}
}

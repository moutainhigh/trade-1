package com.pzj.core.trade.book.resolver;

import java.util.List;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.SparpreisOrderRuleCEnum;
import com.pzj.trade.book.common.SparpreisOrderRuleTEnum;
import com.pzj.trade.book.model.SparpreisQOrderRuleModel;

public enum SparpreisQOrderRuleResolver {
	INTANCE;
	public String convert(List<SparpreisQOrderRuleModel> rules) {
		if (Check.NuNCollections(rules)) {
			return null;
		}
		StringBuffer buff = new StringBuffer();
		for (SparpreisQOrderRuleModel rule : rules) {
			buff.append((convert(rule))).append(",");
		}

		buff.append("book_id ASC");

		return buff.toString();

	}

	public String convert(SparpreisQOrderRuleModel rule) {
		if (Check.NuNObject(rule)) {
			return null;
		}
		String column = "";
		String orderBy = "";

		switch (SparpreisOrderRuleCEnum.getOrderColumn(rule.getSparpreisOrderRuleC())) {
		case ORDER_BY_TRAVELDATE:
			column = "travel_date";
			break;
		case ORDER_BY_BOOKDATE:
			column = "book_date";
			break;
		}

		switch (SparpreisOrderRuleTEnum.getOrderType(rule.getSparpreisOrderRuleT())) {
		case ORDER_BY_ASC:
			orderBy = "asc";
			break;
		case ORDER_BY_DESC:
			orderBy = "desc";
			break;
		}

		return column + " " + orderBy;

	}
}

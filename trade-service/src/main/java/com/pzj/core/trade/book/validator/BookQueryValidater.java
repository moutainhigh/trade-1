package com.pzj.core.trade.book.validator;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookQueryInModel;
import com.pzj.trade.book.model.SparpreisQueryInModel;

public enum BookQueryValidater {
	INTANCE;

	public void validBook(BookQueryInModel x) {
		if (Check.NuNObject(x)) {
			throw new BookException("查询预订单参数为空.");
		}
	}

	public void validSparpreis(SparpreisQueryInModel x) {
		if (Check.NuNObject(x)) {
			throw new BookException("查询特价票、免票参数为空.");
		}

		SparpreisQOrderRuleValidater.INTANCE.validSparpreisQOrderRule(x.getOrderRule());

	}

	public void validBookId(String bookId) {
		if (Check.NuNString(bookId)) {
			throw new BookException("查询预订单/特价票、免票详情，参数为空.");
		}
	}

}

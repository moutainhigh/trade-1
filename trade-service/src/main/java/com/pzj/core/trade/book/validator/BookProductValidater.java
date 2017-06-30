package com.pzj.core.trade.book.validator;

import java.util.List;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookProductModel;

public enum BookProductValidater {
	INTANCE;

	private void validateBase(BookProductModel x) {
		if (Check.NuNObject(x)) {
			throw new BookException("创建预订单/特价票免票参数错误,产品为空.");
		}
		if (x.getProductId() <= 0) {
			throw new BookException("创建预订单/特价票免票[产品id]参数错误,产品id[" + x.getProductId() + "].");
		}
		if (x.getBuyNum() <= 0) {
			throw new BookException(
					"创建预订单/特价票免票[产品购买个数]参数错误,产品id[" + x.getProductId() + "],购买个数[" + x.getBuyNum() + "].");
		}

	}

	public void validateBook(BookProductModel x) {
		validateBase(x);
		if (x.getStrategyRelationId() <= 0) {
			throw new BookException(
					"创建预订单[产品政策关系id]参数错误,产品id[" + x.getProductId() + "],产品政策关系id[" + x.getStrategyRelationId() + "].");
		}
	}

	public void validateBooks(List<BookProductModel> books) {
		for (BookProductModel x : books) {
			validateBook(x);
		}

	}

	public void validateSparpreis(BookProductModel x) {
		validateBase(x);
		if (x.getPrice() < 0) {
			throw new BookException("创建特价票免票订单[产品价格]参数错误,产品id[" + x.getProductId() + "],产品价格[" + x.getPrice() + "].");
		}
	}

	public void validateSparpreis(List<BookProductModel> sparpreis) {

		for (BookProductModel x : sparpreis) {
			validateSparpreis(x);
		}

	}

}

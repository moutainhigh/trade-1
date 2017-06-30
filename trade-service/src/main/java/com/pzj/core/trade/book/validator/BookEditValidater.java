package com.pzj.core.trade.book.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.core.trade.book.utils.BookUtil;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookEditModel;

@Component(value = "bookEditValidater")
public class BookEditValidater implements ObjectConverter<BookEditModel, Void, Boolean> {

	@Override
	public Boolean convert(BookEditModel x, Void y) {

		BaseBookValidater.INTANCE.valid(x);

		if (Check.NuNString(x.getBookId())) {
			throw new BookException("编辑预订单参数错误, 预订单号[" + x.getBookId() + "].");
		}

		//验证产品的政策，游玩时间等是否有效
		if (BookUtil.INTANCE.isBookType(x.getBookType())) {
			BookProductValidater.INTANCE.validateBooks(x.getProducts());
		} else {
			BookProductValidater.INTANCE.validateSparpreis(x.getProducts());
		}

		return true;

	}

}

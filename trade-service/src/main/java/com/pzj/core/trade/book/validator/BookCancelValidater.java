package com.pzj.core.trade.book.validator;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.engine.exception.BookException;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookCancelModel;

@Component("bookCancelValidater")
public class BookCancelValidater implements ObjectConverter<BookCancelModel, Void, Boolean> {

	@Override
	public Boolean convert(BookCancelModel x, Void y) {
		if (Check.NuNObject(x)) {
			throw new BookException("取消预订单/特价票、免票参数错误.");
		}

		if (Check.NuNString(x.getBookId())) {
			throw new BookException("取消预订单/特价票、免票订单参数错误, 预订单/特价票、免票订单号[" + x.getBookId() + "].");
		}
		if (x.getOperatorId() <= 0) {
			throw new BookException("取消预订单/特价票、免票订单参数错误, 操作人[" + x.getOperatorId() + "].");
		}

		return true;
	}

}

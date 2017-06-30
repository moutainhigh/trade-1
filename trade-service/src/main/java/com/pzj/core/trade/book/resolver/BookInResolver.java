package com.pzj.core.trade.book.resolver;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookCreateEModel;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.utils.BookUtil;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookStatusEnum;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.order.common.OrderIDGenerater;
import com.pzj.trade.order.common.SalePortEnum;

@Component(value = "bookInResolver")
public class BookInResolver extends BaseBookInResolver<BookInModel, Void> {

	private Logger logger = LoggerFactory.getLogger(BookInResolver.class);

	@Autowired
	private OrderIDGenerater orderIDGenerater;

	@Override
	public BookCreateEModel convert(final BookInModel x, Void y) {
		return super.convert(x, y);
	}

	@Override
	protected void copyBook(BookInModel source, BookEntity target) {
		super.copyBaseBook(source, target);

		String bookId = orderIDGenerater.generateOrderId(SalePortEnum.OFFLINE_WINDOW.getValue(), source.getBookType(),
				source.getOperatorId());
		target.setBookId(bookId);
		target.setTransactionId(source.getSrcBookId());
		if (Check.NuNString(target.getTransactionId())) {
			target.setTransactionId(bookId);
		}
		if (BookUtil.INTANCE.isSparpreisType(source.getBookType())) {
			target.setBookStatus(BookStatusEnum.AUDITING.getStatus());
		} else {
			target.setBookStatus(BookStatusEnum.BOOKING.getStatus());
		}
		target.setBookDate(new Date().getTime());
	}

	@Override
	protected void copyJson(BookInModel source, BookEntity target, BookJsonEModel json) {
		json.setCheckinPoints(source.getCheckinPoints());
		super.copyBaseJson(source, target, json);
	}

}

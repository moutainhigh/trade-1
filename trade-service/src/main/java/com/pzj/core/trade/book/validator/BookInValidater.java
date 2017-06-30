package com.pzj.core.trade.book.validator;

import org.springframework.stereotype.Component;

import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.book.model.BookInModel;

@Component(value = "bookInValidater")
public class BookInValidater implements ObjectConverter<BookInModel, Void, Boolean> {

	@Override
	public Boolean convert(BookInModel x, Void y) {

		BaseBookValidater.INTANCE.valid(x);

		return true;

	}

}

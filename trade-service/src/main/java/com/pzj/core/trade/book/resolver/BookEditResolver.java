package com.pzj.core.trade.book.resolver;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookCreateEModel;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.book.model.BookEditModel;

@Component(value = "bookEditResolver")
public class BookEditResolver extends BaseBookInResolver<BookEditModel, Void> {

	private Logger logger = LoggerFactory.getLogger(BookEditResolver.class);

	@Resource(name = "bookOrderValidater")
	private ObjectConverter<BookCreateEModel, Boolean, Boolean> bookOrderValidater;

	@Override
	public BookCreateEModel convert(final BookEditModel x, Void y) {

		return super.convert(x, y);

	}

	@Override
	public void copyBook(final BookEditModel source, final BookEntity target) {

		copyBaseBook(source, target);

		target.setUpdateTime(new Date().getTime());

		target.setBookId(source.getBookId());

	}

	@Override
	protected void copyJson(BookEditModel source, BookEntity target, BookJsonEModel json) {

		super.copyBaseJson(source, target, json);

	}

}

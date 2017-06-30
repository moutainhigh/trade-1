package com.pzj.core.trade.book.engine.calculate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookStockChildModel;
import com.pzj.core.trade.order.engine.SeatsAssemble;
import com.pzj.core.trade.order.engine.model.SeatsModel;
import com.pzj.framework.toolkit.Check;

@Component
public class BookCreateCalculate extends BaseBookStockCalculate {

	@Autowired
	private SeatsAssemble seatsAssemble;

	public void occupyStock(BookEntity newBook) {
		super.occupyStock(newBook, null, null);
	}

	@Override
	protected int generateStockNum(BookStockChildModel stock, SeatsModel seats) {
		if (Check.NuNObject(stock)) {
			return 0;
		}
		return stock.getNewNum();
	}

	@Override
	protected SeatsModel generateSeat(BookStockChildModel stock) {
		if (Check.NuNObject(stock)) {
			return null;
		}
		return seatsAssemble.computeSeatsOnCreateBook(stock.getNewSeats());
	}

}

package com.pzj.core.trade.book.engine.calculate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookStockChildModel;
import com.pzj.core.trade.order.engine.SeatsAssemble;
import com.pzj.core.trade.order.engine.model.SeatsModel;
import com.pzj.framework.toolkit.Check;

@Component
public class PreOrderCreateCalculate extends BaseBookStockCalculate {

	@Autowired
	private SeatsAssemble seatsAssemble;

	public void occupyStock(BookEntity newBook, BookEntity oldBook) {
		super.occupyStock(newBook, oldBook, null);
	}

	@Override
	protected int generateStockNum(BookStockChildModel stock, SeatsModel seats) {
		int result = 0;

		if (Check.NuNObject(stock)) {
			return result;
		}
		if (stock.getIsNeedSeat()) {
			result = haveSeatStockNum(stock, seats);
		} else {
			result = noSeatStockNum(stock);
		}

		return result;
	}

	private int haveSeatStockNum(BookStockChildModel stock, SeatsModel seats) {
		if (stock.getOldNum() != 0) {
			return stock.getOldNum() + seats.getNeedSeats().size();
		}
		return stock.getNewNum();
	}

	private int noSeatStockNum(BookStockChildModel stock) {
		return stock.getOldNum() > stock.getNewNum() ? stock.getOldNum() : stock.getNewNum();
	}

	@Override
	protected SeatsModel generateSeat(BookStockChildModel stock) {
		if (Check.NuNObject(stock)) {
			return null;
		}
		return seatsAssemble.computeSeatsOnCreatePreOrder(stock.getOldSeats(), stock.getNewSeats());
	}

}

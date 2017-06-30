package com.pzj.core.trade.book.engine.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pzj.core.product.model.OccupyStockReqModel;
import com.pzj.core.product.model.OccupyStockReqsModel;
import com.pzj.core.product.model.OccupyStockResponse;
import com.pzj.core.product.service.SeatRecordService;
import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.converter.BookStockConverter;
import com.pzj.core.trade.book.engine.exception.BookStockException;
import com.pzj.core.trade.book.engine.model.BookStockChildModel;
import com.pzj.core.trade.book.engine.model.BookStockModel;
import com.pzj.core.trade.order.engine.model.SeatsModel;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;

public abstract class BaseBookStockCalculate {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeatRecordService seatRecordService;

	protected abstract int generateStockNum(BookStockChildModel stock, SeatsModel seats);

	protected abstract SeatsModel generateSeat(BookStockChildModel stock);

	protected void occupyStock(BookEntity newBook, BookEntity oldBook, BookEntity preBook) {
		BookStockModel stock = BookStockConverter.INTANCE.generateBookStock(newBook, oldBook, preBook);
		if (Check.NuNObject(stock) || Check.NuNMap(stock.getBookMap())) {
			return;
		}
		OccupyStockReqsModel stocks = generateStocks(stock);
		if (Check.NuNCollections(stocks.getOccupyStockReqs())) {
			return;
		}
		callStockService(stocks);

	}

	protected void callStockService(OccupyStockReqsModel stocks) {
		String stockJson = JSONConverter.toJson(stocks);
		logger.info("调用库存接口[SeatRecordService.occupyStock],入参:[{}]", stockJson);
		Result<OccupyStockResponse> result = seatRecordService.occupyStock(stocks);
		if (!result.isOk()) {
			logger.error("预约单调用库存接口失败,入参:{},错误信息:[错误码:{},错误信息:{},错误实体信息:{}]", stockJson, result.getErrorCode(),
					result.getErrorMsg(), JSONConverter.toJson(result.getData()));
			throw new BookStockException(result.getData(), result.getErrorCode(), result.getErrorMsg());
		}
	}

	private OccupyStockReqsModel generateStocks(BookStockModel model) {
		if (Check.NuNObject(model) || Check.NuNObject(model)) {
			return null;
		}
		OccupyStockReqsModel result = new OccupyStockReqsModel();
		List<OccupyStockReqModel> occupys = new ArrayList<OccupyStockReqModel>();
		result.setOccupyStockReqs(occupys);

		if (!Check.NuNMap(model.getBookMap())) {
			for (Map.Entry<Long, BookStockChildModel> entry : model.getBookMap().entrySet()) {
				BookStockChildModel stock = entry.getValue();
				SeatsModel seat = generateSeat(stock);
				int occupyNum = generateStockNum(stock, seat);
				OccupyStockReqModel occupy = BookStockConverter.INTANCE.generateStock(stock, seat,
						model.getTransationId(), model.getTravelDate(), occupyNum, model.getOperator(),
						model.getSpuId());
				if (!Check.NuNObject(occupy)) {
					occupys.add(occupy);
				}
			}
		}
		return result;

	}

}

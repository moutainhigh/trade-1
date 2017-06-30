package com.pzj.core.trade.book.engine.converter;

import java.util.Date;
import java.util.Map;

import com.pzj.core.common.utils.StockGlobalDict;
import com.pzj.core.product.model.OccupyStockReqModel;
import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.engine.model.BookStockChildModel;
import com.pzj.core.trade.book.engine.model.BookStockModel;
import com.pzj.core.trade.book.engine.model.ProductJsonEModel;
import com.pzj.core.trade.order.engine.model.SeatsModel;
import com.pzj.framework.toolkit.Check;

public enum BookStockConverter {
	INTANCE;
	public BookStockModel generateBookStock(BookEntity newEntity, BookEntity oldEntity, BookEntity preEntity) {
		if (Check.NuNObject(newEntity)) {
			return null;
		}
		BookStockModel result = new BookStockModel();
		result.setOperator(newEntity.getOperatorId());
		result.setTransationId(newEntity.getTransactionId());
		if (!Check.NuNObject(newEntity.getTravelDate())) {
			result.setTravelDate(newEntity.getTravelDate());
		}
		result.setSpuId(newEntity.getSpuId());
		generateStockChild(result, BookJsonConverter.INTANCE.getBookJson(newEntity),
				BookJsonConverter.INTANCE.getBookJson(oldEntity), BookJsonConverter.INTANCE.getBookJson(preEntity));

		return result;

	}

	private void generateStockChild(BookStockModel result, BookJsonEModel newJson, BookJsonEModel oldJson,
			BookJsonEModel preJson) {

		Map<Long, ProductJsonEModel> newBookMap = BookJsonConverter.INTANCE.getProductMap(newJson);
		if (!Check.NuNMap(newBookMap)) {
			setStockChildMap(result.getBookMap(), newBookMap, "new");
		}

		Map<Long, ProductJsonEModel> oldBookMap = BookJsonConverter.INTANCE.getProductMap(oldJson);
		if (!Check.NuNMap(oldBookMap)) {
			setStockChildMap(result.getBookMap(), oldBookMap, "old");
		}

		Map<Long, ProductJsonEModel> preBookMap = BookJsonConverter.INTANCE.getProductMap(preJson);
		if (!Check.NuNMap(preBookMap)) {
			setStockChildMap(result.getBookMap(), preBookMap, "pre");
		}

	}

	private void setStockChildMap(Map<Long, BookStockChildModel> childMap, Map<Long, ProductJsonEModel> bookMap,
			String type) {
		for (Map.Entry<Long, ProductJsonEModel> book : bookMap.entrySet()) {
			BookStockChildModel child = childMap.get(book.getKey());
			if (Check.NuNObject(book.getValue()) || book.getValue().noHaveStockRule()) {
				continue;
			}
			if (child == null) {
				child = new BookStockChildModel();
				childMap.put(book.getKey(), child);
			}
			setStockChildInfo(child, book.getValue());
			setStockChild(child, book.getValue(), type);

		}
	}

	private void setStockChildInfo(BookStockChildModel child, ProductJsonEModel pBook) {
		child.setProductId(pBook.getProductId());
		child.setScreeningsId(pBook.getScreeningsId());
		child.setAreaId(pBook.getAreaId());
		child.setStockRuleId(pBook.getStockRuleId());

	}

	private void setStockChild(BookStockChildModel child, ProductJsonEModel pBook, String type) {
		switch (type) {
		case "new":
			child.setIsNeedSeat(!Check.NuNCollections(pBook.getSeats()));
			child.setNewInfo(pBook.getSeats(), pBook.getBuyNum());
			return;
		case "old":
			child.setOldInfo(pBook.getSeats(), pBook.getBuyNum());
			return;
		case "pre":
			child.setPreInfo(pBook.getSeats(), pBook.getBuyNum());
			return;
		}
	}

	public OccupyStockReqModel generateStock(BookStockChildModel child, SeatsModel seatModel, String transationId,
			long travelDate, int occupyNum, long operator, long spuId) {
		OccupyStockReqModel stock = new OccupyStockReqModel();
		stock.setAreaId(child.getAreaId());
		stock.setTransactionId(transationId);
		stock.setOccupyType(StockGlobalDict.ooccupyType.preemptionSeat);

		stock.setProductId(child.getProductId());
		stock.setScreeningsId(child.getScreeningsId());
		stock.setStockRuleId(child.getStockRuleId());
		stock.setTravelDate(new Date(travelDate));
		stock.setOperator(operator);
		if (!Check.NuNObject(seatModel)) {
			stock.setOccupySeatIds(seatModel.getNeedSeats());
			stock.setReleaseSeatIds(seatModel.getReleaseSeats());
		}
		stock.setOutQuantity(occupyNum);
		stock.setSpuId(spuId);

		return stock;

	}

	/**
	 * 判断新旧单据的游玩时间是否一致，是否需要释放之前单据的库存
	 * 
	 * @param newBook
	 * @param oldBook
	 * @return
	 */
	public boolean isNeedStockFDiffTDate(BookEntity newBook, BookEntity oldBook) {
		if (Check.NuNObject(oldBook.getNeedStockEntity())) {
			return false;
		}
		if (Check.NuNObject(newBook.getTravelDate())) {
			return false;
		}
		if (Check.NuNObject(oldBook.getTravelDate())
				&& newBook.getTravelDate().longValue() != oldBook.getTravelDate()) {
			return true;
		}
		return false;

	}
}

package com.pzj.core.trade.book.resolver;

import com.pzj.core.product.model.OccupyStockResponse;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.BookResponse;
import com.pzj.trade.book.model.StockEInfoModel;
import com.pzj.trade.order.entity.OrderResponse;

public enum StockResponseResolver {
	INTANCE;
	public BookResponse convertBookR(OccupyStockResponse stockMsg) {
		if (Check.NuNObject(stockMsg)) {
			return null;
		}
		return new BookResponse(convertInfo(stockMsg));
	}

	public OrderResponse convertOrderR(OccupyStockResponse stockMsg) {
		if (Check.NuNObject(stockMsg)) {
			return null;
		}
		return new OrderResponse(convertInfo(stockMsg));
	}

	private StockEInfoModel convertInfo(OccupyStockResponse source) {
		StockEInfoModel result = new StockEInfoModel();
		result.setProductId(source.getProductId());
		result.setRemainNum(source.getRemainNum());
		result.setStockId(source.getStockId());
		result.setStockName(source.getStockName());
		result.setStockRuleId(source.getStockRuleId());
		result.setStockType(source.getStockType());
		result.setTravelDate(source.getTravelDate());
		return result;

	}

}

package com.pzj.core.trade.order.engine.exception;

import com.pzj.core.product.model.OccupyStockResponse;
import com.pzj.core.trade.exception.TradeException;

public class OrderStockException extends TradeException {
	final private OccupyStockResponse stockException;

	public OrderStockException(final OccupyStockResponse stockE, final int errCode, final String message) {
		super(errCode, message);
		this.stockException = stockE;

	}

	/**  */
	private static final long serialVersionUID = 4143268529492998764L;

	public OccupyStockResponse getStockException() {
		return stockException;
	}
}

package com.pzj.trade.book.model;

import java.io.Serializable;

public final class BookResponse implements Serializable {

	/**  */
	private static final long serialVersionUID = 1320951892175834383L;
	/**
	* @apiDefine BookResponse  BookResponse 预订单创建编辑返回结果
	* 
	* @apiParam (BookResponse) {long} bookId  预约单id/特价票、免票id 
	* 
	* @apiParam (BookResponse) {StockEInfoModel} stockException 库存错误信息
	* 
	*/

	private final String bookId;

	private final StockEInfoModel exceptionMsg;

	public final String getBookId() {
		return bookId;
	}

	public BookResponse(final String bookId) {
		this.bookId = bookId;
		this.exceptionMsg = null;
	}

	public BookResponse(final StockEInfoModel exceptionMsg) {
		this.bookId = null;
		this.exceptionMsg = exceptionMsg;
	}

	@Override
	public String toString() {
		return "bookId : " + this.bookId;

	}

	public StockEInfoModel getExceptionMsg() {
		return exceptionMsg;
	}
}

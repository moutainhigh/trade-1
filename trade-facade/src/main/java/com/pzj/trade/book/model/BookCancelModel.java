package com.pzj.trade.book.model;

import java.io.Serializable;

public class BookCancelModel implements Serializable {

	/**
	 * @apiDefine BookCancelModel  BookCancelModel 预订单/特价票、免票取消入参
	 * 
	 * @apiParam (BookCancelModel) {long} bookId  预订单id  必填
	 * @apiParam (BookCancelModel) {long} operatorId 操作人id
	 */

	/**  */
	private static final long serialVersionUID = -1137186462791140369L;
	/**
	 * 预订单ID.
	 */
	private String bookId;

	/** 操作人id*/
	private long operatorId;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

}

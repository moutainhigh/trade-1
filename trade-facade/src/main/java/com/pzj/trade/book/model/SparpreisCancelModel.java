package com.pzj.trade.book.model;

import java.io.Serializable;

public class SparpreisCancelModel implements Serializable {

	/**
	 * @apiDefine SparpreisCancelModel  SparpreisCancelModel 特价票、免票取消入参
	 * 
	 * @apiParam (SparpreisCancelModel) {long} bookId  免票、特价票ID  必填
	 * @apiParam (SparpreisCancelModel) {long} operatorId 操作人id
	 */

	/**  */
	private static final long serialVersionUID = 6291296971733973039L;
	/**
	 * 免票、特价票ID.
	 */
	private long bookId;

	/** 操作人id*/
	private long operatorId;

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

}

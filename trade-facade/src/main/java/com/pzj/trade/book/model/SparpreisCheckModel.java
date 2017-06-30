package com.pzj.trade.book.model;

import java.io.Serializable;

public class SparpreisCheckModel implements Serializable {

	/**
	* @apiDefine SparpreisCheckModel  SparpreisCheckModel 特价票、免票审核入参
	* 
	* @apiParam (SparpreisCheckModel) {long} bookId  免票特价票订单Id  必填
	* @apiParam (SparpreisCheckModel) {long} operatorId  操作人id  必填
	* @apiParam (SparpreisCheckModel) {String} reason 审核原因
	* 
	*/

	/**  */
	private static final long serialVersionUID = -8720234788012555942L;

	/**免票特价票订单Id*/
	private String bookId;

	/** 操作人id*/
	private long operatorId;

	/** 审核人*/
	private String auditor;

	/**审核原因*/
	private String reason;//审核原因, 不支持表情符号.

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

}

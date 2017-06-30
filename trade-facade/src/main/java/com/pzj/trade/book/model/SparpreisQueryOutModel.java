package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.Date;

public class SparpreisQueryOutModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -8894769720633968045L;

	/**
	 * @apiDefine SparpreisQueryOutModel  SparpreisQueryOutModel 特价票、免票分页查询出参
	 * 
	 * @apiParam (SparpreisQueryOutModel) {long} bookId  订单Id 
	 * @apiParam (SparpreisQueryOutModel) {long} resellerId  客户Id 
	 * @apiParam (SparpreisQueryOutModel) {long} operatorId 操作人id  必填
	 * @apiParam (SparpreisQueryOutModel) {int}  bookType 业务类型
	 * @apiParam (SparpreisQueryOutModel) {Date} travelDate  游玩时间
	 * @apiParam (SparpreisQueryOutModel) {Date} bookDate  预定时间
	 * @apiParam (SparpreisQueryOutModel) {long} bookState  单据状态
	 * @apiParam (SparpreisQueryOutModel) {int}  totalAmount 单据价格 
	 * @apiParam (SparpreisQueryOutModel) {long}  spuId spu产品id 
	 * 
	 */

	private String bookId;

	private long resellerId;

	private long operateId;

	/** 免票：2  特价票：3 */
	private int bookType;

	private Date bookDate;

	private Date travelDate;

	/** 待审核：1 待出票：2 已出票：3 待取消：0 已拒绝：4*/
	private int bookState;

	private int totalAmount;

	private long spuId;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public long getSpuId() {
		return spuId;
	}

	public void setSpuId(long spuId) {
		this.spuId = spuId;
	}

	public int getBookType() {
		return bookType;
	}

	public void setBookType(int bookType) {
		this.bookType = bookType;
	}

	public int getBookState() {
		return bookState;
	}

	public void setBookState(int bookState) {
		this.bookState = bookState;
	}

	public long getOperateId() {
		return operateId;
	}

	public void setOperateId(long operateId) {
		this.operateId = operateId;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

}

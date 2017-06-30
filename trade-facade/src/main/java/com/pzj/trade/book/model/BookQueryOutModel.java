package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.Date;

import com.pzj.trade.book.common.BookTypeEnum;

public class BookQueryOutModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 5278039010129037360L;

	/**
	 * @apiDefine BookQueryOutModel  BookQueryOutModel 预订单分页查询出参
	 * 
	 * @apiParam (BookQueryOutModel) {long} bookId  订单Id 
	 * @apiParam (BookQueryOutModel) {long} resellerId  客户Id 
	 * @apiParam (BookQueryOutModel) {long} operatorId 操作人id  必填 
	 * @apiParam (BookQueryOutModel) {Date} travelDate  游玩时间
	 * @apiParam (BookQueryOutModel) {int}  bookType 业务类型
	 * @apiParam (BookQueryOutModel) {Date} bookDate  预定时间
	 * @apiParam (BookQueryOutModel) {long} bookState  单据状态
	 * @apiParam (BookQueryOutModel) {int}  totalNum 单据总数量
	 * @apiParam (BookQueryOutModel) {int}  totalAmount 单据价格 
	 * @apiParam (BookQueryOutModel) {long}  spuId spu产品Id
	 * 
	 */

	private String bookId;

	private long resellerId;

	private long operateId;

	private Date travelDate;

	private Date bookDate;

	/** 待审核：1 待出票：2 已出票：3 待取消：0 已拒绝：4*/
	private int bookState;

	private int totalAmount;

	private int totalNum;

	private long spuId;

	/** {@linkplain BookTypeEnum}*/
	private int bookType;

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

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getBookState() {
		return bookState;
	}

	public void setBookState(int bookState) {
		this.bookState = bookState;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
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

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public int getBookType() {
		return bookType;
	}

	public void setBookType(int bookType) {
		this.bookType = bookType;
	}

}

/*
 * TBook.java
 
 * www.piaozhijia.coim
 */
package com.pzj.core.trade.book.dao.entity;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookStatusEnum;

/**
 * vo.区域
 * @author 票之家
 */

public class BookEntity {
	/** 预订单id */
	private String bookId;
	/** 原始预约单id */
	private String srcBookId;
	/** 交易ID，原始预约单和最新预约单所用的整个交易ID */
	private String transactionId;
	/** 操作人id */
	private Long operatorId;
	/** 预定/免票特价票分销商Id */
	private Long resellerId;

	/** saas供应商id*/
	private Long supplierId;

	/** 游玩时间 */
	private Long travelDate;
	/** 状态（1：待出票 2：已出票 0：已取消 3：待审核 4：已拒绝） */
	private Integer bookStatus;
	/** 类型（0:普通订单 1：预约单 2：特价票 3：免票） */
	private Integer bookType;
	/** 创建时间 */
	private Long bookDate;
	/** 单据总金额 */
	private Integer totalAmount;
	/** 单据总数量 */
	private Integer totalNum;
	/** 提货码 */
	private String deliveryCode;

	/** 产品信息 */
	private String bookDetail;

	/** 产品集合，以","分隔*/
	private Long spuId;

	/** 最新更新时间*/
	private Long updateTime;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getSrcBookId() {
		return srcBookId;
	}

	public void setSrcBookId(String srcBookId) {
		this.srcBookId = srcBookId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/** 设置 操作人id */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/** 得到 操作人id */
	public Long getOperatorId() {
		return operatorId;
	}

	public Long getResellerId() {
		return resellerId;
	}

	public void setResellerId(Long resellerId) {
		this.resellerId = resellerId;
	}

	/** 设置 游玩时间 */
	public void setTravelDate(Long travelDate) {
		this.travelDate = travelDate;
	}

	/** 得到 游玩时间 */
	public Long getTravelDate() {
		return travelDate;
	}

	/** 设置 状态（1：待出票 2：已出票 0：已取消 3：待审核 4：已拒绝） */
	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	/** 得到 状态（1：待出票 2：已出票 0：已取消 3：待审核 4：已拒绝） */
	public Integer getBookStatus() {
		return bookStatus;
	}

	/** 设置 类型（0:普通订单 1：预约单 2：特价票 3：免票） */
	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	/** 得到 类型（0:普通订单 1：预约单 2：特价票 3：免票） */
	public Integer getBookType() {
		return bookType;
	}

	/** 设置 创建时间 */
	public void setBookDate(Long bookDate) {
		this.bookDate = bookDate;
	}

	/** 得到 创建时间 */
	public Long getBookDate() {
		return bookDate;
	}

	/** 设置 单据总金额 */
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	/** 得到 单据总金额 */
	public Integer getTotalAmount() {
		return totalAmount;
	}

	/** 设置 单据总数量 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	/** 得到 单据总数量 */
	public Integer getTotalNum() {
		return totalNum;
	}

	/** 设置 提货码 */
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	/** 得到 提货码 */
	public String getDeliveryCode() {
		return deliveryCode;
	}

	/** 设置 产品信息 */
	public void setBookDetail(String bookDetail) {
		this.bookDetail = bookDetail;
	}

	/** 得到 产品信息 */
	public String getBookDetail() {
		return bookDetail;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public BookEntity() {
	}

	public BookEntity(String bookId, int bookStatus) {
		this.bookId = bookId;
		this.bookStatus = bookStatus;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	* 判断该实体是否需要操作库存，需要返回实体，不需要返回空
	* 
	* @param entity
	* @return
	*/
	public BookEntity getNeedStockEntity() {
		if (Check.NuNString(this.getBookDetail())) {
			return null;
		}

		if (this.getBookStatus() == BookStatusEnum.AUDITING.getStatus()
				|| this.getBookStatus() == BookStatusEnum.BOOKING.getStatus()) {
			return this;
		}
		return null;
	}

}

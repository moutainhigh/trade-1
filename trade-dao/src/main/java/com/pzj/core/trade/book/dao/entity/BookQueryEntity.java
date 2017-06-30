package com.pzj.core.trade.book.dao.entity;

import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

public class BookQueryEntity {

	/**预约单号*/
	private String bookId;

	/**分销商id集合*/
	private List<Long> resellerIds;

	/**操作人id*/
	private Long operatorId;

	/** saas供应商id*/
	private Long supplierId;

	/**查询游玩时间-开始时间*/
	private Long queryTravelSDate;

	/**查询游玩时间-结束时间*/
	private Long queryTravelEDate;

	/**预定状态*/
	private Integer bookStatus;

	/** 提货码*/
	private String deliveryCode;

	private Integer bookType;

	/** 1.预约单     2.特价票    3.免票 */
	private List<Integer> bookTypes;

	/**查询预定时间-开始时间*/
	private Long queryBookSDate;

	/**查询预定时间-结束时间*/
	private Long queryBookEDate;

	/**分页参数*/
	private PageableRequestBean page = new PageableRequestBean();

	/**
	 * 排序规则
	 */
	private String orderByClause;

	public List<Long> getResellerIds() {
		return resellerIds;
	}

	public void setResellerIds(List<Long> resellerIds) {
		this.resellerIds = resellerIds;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getQueryTravelSDate() {
		return queryTravelSDate;
	}

	public void setQueryTravelSDate(Long queryTravelSDate) {
		this.queryTravelSDate = queryTravelSDate;
	}

	public Long getQueryTravelEDate() {
		return queryTravelEDate;
	}

	public void setQueryTravelEDate(Long queryTravelEDate) {
		this.queryTravelEDate = queryTravelEDate;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public Integer getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public List<Integer> getBookTypes() {
		return bookTypes;
	}

	public void setBookTypes(List<Integer> bookTypes) {
		this.bookTypes = bookTypes;
	}

	public Long getQueryBookSDate() {
		return queryBookSDate;
	}

	public void setQueryBookSDate(Long queryBookSDate) {
		this.queryBookSDate = queryBookSDate;
	}

	public Long getQueryBookEDate() {
		return queryBookEDate;
	}

	public void setQueryBookEDate(Long queryBookEDate) {
		this.queryBookEDate = queryBookEDate;
	}

	public PageableRequestBean getPage() {
		return page;
	}

	public void setPage(PageableRequestBean page) {
		this.page = page;
	}

	public Integer getBookType() {
		return bookType;
	}

	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

}

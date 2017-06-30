package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

public class BookQueryInModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 8952754230800096902L;

	/**
	 * @apiDefine BookQueryInModel  BookQueryInModel 预订单分页查询入参
	 * 
	 * @apiParam (BookQueryInModel) {List} resellerIds  分销商id集合  Long
	 * @apiParam (BookQueryInModel) {long} bookId  预约单id  
	 * @apiParam (BookQueryInModel) {long} operatorId 操作人id  
	 * @apiParam (BookQueryInModel) {long} supplierId saas供应商id 
	 * @apiParam (BookQueryInModel) {long} queryStartDate  查询开始时间---游玩时间
	 * @apiParam (BookQueryInModel) {long} queryEndDate  查询结束时间---游玩时间
	 * @apiParam (BookQueryInModel) {int}  bookStatus 单据状态
	 * @apiParam (BookQueryInModel) {PageableRequestBean} page 分页信息
	 * 
	 */
	private List<Long> resellerIds;

	private Long operatorId;

	private String bookId;

	/** saas供应商id*/
	private Long supplierId;

	private Date queryStartDate;

	private Date queryEndDate;

	/**  待出票：1 
	 *   已出票：2 
	 *   已取消：0 */
	private Integer bookStatus;

	private PageableRequestBean page = null;

	public List<Long> getResellerIds() {
		return resellerIds;
	}

	public void setResellerIds(List<Long> resellerIds) {
		this.resellerIds = resellerIds;
	}

	public Date getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(Date queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public Date getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public PageableRequestBean getPage() {
		return page;
	}

	public void setPage(PageableRequestBean page) {
		this.page = page;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}

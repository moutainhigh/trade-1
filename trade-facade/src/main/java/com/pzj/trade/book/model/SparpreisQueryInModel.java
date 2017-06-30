package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

public class SparpreisQueryInModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 2729215705316831521L;

	/**
	 * @apiDefine SparpreisQueryInModel  SparpreisQueryInModel 特价票、免票分页查询入参
	 * 
	 * @apiParam (SparpreisQueryInModel) {List} resellerIds  分销商id集合  Long
	 * @apiParam (SparpreisQueryInModel) {long} operatorId 操作人id 
	 * @apiParam (SparpreisQueryInModel) {long} supplierId saas供应商id 
	 * @apiParam (SparpreisQueryInModel) {String} deliveryCode 提货码
	 * @apiParam (SparpreisQueryInModel) {Date} queryStartDate  查询开始时间---预定时间
	 * @apiParam (SparpreisQueryInModel) {Date} queryEndDate  查询结束时间---预定时间
	 * @apiParam (SparpreisQueryInModel) {int}  bookType 业务类型 
	 * @apiParam (SparpreisQueryInModel) {int}  bookState 单据状态
	 * @apiParam (SparpreisQueryInModel) {PageableRequestBean} page 分页信息
	 * @apiParam (SparpreisQueryInModel) {String} bookId 单号
	 * @apiParam (SparpreisQueryInModel) {List} orderRules 排序规则
	 * 
	 */

	private String bookId;

	private List<Long> resellerIds;

	private Long operatorId;

	/** saas供应商id*/
	private Long supplierId;

	private String deliveryCode;

	private Date queryStartDate;

	private Date queryEndDate;

	/** 免票：2  特价票：3 */
	private Integer bookType;

	/** 待审核：3  
	 *  待出票：1  
	 *  已出票：2 
	 *  待取消：0 
	 *  已拒绝：4*/
	private Integer bookStatus;

	private PageableRequestBean page = null;

	private List<SparpreisQOrderRuleModel> orderRules = null;

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

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
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

	public Integer getBookType() {
		return bookType;
	}

	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	public Integer getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public PageableRequestBean getPage() {
		return page;
	}

	public void setPage(PageableRequestBean page) {
		this.page = page;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<SparpreisQOrderRuleModel> getOrderRule() {
		if (orderRules == null) {
			orderRules = new ArrayList<SparpreisQOrderRuleModel>();
		}
		return orderRules;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

}

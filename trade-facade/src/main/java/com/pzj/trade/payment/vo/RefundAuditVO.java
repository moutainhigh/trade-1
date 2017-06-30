package com.pzj.trade.payment.vo;

import java.io.Serializable;
import java.util.Date;

public class RefundAuditVO implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2915493332613059233L;
	private final static int MAX_PAGESIZE = 10;
	/** 当前页码. */
	private int current_page = 1;
	/** 是否分页 默认分页 */
	private Boolean isPage = true;
	/** 每页显示的最大数量, 默认为10 */
	private int page_size = MAX_PAGESIZE;
    //退款单订单id
	private String orderId;
	//退款单id
    private String refundId;
    //退款状态（0发起，1成功,2拒绝,3不需要审核）
    private Integer refundState;
    //备注
    private String remark;
    //查询条件 提交开始时间
    private Date startCreateDate;
    //查询条件 提交截止时间
    private Date endCreateDate;
    //查询条件 审核开始时间
    private Date startRefundDate;
    //查询条件 审核截止时间
    private Date endRefundDate;
    
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getRefundState() {
		return refundState;
	}

	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Date getStartRefundDate() {
		return startRefundDate;
	}

	public void setStartRefundDate(Date startRefundDate) {
		this.startRefundDate = startRefundDate;
	}

	public Date getEndRefundDate() {
		return endRefundDate;
	}

	public void setEndRefundDate(Date endRefundDate) {
		this.endRefundDate = endRefundDate;
	}

	public int getCurrent_page() {
		return isPage?current_page <= 0 ? 1 : current_page:0;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getPage_index() {
		return (getCurrent_page() - 1) * getPage_size();
	}

	public int getPage_size() {
		return isPage?page_size <= 0 ? MAX_PAGESIZE : page_size:0;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public Boolean getIsPage() {
		return isPage;
	}

	public void setIsPage(Boolean isPage) {
		this.isPage = isPage;
	}
	
}

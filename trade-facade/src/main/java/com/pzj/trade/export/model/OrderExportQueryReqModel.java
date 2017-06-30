/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.model;

import java.io.Serializable;
import java.util.Date;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 订单导出记录查询请求参数
 *
 * @author DongChunfu
 * @date 2017年2月9日
 */
public class OrderExportQueryReqModel extends PageableRequestBean implements Serializable {

	private static final long serialVersionUID = -1509589815268047844L;

	/** 导出开始时间*/
	private Date startTime;
	/** 导出结束时间*/
	private Date endTime;
	/** 导出人*/
	private String createBy;
	/**是否分页*/
	private boolean pageAble = true;

	public OrderExportQueryReqModel() {
		super();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(final String createBy) {
		this.createBy = createBy;
	}

	public boolean getPageAble() {
		return pageAble;
	}

	public void setPageAble(final boolean pageAble) {
		this.pageAble = pageAble;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderExportQueryReqModel [startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", pageAble=");
		builder.append(pageAble);
		builder.append("]");
		return builder.toString();
	}

	//	@Override
	//	public String toString() {
	//		final StringBuilder str = new StringBuilder(OrderExportQueryReqModel.class.getSimpleName());
	//
	//		str.append("currentPage").append(getCurrentPage()).append(",");
	//		str.append("pageSize").append(getPageSize()).append(",");
	//		str.append("pageAble").append(pageAble).append(",");
	//
	//		str.append("startTime=").append(startTime).append(",");
	//		str.append("endTime=").append(endTime).append(",");
	//		str.append("createBy=").append(createBy).append(".");
	//
	//		return str.toString();
	//	}

}

package com.pzj.core.trade.export.entity;

import java.io.Serializable;
import java.util.Date;

public class QueryOrderExportLogParam implements Serializable {

	private static final long serialVersionUID = -1870699682941411602L;

	/**开始时间*/
	private Date startTime;
	/**结束时间*/
	private Date endTime;
	/**创建人*/
	private String createBy;
	/**是否分页*/
	private boolean pageAble = true;
	/**页面大小*/
	private int pageSize;
	/**页下标*/
	private int pageIndex;

	public QueryOrderExportLogParam() {
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

	public boolean isPageAble() {
		return pageAble;
	}

	public void setPageAble(final boolean pageAble) {
		this.pageAble = pageAble;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(final int pageIndex) {
		this.pageIndex = pageIndex;
	}

}

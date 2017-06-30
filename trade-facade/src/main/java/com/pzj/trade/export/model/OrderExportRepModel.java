/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单导出记录响应实体
 * @author DongChunfu
 * @date 2017年2月9日
 */
public class OrderExportRepModel implements Serializable {

	private static final long serialVersionUID = 4364771876856127786L;

	/**导出ID*/
	private int id;

	/** 文件名*/
	private String fileName;

	/** 导出状态 0：导出中，1：导出成功,2:导出失败*/
	private int exportState;

	/** 导出时间*/
	private Date createTime;

	public OrderExportRepModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public int getExportState() {
		return exportState;
	}

	public void setExportState(final int exportState) {
		this.exportState = exportState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

}

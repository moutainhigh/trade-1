/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出订单验证响应参数
 *
 * @author DongChunfu
 * @version $Id: OrderExportVerifyRepModel.java, v 0.1 2017年2月20日 上午10:45:33 DongChunfu Exp $
 */
public class OrderExportVerifyRepModel implements Serializable {

	private static final long serialVersionUID = 4145982885831073262L;

	/**主键*/
	private int id;

	/**文件名*/
	private String fileName;

	/**导出状态 0：导出中，1：导出成功,2:导出失败*/
	private int exportState;

	/**导出时间*/
	private Date createTime;

	/**导出人*/
	private String createBy;

	public OrderExportVerifyRepModel() {
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(final String createBy) {
		this.createBy = createBy;
	}

}

/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.model;

import java.util.Date;

/**
 * 导出订单记录对象
 * @author Administrator
 * @version $Id: ExportModel.java, v 0.1 2017年2月7日 下午2:07:22 Administrator Exp $
 */
public class ExportModel {
	/** 主键*/
	private int export_id;
	/** 文件名*/
	private String file_name;
	/** 条件*/
	private String param;
	/** 导出状态 0：导出中，1：导出成功,2:导出失败*/
	private int export_state;
	/**失败原因 */
	private String err_msg;
	/** 导出时间*/
	private Date create_time;
	/** 导出人*/
	private String create_by;

	/**
	 * Getter method for property <tt>export_id</tt>.
	 *
	 * @return property value of export_id
	 */
	public int getExport_id() {
		return export_id;
	}

	/**
	 * Setter method for property <tt>export_id</tt>.
	 *
	 * @param export_id value to be assigned to property export_id
	 */
	public void setExport_id(final int export_id) {
		this.export_id = export_id;
	}

	/**
	 * Getter method for property <tt>file_name</tt>.
	 *
	 * @return property value of file_name
	 */
	public String getFile_name() {
		return file_name;
	}

	/**
	 * Setter method for property <tt>file_name</tt>.
	 *
	 * @param file_name value to be assigned to property file_name
	 */
	public void setFile_name(final String file_name) {
		this.file_name = file_name;
	}

	/**
	 * Getter method for property <tt>param</tt>.
	 *
	 * @return property value of param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * Setter method for property <tt>param</tt>.
	 *
	 * @param param value to be assigned to property param
	 */
	public void setParam(final String param) {
		this.param = param;
	}

	/**
	 * Getter method for property <tt>export_state</tt>.
	 *
	 * @return property value of export_state
	 */
	public int getExport_state() {
		return export_state;
	}

	/**
	 * Setter method for property <tt>export_state</tt>.
	 *
	 * @param export_state value to be assigned to property export_state
	 */
	public void setExport_state(final int export_state) {
		this.export_state = export_state;
	}

	/**
	 * Getter method for property <tt>err_msg</tt>.
	 *
	 * @return property value of err_msg
	 */
	public String getErr_msg() {
		return err_msg;
	}

	/**
	 * Setter method for property <tt>err_msg</tt>.
	 *
	 * @param err_msg value to be assigned to property err_msg
	 */
	public void setErr_msg(final String err_msg) {
		this.err_msg = err_msg;
	}

	/**
	 * Getter method for property <tt>create_time</tt>.
	 *
	 * @return property value of create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * Setter method for property <tt>create_time</tt>.
	 *
	 * @param create_time value to be assigned to property create_time
	 */
	public void setCreate_time(final Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * Getter method for property <tt>create_by</tt>.
	 *
	 * @return property value of create_by
	 */
	public String getCreate_by() {
		return create_by;
	}

	/**
	 * Setter method for property <tt>create_by</tt>.
	 *
	 * @param create_by value to be assigned to property create_by
	 */
	public void setCreate_by(final String create_by) {
		this.create_by = create_by;
	}

}

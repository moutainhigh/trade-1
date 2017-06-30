/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.export.model;

import java.io.Serializable;

import com.pzj.trade.order.model.ResellerOrdersReqModel;
import com.pzj.trade.order.vo.PlatformOrderListVO;

/**
 * 导出申请对象
 * @author Administrator
 * @version $Id: ExportModel.java, v 0.1 2017年2月7日 下午2:07:22 Administrator Exp $
 */
public class ExportRequestModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	/** 页面名称*/
	private String page_name;

	/** 操作人*/
	private String operator;

	/**0平台,默认为平台 1SaaS直营 2SaaS代售*/
	private int type;

	/** 平台、直营查询条件*/
	private PlatformOrderListVO supplierReqModel;

	/** 代售查询条件*/
	private ResellerOrdersReqModel resellerReqModel;

	/**
	 * Getter method for property <tt>page_name</tt>.
	 * 
	 * @return property value of page_name
	 */
	public String getPage_name() {
		return page_name;
	}

	/**
	 * Setter method for property <tt>page_name</tt>.
	 * 
	 * @param page_name value to be assigned to property page_name
	 */
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}

	/**
	 * Getter method for property <tt>operator</tt>.
	 * 
	 * @return property value of operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Setter method for property <tt>operator</tt>.
	 * 
	 * @param operator value to be assigned to property operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Getter method for property <tt>type</tt>.
	 * 
	 * @return property value of type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type value to be assigned to property type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Getter method for property <tt>supplierReqModel</tt>.
	 * 
	 * @return property value of supplierReqModel
	 */
	public PlatformOrderListVO getSupplierReqModel() {
		return supplierReqModel;
	}

	/**
	 * Setter method for property <tt>supplierReqModel</tt>.
	 * 
	 * @param supplierReqModel value to be assigned to property supplierReqModel
	 */
	public void setSupplierReqModel(PlatformOrderListVO supplierReqModel) {
		this.supplierReqModel = supplierReqModel;
	}

	/**
	 * Getter method for property <tt>resellerReqModel</tt>.
	 * 
	 * @return property value of resellerReqModel
	 */
	public ResellerOrdersReqModel getResellerReqModel() {
		return resellerReqModel;
	}

	/**
	 * Setter method for property <tt>resellerReqModel</tt>.
	 * 
	 * @param resellerReqModel value to be assigned to property resellerReqModel
	 */
	public void setResellerReqModel(ResellerOrdersReqModel resellerReqModel) {
		this.resellerReqModel = resellerReqModel;
	}

}

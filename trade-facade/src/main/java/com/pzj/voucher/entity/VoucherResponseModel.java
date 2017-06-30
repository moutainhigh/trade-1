/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.voucher.entity;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * @version $Id: VoucherResponseModel.java, v 0.1 2017年2月24日 上午11:41:31 Administrator Exp $
 */
public class VoucherResponseModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -4275569009166391462L;

	/** 凭证ID */
	private Long voucherId;

	/** 身份证号或二维码  */
	private String voucherContent;

	/** 凭证介质类型：（ 1手机号    2 二维码或条码及其辅助码  3身份证号 )  */
	private Integer voucherContentType;

	/** 凭证人手机号 */
	private String contactMobile;

	/** 凭证人姓名 */
	private String contactName;

	/** 凭证状态(-1:不可用；0：可以使用；1核销完毕；2：退单)  */
	private Integer voucherState;

	/** 产品线  */
	private Integer voucherCategory;

	/**
	 * Getter method for property <tt>voucherId</tt>.
	 * 
	 * @return property value of voucherId
	 */
	public Long getVoucherId() {
		return voucherId;
	}

	/**
	 * Setter method for property <tt>voucherId</tt>.
	 * 
	 * @param voucherId value to be assigned to property voucherId
	 */
	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	/**
	 * Getter method for property <tt>voucherContent</tt>.
	 * 
	 * @return property value of voucherContent
	 */
	public String getVoucherContent() {
		return voucherContent;
	}

	/**
	 * Setter method for property <tt>voucherContent</tt>.
	 * 
	 * @param voucherContent value to be assigned to property voucherContent
	 */
	public void setVoucherContent(String voucherContent) {
		this.voucherContent = voucherContent;
	}

	/**
	 * Getter method for property <tt>voucherContentType</tt>.
	 * 
	 * @return property value of voucherContentType
	 */
	public Integer getVoucherContentType() {
		return voucherContentType;
	}

	/**
	 * Setter method for property <tt>voucherContentType</tt>.
	 * 
	 * @param voucherContentType value to be assigned to property voucherContentType
	 */
	public void setVoucherContentType(Integer voucherContentType) {
		this.voucherContentType = voucherContentType;
	}

	/**
	 * Getter method for property <tt>contactMobile</tt>.
	 * 
	 * @return property value of contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * Setter method for property <tt>contactMobile</tt>.
	 * 
	 * @param contactMobile value to be assigned to property contactMobile
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * Getter method for property <tt>contactName</tt>.
	 * 
	 * @return property value of contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Setter method for property <tt>contactName</tt>.
	 * 
	 * @param contactName value to be assigned to property contactName
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Getter method for property <tt>voucherState</tt>.
	 * 
	 * @return property value of voucherState
	 */
	public Integer getVoucherState() {
		return voucherState;
	}

	/**
	 * Setter method for property <tt>voucherState</tt>.
	 * 
	 * @param voucherState value to be assigned to property voucherState
	 */
	public void setVoucherState(Integer voucherState) {
		this.voucherState = voucherState;
	}

	/**
	 * Getter method for property <tt>voucherCategory</tt>.
	 * 
	 * @return property value of voucherCategory
	 */
	public Integer getVoucherCategory() {
		return voucherCategory;
	}

	/**
	 * Setter method for property <tt>voucherCategory</tt>.
	 * 
	 * @param voucherCategory value to be assigned to property voucherCategory
	 */
	public void setVoucherCategory(Integer voucherCategory) {
		this.voucherCategory = voucherCategory;
	}

}

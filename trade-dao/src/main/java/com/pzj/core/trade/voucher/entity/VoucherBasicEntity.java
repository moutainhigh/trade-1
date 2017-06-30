/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.voucher.entity;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * @version $Id: VoucherBaseEntity.java, v 0.1 2017年2月24日 上午11:51:28 Administrator Exp $
 */
public class VoucherBasicEntity implements Serializable {
	/**  */
	private static final long serialVersionUID = 1468965912985648464L;

	/** 凭证ID */
	private Long voucher_id;

	/** 身份证号或二维码  */
	private String voucher_content;

	/** 凭证介质类型：（ 1手机号    2 二维码或条码及其辅助码  3身份证号 )  */
	private Integer voucher_content_type;

	//	/** 凭证人手机号 */
	//	private String contact_mobile;
	//
	//	/** 凭证人姓名 */
	//	private String contact_name;

	/** 凭证状态(-1:不可用；0：可以使用；1核销完毕；2：退单)  */
	private Integer voucher_state;

	/** 产品线  */
	private Integer voucher_category;

	/**
	 * Getter method for property <tt>voucher_id</tt>.
	 * 
	 * @return property value of voucher_id
	 */
	public Long getVoucher_id() {
		return voucher_id;
	}

	/**
	 * Setter method for property <tt>voucher_id</tt>.
	 * 
	 * @param voucher_id value to be assigned to property voucher_id
	 */
	public void setVoucher_id(final Long voucher_id) {
		this.voucher_id = voucher_id;
	}

	/**
	 * Getter method for property <tt>voucher_content</tt>.
	 * 
	 * @return property value of voucher_content
	 */
	public String getVoucher_content() {
		return voucher_content;
	}

	/**
	 * Setter method for property <tt>voucher_content</tt>.
	 * 
	 * @param voucher_content value to be assigned to property voucher_content
	 */
	public void setVoucher_content(final String voucher_content) {
		this.voucher_content = voucher_content;
	}

	/**
	 * Getter method for property <tt>voucher_content_type</tt>.
	 * 
	 * @return property value of voucher_content_type
	 */
	public Integer getVoucher_content_type() {
		return voucher_content_type;
	}

	/**
	 * Setter method for property <tt>voucher_content_type</tt>.
	 * 
	 * @param voucher_content_type value to be assigned to property voucher_content_type
	 */
	public void setVoucher_content_type(final Integer voucher_content_type) {
		this.voucher_content_type = voucher_content_type;
	}

	//	/**
	//	 * Getter method for property <tt>contact_mobile</tt>.
	//	 * 
	//	 * @return property value of contact_mobile
	//	 */
	//	public String getContact_mobile() {
	//		return contact_mobile;
	//	}
	//
	//	/**
	//	 * Setter method for property <tt>contact_mobile</tt>.
	//	 * 
	//	 * @param contact_mobile value to be assigned to property contact_mobile
	//	 */
	//	public void setContact_mobile(String contact_mobile) {
	//		this.contact_mobile = contact_mobile;
	//	}

	//	/**
	//	 * Getter method for property <tt>contact_name</tt>.
	//	 * 
	//	 * @return property value of contact_name
	//	 */
	//	public String getContact_name() {
	//		return contact_name;
	//	}
	//
	//	/**
	//	 * Setter method for property <tt>contact_name</tt>.
	//	 * 
	//	 * @param contact_name value to be assigned to property contact_name
	//	 */
	//	public void setContact_name(String contact_name) {
	//		this.contact_name = contact_name;
	//	}

	/**
	 * Getter method for property <tt>voucher_state</tt>.
	 * 
	 * @return property value of voucher_state
	 */
	public Integer getVoucher_state() {
		return voucher_state;
	}

	/**
	 * Setter method for property <tt>voucher_state</tt>.
	 * 
	 * @param voucher_state value to be assigned to property voucher_state
	 */
	public void setVoucher_state(final Integer voucher_state) {
		this.voucher_state = voucher_state;
	}

	/**
	 * Getter method for property <tt>voucher_category</tt>.
	 * 
	 * @return property value of voucher_category
	 */
	public Integer getVoucher_category() {
		return voucher_category;
	}

	/**
	 * Setter method for property <tt>voucher_category</tt>.
	 * 
	 * @param voucher_category value to be assigned to property voucher_category
	 */
	public void setVoucher_category(final Integer voucher_category) {
		this.voucher_category = voucher_category;
	}

	/**
	 * Getter method for property <tt>serialversionuid</tt>.
	 * 
	 * @return property value of serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

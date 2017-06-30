/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.core.trade.order.engine.model.CheckinPointModel;

/**
 * 
 * @author Administrator
 * @version $Id: VoucherInModel.java, v 0.1 2017年3月16日 上午11:45:38 Administrator Exp $
 */
public class VoucherInModel {

	private int voucher_content_type;

	private String voucher_content;

	private Date strat_time;

	private Date expire_time;

	private Date show_start_time;

	private Date show_end_time;

	private int voucher_category;

	private long supplier_id;

	private String transaction_id;

	private long sceneId;

	private List<CheckinPointModel> checkinPointModels;

	private List<Long> productIds = new ArrayList<Long>();

	/**
	 * Getter method for property <tt>sceneId</tt>.
	 * 
	 * @return property value of sceneId
	 */
	public long getSceneId() {
		return sceneId;
	}

	/**
	 * Setter method for property <tt>sceneId</tt>.
	 * 
	 * @param sceneId value to be assigned to property sceneId
	 */
	public void setSceneId(long sceneId) {
		this.sceneId = sceneId;
	}

	/**
	 * Getter method for property <tt>voucher_content_type</tt>.
	 * 
	 * @return property value of voucher_content_type
	 */
	public int getVoucher_content_type() {
		return voucher_content_type;
	}

	/**
	 * Setter method for property <tt>voucher_content_type</tt>.
	 * 
	 * @param voucher_content_type value to be assigned to property voucher_content_type
	 */
	public void setVoucher_content_type(int voucher_content_type) {
		this.voucher_content_type = voucher_content_type;
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
	public void setVoucher_content(String voucher_content) {
		this.voucher_content = voucher_content;
	}

	/**
	 * Getter method for property <tt>strat_time</tt>.
	 * 
	 * @return property value of strat_time
	 */
	public Date getStrat_time() {
		return strat_time;
	}

	/**
	 * Setter method for property <tt>strat_time</tt>.
	 * 
	 * @param strat_time value to be assigned to property strat_time
	 */
	public void setStrat_time(Date strat_time) {
		this.strat_time = strat_time;
	}

	/**
	 * Getter method for property <tt>expire_time</tt>.
	 * 
	 * @return property value of expire_time
	 */
	public Date getExpire_time() {
		return expire_time;
	}

	/**
	 * Setter method for property <tt>expire_time</tt>.
	 * 
	 * @param expire_time value to be assigned to property expire_time
	 */
	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}

	/**
	 * Getter method for property <tt>show_start_time</tt>.
	 * 
	 * @return property value of show_start_time
	 */
	public Date getShow_start_time() {
		return show_start_time;
	}

	/**
	 * Setter method for property <tt>show_start_time</tt>.
	 * 
	 * @param show_start_time value to be assigned to property show_start_time
	 */
	public void setShow_start_time(Date show_start_time) {
		this.show_start_time = show_start_time;
	}

	/**
	 * Getter method for property <tt>show_end_time</tt>.
	 * 
	 * @return property value of show_end_time
	 */
	public Date getShow_end_time() {
		return show_end_time;
	}

	/**
	 * Setter method for property <tt>show_end_time</tt>.
	 * 
	 * @param show_end_time value to be assigned to property show_end_time
	 */
	public void setShow_end_time(Date show_end_time) {
		this.show_end_time = show_end_time;
	}

	/**
	 * Getter method for property <tt>voucher_category</tt>.
	 * 
	 * @return property value of voucher_category
	 */
	public int getVoucher_category() {
		return voucher_category;
	}

	/**
	 * Setter method for property <tt>voucher_category</tt>.
	 * 
	 * @param voucher_category value to be assigned to property voucher_category
	 */
	public void setVoucher_category(int voucher_category) {
		this.voucher_category = voucher_category;
	}

	/**
	 * Getter method for property <tt>supplier_id</tt>.
	 * 
	 * @return property value of supplier_id
	 */
	public long getSupplier_id() {
		return supplier_id;
	}

	/**
	 * Setter method for property <tt>supplier_id</tt>.
	 * 
	 * @param supplier_id value to be assigned to property supplier_id
	 */
	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	/**
	 * Getter method for property <tt>transaction_id</tt>.
	 * 
	 * @return property value of transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * Setter method for property <tt>transaction_id</tt>.
	 * 
	 * @param transaction_id value to be assigned to property transaction_id
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * Getter method for property <tt>checkinPointModels</tt>.
	 * 
	 * @return property value of checkinPointModels
	 */
	public List<CheckinPointModel> getCheckinPointModels() {
		return checkinPointModels;
	}

	/**
	 * Setter method for property <tt>checkinPointModels</tt>.
	 * 
	 * @param checkinPointModels value to be assigned to property checkinPointModels
	 */
	public void setCheckinPointModels(List<CheckinPointModel> checkinPointModels) {
		this.checkinPointModels = checkinPointModels;
	}

	/**
	 * Getter method for property <tt>productIds</tt>.
	 * 
	 * @return property value of productIds
	 */
	public List<Long> getProductIds() {
		return productIds;
	}

	/**
	 * Setter method for property <tt>productIds</tt>.
	 * 
	 * @param productIds value to be assigned to property productIds
	 */
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

}

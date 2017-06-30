/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 身份证判重模型
 * @author Administrator
 * @version $Id: CheckIdCardModel.java, v 0.1 2017年3月29日 下午4:18:53 Administrator Exp $
 */
public class CheckIdCardModel implements Serializable {
	/**
	 * @apiDefine CheckIdCardModel  CheckIdCardModel 身份证判重入参
	 * 
	 * @apiParam (MultiOrderInModel) {long} productId  产品ID  必填
	 * @apiParam (MultiOrderInModel) {long} supplierId  供应商ID  必填
	 * @apiParam (MultiOrderInModel) {List[String]} idCards  身份证号ID列表  必填
	 * @apiParam (MultiOrderInModel) {Date} date  游玩日期 必填
	 * 
	 */

	/**  */
	private static final long serialVersionUID = -6274697389626143570L;
	/**产品ID */
	private long productId;
	/**供应商ID */
	private long supplierId;
	/**需要验证的身份证号ID列表 */
	private List<String> idCards;
	/**游玩日期 */
	private Date date;

	/**
	 * Getter method for property <tt>productId</tt>.
	 * 
	 * @return property value of productId
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * Setter method for property <tt>productId</tt>.
	 * 
	 * @param productId value to be assigned to property productId
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * Getter method for property <tt>supplierId</tt>.
	 * 
	 * @return property value of supplierId
	 */
	public long getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for property <tt>supplierId</tt>.
	 * 
	 * @param supplierId value to be assigned to property supplierId
	 */
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Getter method for property <tt>idCards</tt>.
	 * 
	 * @return property value of idCards
	 */
	public List<String> getIdCards() {
		return idCards;
	}

	/**
	 * Setter method for property <tt>idCards</tt>.
	 * 
	 * @param idCards value to be assigned to property idCards
	 */
	public void setIdCards(List<String> idCards) {
		this.idCards = idCards;
	}

	/**
	 * Getter method for property <tt>date</tt>.
	 * 
	 * @return property value of date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter method for property <tt>date</tt>.
	 * 
	 * @param date value to be assigned to property date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}

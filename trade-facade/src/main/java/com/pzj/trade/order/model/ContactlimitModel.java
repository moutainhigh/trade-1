/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Administrator
 * @version $Id: ContactlimitModel.java, v 0.1 2017年3月29日 下午4:08:33 Administrator Exp $
 */
public class ContactlimitModel implements Serializable {

	/**
	 * @apiDefine ContactlimitModel  ContactlimitModel 联系人限制入参
	 * 
	 * @apiParam (MultiOrderInModel) {long} productId  产品ID  必填
	 * @apiParam (MultiOrderInModel) {Date} startTime  下单起始时间  必填
	 * @apiParam (MultiOrderInModel) {String} contactMobile  联系人手机号 必填
	 * 
	 */

	/**  */
	private static final long serialVersionUID = -3476976743492161488L;
	/**产品组id */
	private long spuId;
	/**下单起始时间 */
	private Date startTime;
	/**联系人手机号 */
	private String contactMobile;

	/**
	 * Getter method for property <tt>spuId</tt>.
	 * 
	 * @return property value of spuId
	 */
	public long getSpuId() {
		return spuId;
	}

	/**
	 * Setter method for property <tt>spuId</tt>.
	 * 
	 * @param spuId value to be assigned to property spuId
	 */
	public void setSpuId(long spuId) {
		this.spuId = spuId;
	}

	/**
	 * Getter method for property <tt>startTime</tt>.
	 * 
	 * @return property value of startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Setter method for property <tt>startTime</tt>.
	 * 
	 * @param startTime value to be assigned to property startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

}

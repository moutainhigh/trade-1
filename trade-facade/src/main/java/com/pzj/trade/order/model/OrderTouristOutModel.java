/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品游客信息数据返回模型
 * @author Administrator
 * @version $Id: OrderTouristOutModel.java, v 0.1 2017年3月27日 下午1:46:35 Administrator Exp $
 */
public class OrderTouristOutModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 8073239458780107773L;

	/**商品id*/
	private String merchId;

	/**姓名*/
	private String name;

	/**身份证*/
	private String idcard;

	private long touristId;

	/**手机号*/
	private String mobile;

	/**拼音*/
	private String nameSpell;

	/**其他*/
	private String other;

	private Date createTime;

	public long getTouristId() {
		return touristId;
	}

	public void setTouristId(long touristId) {
		this.touristId = touristId;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNameSpell() {
		return nameSpell;
	}

	public void setNameSpell(String nameSpell) {
		this.nameSpell = nameSpell;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

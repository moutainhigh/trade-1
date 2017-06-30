package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 联系人模型.
 * @author YRJ
 *
 */
public class ContacteeModel implements Serializable {

	/**
	 * @apiDefine ContacteeModel  ContacteeModel
	 * 
	 * @apiParam (ContacteeModel) {String} contactee  联系人
	 * @apiParam (ContacteeModel) {String} contactMobile 联系人电话
	 * @apiParam (ContacteeModel) {String} idcardNo  身份证  必填
	 * @apiParam (ContacteeModel) {String} touristSpell  联系人姓名拼音
	 * 
	 */

	private static final long serialVersionUID = 1L;

	/** 联系人 */
	private String contactee;

	/**联系人拼音*/
	private String contacteeSpell;

	/** 联系人电话 */
	private String contactMobile;

	/** 联系人身份证号 */
	private String idcardNo;

	/**联系人邮箱*/
	private String email;

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * Getter method for property <tt>idcardNo</tt>.
	 * 
	 * @return property value of idcardNo
	 */
	public String getIdcardNo() {
		return idcardNo == null ? "" : idcardNo.toUpperCase();
	}

	/**
	 * Setter method for property <tt>idcardNo</tt>.
	 * 
	 * @param idcardNo value to be assigned to property idcardNo
	 */
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getContacteeSpell() {
		return contacteeSpell;
	}

	public void setContacteeSpell(String contacteeSpell) {
		this.contacteeSpell = contacteeSpell;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder tostr = new StringBuilder();
		tostr.append("contactee=").append(contactee);
		tostr.append(", mobile=").append(contactMobile);
		tostr.append(", cardNo=").append(idcardNo);
		return tostr.toString();
	}
}

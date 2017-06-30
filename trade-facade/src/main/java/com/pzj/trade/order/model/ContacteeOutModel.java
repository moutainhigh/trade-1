package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 联系人输出模型.
 * @author YRJ
 *
 */
public class ContacteeOutModel implements Serializable {

	/**
	 * @apiDefine ContacteeOutModel  ContacteeOutModel
	 * 
	 * @apiParam (ContacteeOutModel) {String} contactee  联系人
	 * @apiParam (ContacteeOutModel) {String} contactMobile 联系人电话
	 * @apiParam (ContacteeOutModel) {String} idcardNo  身份证  必填
	 * @apiParam (ContacteeOutModel) {String} touristSpell  联系人姓名拼音
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
		return idcardNo;
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
		StringBuilder builder = new StringBuilder();
		builder.append("ContacteeOutModel [contactee=");
		builder.append(contactee);
		builder.append(", contacteeSpell=");
		builder.append(contacteeSpell);
		builder.append(", contactMobile=");
		builder.append(contactMobile);
		builder.append(", idcardNo=");
		builder.append(idcardNo);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

}

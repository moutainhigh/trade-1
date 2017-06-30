package com.pzj.trade.book.model;

import java.io.Serializable;

public class TouristModel implements Serializable {

	/**
	 * @apiDefine TouristModel  TouristModel  预订单/特价票、免票游客信息模型
	 * 
	 * @apiParam (TouristModel) {long} productId  产品ID  必填
	 * @apiParam (TouristModel) {String} tourist  游客姓名  必填
	 * @apiParam (TouristModel) {String} touristMobile 电话  必填
	 * @apiParam (TouristModel) {String} idcardNo  身份证  必填
	 * @apiParam (TouristModel) {String} touristSpell  拼音 必填
	 * @apiParam (TouristModel) {String} [remark]  其他证件号
	 * 
	 */

	/**  */
	private static final long serialVersionUID = 1513852422889609105L;

	/** */
	private long productId;

	/**游客姓名*/
	private String tourist;

	/**游客电话*/
	private String touristMobile;

	/**游客身份证*/
	private String idcardNo;

	/**游客拼音*/
	private String touristSpell;

	/**备注*/
	private String remark;

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

	public String getTourist() {
		return tourist == null ? "" : tourist;
	}

	public void setTourist(String tourist) {
		this.tourist = tourist;
	}

	public String getTouristMobile() {
		return touristMobile == null ? "" : touristMobile;
	}

	public void setTouristMobile(String touristMobile) {
		this.touristMobile = touristMobile;
	}

	public String getTouristSpell() {
		return touristSpell == null ? "" : touristSpell;
	}

	public void setTouristSpell(String touristSpell) {
		this.touristSpell = touristSpell;
	}

	public String getIdcardNo() {
		return idcardNo == null ? "" : idcardNo.toUpperCase();
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getRemark() {
		return remark == null ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

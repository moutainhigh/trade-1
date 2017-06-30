package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pzj.trade.book.common.BookTypeEnum;
import com.pzj.trade.order.model.ContacteeModel;
import com.pzj.trade.order.model.FilledModel;

public class BookInModel implements Serializable {

	/**
	 * @apiDefine BookInModel  BookInModel 预订单创建入参
	 * 
	 * @apiParam (BookInModel) {int} bookType  单据类型  必填
	 * @apiParam (BookInModel) {long} resellerId  预定/免票特价票分销商Id  必填 
	 * @apiParam (BookInModel) {long} operatorId 操作人id  必填
	 * @apiParam (BookInModel) {long} supplierId saas供应商id  必填
	 * @apiParam (BookInModel) {Date} travelDate 游玩时间  必填
	 * @apiParam (BookInModel) {long} srcBookId 预约单id：如是前置订单信息，需传入它对应的预约单id；如是预约单，传入0
	 * @apiParam (BookInModel) {String} travel  旅行社
	 * @apiParam (BookInModel) {String} travelDepartment  旅行社部门
	 * @apiParam (BookInModel) {String} guideId 导游
	 * @apiParam (BookInModel) {List} contactees 联系人信息  ContacteeModel 
	 * @apiParam (BookInModel) {List} filledModelList 填写项  FilledModel 
	 * @apiParam (BookInModel) {long} spuId 产品spuId 必填
	 * @apiParam (BookInModel) {List} products 产品信息  BookProductModel 必填
	 * @apiParam (BookInModel) {List} tourists 游客信息 TouristModel
	 * @apiParam (BookInModel) {String} remark 备注
	 * @apiParam (BookInModel) {String} touristSourceCountry 客源地国家
	 * @apiParam (BookInModel) {String} touristSourceProvince 客源地省
	 * @apiParam (BookInModel) {String} touristSourceCity 客源地城市
	 * @apiParam (BookInModel) {String} deliveryCode 提货码
	 * @apiParam (BookInModel) {List} checkinPoints 检票点ID列表
	 * 
	 */

	/**  */
	private static final long serialVersionUID = -5471226617873530059L;

	/** 单据类型：{@linkplain BookTypeEnum}*/
	private Integer bookType;

	/** 预定/免票特价票分销商Id*/
	private long resellerId;

	/** 操作人id*/
	private long operatorId;

	/** saas供应商id*/
	private long supplierId;

	/** 游玩时间*/
	private Date travelDate;

	/** 预约单id*/
	private String srcBookId;

	/** 提货码*/
	private String deliveryCode;

	/** 旅行社*/
	private String travel;

	/** 旅行社部门*/
	private String travelDepartment;

	/** 导游*/
	private String guide;

	/** 产品spuId*/
	private long spuId;

	/** 客源地国家*/
	private String touristSourceCountry;

	/** 客源地省*/
	private String touristSourceProvince;

	/** 客源地城市*/
	private String touristSourceCity;

	/** 联系人*/
	private ContacteeModel contactee;

	/**填写项*/
	private List<FilledModel> filledModelList;

	/**游客信息*/
	private List<TouristModel> tourists;

	private String remark;

	private List<Long> checkinPoints;

	/**预订单商品 */
	private List<BookProductModel> products;

	public List<BookProductModel> getProducts() {
		return products;
	}

	public void setProducts(List<BookProductModel> products) {
		this.products = products;
	}

	public String getTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
	}

	public String getTravelDepartment() {
		return travelDepartment;
	}

	public void setTravelDepartment(String travelDepartment) {
		this.travelDepartment = travelDepartment;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public String getSrcBookId() {
		return srcBookId;
	}

	public void setSrcBookId(String srcBookId) {
		this.srcBookId = srcBookId;
	}

	public Integer getBookType() {
		return bookType;
	}

	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public long getSpuId() {
		return spuId;
	}

	public void setSpuId(long spuId) {
		this.spuId = spuId;
	}

	public String getTouristSourceCountry() {
		return touristSourceCountry;
	}

	public void setTouristSourceCountry(String touristSourceCountry) {
		this.touristSourceCountry = touristSourceCountry;
	}

	public String getTouristSourceProvince() {
		return touristSourceProvince;
	}

	public void setTouristSourceProvince(String touristSourceProvince) {
		this.touristSourceProvince = touristSourceProvince;
	}

	public String getTouristSourceCity() {
		return touristSourceCity;
	}

	public void setTouristSourceCity(String touristSourceCity) {
		this.touristSourceCity = touristSourceCity;
	}

	public ContacteeModel getContactee() {
		return contactee;
	}

	public void setContactee(ContacteeModel contactee) {
		this.contactee = contactee;
	}

	public List<FilledModel> getFilledModelList() {
		return filledModelList;
	}

	public void setFilledModelList(List<FilledModel> filledModelList) {
		this.filledModelList = filledModelList;
	}

	public List<TouristModel> getTourists() {
		return tourists;
	}

	public void setTourists(List<TouristModel> tourists) {
		this.tourists = tourists;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public List<Long> getCheckinPoints() {
		return checkinPoints;
	}

	public void setCheckinPoints(List<Long> checkinPoints) {
		this.checkinPoints = checkinPoints;
	}

}

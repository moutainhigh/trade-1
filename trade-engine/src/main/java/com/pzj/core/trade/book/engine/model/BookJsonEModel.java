package com.pzj.core.trade.book.engine.model;

import java.util.ArrayList;
import java.util.List;

import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.model.TouristModel;
import com.pzj.trade.order.model.ContacteeModel;
import com.pzj.trade.order.model.FilledModel;

public class BookJsonEModel {

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

	/**预订单商品 */
	private List<ProductJsonEModel> products;

	/**游客信息*/
	private List<TouristModel> tourists;

	/**预约单备注*/
	private String remark;

	/** 旅行社*/
	private String travel;

	/** 旅行社部门*/
	private String travelDepartment;

	/** 导游*/
	private String guide;

	/**审核人*/
	private String auditor;

	/**审核原因*/
	private String auditorReason;

	/**检票点ID列表*/
	private List<Long> checkinPoints;

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

	public List<ProductJsonEModel> getProducts() {
		if (Check.NuNObject(products)) {
			products = new ArrayList<ProductJsonEModel>();
		}
		return products;
	}

	public void addProduct(ProductJsonEModel product) {
		this.getProducts().add(product);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<TouristModel> getTourists() {
		return tourists;
	}

	public void setTourists(List<TouristModel> tourists) {
		this.tourists = tourists;
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

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditorReason() {
		return auditorReason;
	}

	public void setAuditorReason(String auditorReason) {
		this.auditorReason = auditorReason;
	}

	public List<Long> getCheckinPoints() {
		return checkinPoints;
	}

	public void setCheckinPoints(List<Long> checkinPoints) {
		this.checkinPoints = checkinPoints;
	}

}

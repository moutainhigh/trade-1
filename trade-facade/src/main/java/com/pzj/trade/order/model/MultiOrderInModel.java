package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.List;

import com.pzj.trade.book.model.TouristModel;

/**
 * 交易订单参数. 用户下单时传递的参数对象.
 * @author YRJ
 *
 */
public class MultiOrderInModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @apiDefine MultiOrderInModel  MultiOrderInModel 多级下单入参
	 * 
	 * @apiParam (MultiOrderInModel) {long} resellerId  分销商ID  必填
	 * @apiParam (MultiOrderInModel) {long} payerId  付款者ID  必填
	 * @apiParam (MultiOrderInModel) {long} operatorId  操作者ID  必填
	 * @apiParam (MultiOrderInModel) {long} ticketOfficeId  售票点ID
	 * @apiParam (MultiOrderInModel) {long} touristSource  客源地  
	 * @apiParam (MultiOrderInModel) {long} bookId  预约单id  必填
	 * @apiParam (MultiOrderInModel) {int} bookType  预约单类型  可以不填
	 * @apiParam (MultiOrderInModel) {long} travel  旅行社  必填
	 * @apiParam (MultiOrderInModel) {long} travelDepartId  旅行社部门  必填
	 * @apiParam (MultiOrderInModel) {long} guideId  导游ID  必填
	 * @apiParam (MultiOrderInModel) {int} salePort 销售端口（APP, OTA, 微店）  必填
	 * @apiParam (MultiOrderInModel) {long} travelDate  游玩时间  必填
	 * @apiParam (MultiOrderInModel) {int} vourType 出票方式  必填
	 * @apiParam (MultiOrderInModel) {List} contactee  联系人信息  ContacteeModel
	 * @apiParam (MultiOrderInModel) {long} remark  订单备注信息  必填
	 * @apiParam (MultiOrderInModel) {List} products  选购的商品   必填  MultiOrderProductModel
	 * @apiParam (MultiOrderInModel) {List} filleds  填单项列表  必填    FilledModel
	 * 
	 */

	/** 分销商ID 必填*/
	private long resellerId;

	/**
	 * 付款者ID. 必填项. 当前操作者下单时, 可以指定付款者ID, 只有具备结算能力的结算人.
	 */
	private long payerId;

	/** 操作者ID 必填*/
	private long operatorId;

	/** 售票点ID*/
	private long ticketOfficeId;

	/** 客源地国家*/
	private String touristSourceCountry;

	/** 客源地省*/
	private String touristSourceProvince;

	/** 客源地城市*/
	private String touristSourceCity;

	/** 预约单id*/
	private String bookId;

	/** 预约单类型*/
	private int bookType;

	/** 旅行社 */
	private long travel;

	/** 旅行社部门 */
	private long travelDepartId;

	/** 旅行社部门名称 */
	private String travelDepartName;

	/** 导游ID */
	private long guideId;

	/** 导游名称 */
	private String guideIdName;

	/** 销售端口（APP, OTA, 微店）必填 */
	private int salePort;

	/** 游玩时间*/
	private long travelDate;

	/** 出票方式*/
	private int vourType;

	/** 联系人信息.*/
	private ContacteeModel contactee;

	/** 订单备注信息. */
	private String remark;

	/** 选购的商品  必填*/
	private List<MultiOrderProductModel> products;

	/** 游客信息*/
	private List<TouristModel> tourists;

	/** 填单项列表*/
	private List<FilledModel> filleds;
	/** 检票点IDs*/
	private List<Long> checkinPoints;

	public List<Long> getCheckinPoints() {
		return checkinPoints;
	}

	public void setCheckinPoints(List<Long> checkinPoints) {
		this.checkinPoints = checkinPoints;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public long getPayerId() {
		return payerId;
	}

	public void setPayerId(long payerId) {
		this.payerId = payerId;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * Getter method for property <tt>bookType</tt>.
	 * 
	 * @return property value of bookType
	 */
	public int getBookType() {
		return bookType;
	}

	/**
	 * Setter method for property <tt>bookType</tt>.
	 * 
	 * @param bookType value to be assigned to property bookType
	 */
	public void setBookType(int bookType) {
		this.bookType = bookType;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(long travel) {
		this.travel = travel;
	}

	public long getTravelDepartId() {
		return travelDepartId;
	}

	public void setTravelDepartId(long travelDepartId) {
		this.travelDepartId = travelDepartId;
	}

	public long getGuideId() {
		return guideId;
	}

	public void setGuideId(long guideId) {
		this.guideId = guideId;
	}

	public int getSalePort() {
		return salePort;
	}

	public void setSalePort(int salePort) {
		this.salePort = salePort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<MultiOrderProductModel> getProducts() {
		return products;
	}

	public void setProducts(List<MultiOrderProductModel> products) {
		this.products = products;
	}

	public List<FilledModel> getFilleds() {
		return filleds;
	}

	public void setFilleds(List<FilledModel> filleds) {
		this.filleds = filleds;
	}

	/**
	 * Getter method for property <tt>contactee</tt>.
	 * 
	 * @return property value of contactee
	 */
	public ContacteeModel getContactee() {
		return contactee;
	}

	/**
	 * Setter method for property <tt>contactee</tt>.
	 * 
	 * @param contactee value to be assigned to property contactee
	 */
	public void setContactee(ContacteeModel contactee) {
		this.contactee = contactee;
	}

	public long getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(long travelDate) {
		this.travelDate = travelDate;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	/**
	 * Getter method for property <tt>ticketOfficeId</tt>.
	 * 
	 * @return property value of ticketOfficeId
	 */
	public long getTicketOfficeId() {
		return ticketOfficeId;
	}

	/**
	 * Setter method for property <tt>ticketOfficeId</tt>.
	 * 
	 * @param ticketOfficeId value to be assigned to property ticketOfficeId
	 */
	public void setTicketOfficeId(long ticketOfficeId) {
		this.ticketOfficeId = ticketOfficeId;
	}

	/**
	 * Getter method for property <tt>touristSourceCountry</tt>.
	 * 
	 * @return property value of touristSourceCountry
	 */
	public String getTouristSourceCountry() {
		return touristSourceCountry;
	}

	/**
	 * Setter method for property <tt>touristSourceCountry</tt>.
	 * 
	 * @param touristSourceCountry value to be assigned to property touristSourceCountry
	 */
	public void setTouristSourceCountry(String touristSourceCountry) {
		this.touristSourceCountry = touristSourceCountry;
	}

	/**
	 * Getter method for property <tt>touristSourceProvince</tt>.
	 * 
	 * @return property value of touristSourceProvince
	 */
	public String getTouristSourceProvince() {
		return touristSourceProvince;
	}

	/**
	 * Setter method for property <tt>touristSourceProvince</tt>.
	 * 
	 * @param touristSourceProvince value to be assigned to property touristSourceProvince
	 */
	public void setTouristSourceProvince(String touristSourceProvince) {
		this.touristSourceProvince = touristSourceProvince;
	}

	/**
	 * Getter method for property <tt>touristSourceCity</tt>.
	 * 
	 * @return property value of touristSourceCity
	 */
	public String getTouristSourceCity() {
		return touristSourceCity;
	}

	/**
	 * Setter method for property <tt>touristSourceCity</tt>.
	 * 
	 * @param touristSourceCity value to be assigned to property touristSourceCity
	 */
	public void setTouristSourceCity(String touristSourceCity) {
		this.touristSourceCity = touristSourceCity;
	}

	/**
	 * Getter method for property <tt>vourType</tt>.
	 * 
	 * @return property value of vourType
	 */
	public int getVourType() {
		return vourType;
	}

	/**
	 * Setter method for property <tt>vourType</tt>.
	 * 
	 * @param vourType value to be assigned to property vourType
	 */
	public void setVourType(int vourType) {
		this.vourType = vourType;
	}

	/**
	 * Getter method for property <tt>travelDepartName</tt>.
	 * 
	 * @return property value of travelDepartName
	 */
	public String getTravelDepartName() {
		return travelDepartName;
	}

	/**
	 * Setter method for property <tt>travelDepartName</tt>.
	 * 
	 * @param travelDepartName value to be assigned to property travelDepartName
	 */
	public void setTravelDepartName(String travelDepartName) {
		this.travelDepartName = travelDepartName;
	}

	/**
	 * Getter method for property <tt>guideIdName</tt>.
	 * 
	 * @return property value of guideIdName
	 */
	public String getGuideIdName() {
		return guideIdName;
	}

	/**
	 * Setter method for property <tt>guideIdName</tt>.
	 * 
	 * @param guideIdName value to be assigned to property guideIdName
	 */
	public void setGuideIdName(String guideIdName) {
		this.guideIdName = guideIdName;
	}

	/**
	 * Getter method for property <tt>tourists</tt>.
	 * 
	 * @return property value of tourists
	 */
	public List<TouristModel> getTourists() {
		return tourists;
	}

	/**
	 * Setter method for property <tt>tourists</tt>.
	 * 
	 * @param tourists value to be assigned to property tourists
	 */
	public void setTourists(List<TouristModel> tourists) {
		this.tourists = tourists;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MultiOrderInModel [resellerId=");
		builder.append(resellerId);
		builder.append(", payerId=");
		builder.append(payerId);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append(", ticketOfficeId=");
		builder.append(ticketOfficeId);
		builder.append(", touristSourceCountry=");
		builder.append(touristSourceCountry);
		builder.append(", touristSourceProvince=");
		builder.append(touristSourceProvince);
		builder.append(", touristSourceCity=");
		builder.append(touristSourceCity);
		builder.append(", bookId=");
		builder.append(bookId);
		builder.append(", travel=");
		builder.append(travel);
		builder.append(", travelDepartId=");
		builder.append(travelDepartId);
		builder.append(", guideId=");
		builder.append(guideId);
		builder.append(", salePort=");
		builder.append(salePort);
		builder.append(", travelDate=");
		builder.append(travelDate);
		builder.append(", vourType=");
		builder.append(vourType);
		builder.append(", contactee=");
		builder.append(contactee);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", products=");
		builder.append(products);
		builder.append(", filleds=");
		builder.append(filleds);
		builder.append("]");
		return builder.toString();
	}

}

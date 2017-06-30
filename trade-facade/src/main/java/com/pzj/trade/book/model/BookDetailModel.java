package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.book.common.BookTypeEnum;
import com.pzj.trade.order.model.ContacteeModel;
import com.pzj.trade.order.model.FilledModel;

public class BookDetailModel implements Serializable {

	/**
	 * @apiDefine BookDetailModel  BookDetailModel 预订单详情模型
	 * 
	 * @apiParam (BookDetailModel) {String} bookId  预定单Id 
	 * @apiParam (BookDetailModel) {String} srcBookId  上级预约单id 
	 * @apiParam (BookDetailModel) {int} bookType  单据类型 
	 * @apiParam (BookDetailModel) {long} resellerId  预定/免票特价票分销商Id 
	 * @apiParam (BookDetailModel) {long} operatorId 操作人id 
	 * @apiParam (BookDetailModel) {long} supplierId saas供应商id 
	 * @apiParam (BookDetailModel) {long} bookStatus  预定单状态  
	 * @apiParam (BookDetailModel) {Date} bookDate  预定时间 
	 * @apiParam (BookDetailModel) {int}  totalBugNum 单据总数量
	 * @apiParam (BookDetailModel) {int}  totalAmount 单据价格 
	 * @apiParam (BookDetailModel) {String} travel  旅行社
	 * @apiParam (BookDetailModel) {String} travelDepartment  旅行社部门
	 * @apiParam (BookDetailModel) {String} guide 导游
	 * @apiParam (BookDetailModel) {Date} travelDate 游玩时间  
	 * @apiParam (BookDetailModel) {String} deliveryCode 提货码
	 * @apiParam (BookDetailModel) {ContacteeModel} contactees 联系人信息   
	 * @apiParam (BookDetailModel) {List} filledModelList 填写项  FilledModel 
	 * @apiParam (BookDetailModel) {long} spuId 产品spuId 必填
	 * @apiParam (BookDetailModel) {List} products 产品信息  BookProductModel 
	 * @apiParam (BookDetailModel) {String} auditor 审核操作人
	 * @apiParam (BookDetailModel) {String} auditReason 审核原因
	 * @apiParam (BookDetailModel) {String} remark 备注
	 * @apiParam (BookDetailModel) {List} checkinPoints 检票点ID列表
	 * 
	 */

	/**  */
	private static final long serialVersionUID = -7184182625022081333L;

	/***/
	private String bookId;

	private String srcBookId;

	/** 预定/免票特价票分销商Id*/
	private long resellerId;

	/** 操作人id*/
	private long operatorId;

	/** saas供应商id*/
	private long supplierId;

	/** 特价票，免票订单类型 {@linkplain BookTypeEnum}*/
	private int bookType;

	/** 游玩时间*/
	private Date travelDate;

	/***/
	private int bookStatus;

	private Date bookDate;

	private int totalAmount;

	private int totalNum;

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

	/** 提货码*/
	private String deliveryCode;

	/** 审核操作人*/
	private String auditor;

	/** 原因*/
	private String auditReason;

	/**预订单商品 */
	private List<BookProductModel> products;

	/**检票点*/
	private List<Long> checkinPoints;

	public List<BookProductModel> getProducts() {
		if (Check.NuNObject(products)) {
			products = new ArrayList<BookProductModel>();
		}
		return products;
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

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(int bookStatus) {
		this.bookStatus = bookStatus;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getSrcBookId() {
		return srcBookId;
	}

	public void setSrcBookId(String srcBookId) {
		this.srcBookId = srcBookId;
	}

	public int getBookType() {
		return bookType;
	}

	public void setBookType(int bookType) {
		this.bookType = bookType;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Override
	public String toString() {
		return JSONConverter.toJson(this);
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
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

	public List<Long> getCheckinPoints() {
		return checkinPoints;
	}

	public void setCheckinPoints(List<Long> checkinPoints) {
		this.checkinPoints = checkinPoints;
	}

}

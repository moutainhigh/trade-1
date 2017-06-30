package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.List;

/**
 * 交易订单参数. 用户下单时传递的参数对象.
 * @author YRJ
 *
 */
public class TradeOrderInModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 分销商ID 必填*/
	private long resellerId;

	/**
	 * 付款者ID. 必填项. 当前操作者下单时, 可以指定付款者ID, 只有具备结算能力的结算人.
	 */
	private long payerId;

	/** 操作者ID 必填*/
	private long operatorId;

	/** 旅行社 */
	private long travel;

	/** 旅行社部门 */
	private long travelDepartId;

	/** 导游ID */
	private long guideId;

	/** 销售端口（APP, OTA, 微店）必填 */
	private int salePort;

	/** 联系人信息.*/
	private ContacteeModel contactee;

	/** 订单备注信息. */
	private String remark;

	/** 选购的商品  必填*/
	private List<PurchaseProductModel> products;

	/** 填单项列表*/
	private List<FilledModel> filleds;

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

	public List<PurchaseProductModel> getProducts() {
		return products;
	}

	public void setProducts(List<PurchaseProductModel> products) {
		this.products = products;
	}

	public List<FilledModel> getFilleds() {
		return filleds;
	}

	public void setFilleds(List<FilledModel> filleds) {
		this.filleds = filleds;
	}

	public ContacteeModel getContactee() {
		return contactee;
	}

	public void setContactee(ContacteeModel contactee) {
		this.contactee = contactee;
	}
}

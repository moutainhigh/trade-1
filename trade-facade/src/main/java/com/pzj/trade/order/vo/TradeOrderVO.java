package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.List;

import com.pzj.trade.order.model.FilledModel;

/**
 * 交易订单参数. 用户下单时传递的参数对象.
 * @author YRJ
 *
 */
public class TradeOrderVO implements Serializable {
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

	/** 分销端代理商 */
	@Deprecated
	private long resellerAgentId;

	/** 供应端代理商 */
	@Deprecated
	private long supplierAgentId;

	/** 销售端口 */
	private int salePort;

	/** 联系人 */
	private String contactee;

	/** 联系人电话 */
	private String contactMobile;

	/** 联系人身份证号 */
	private String idcard_no;

	/** 订单备注信息. */
	private String remark;

	/** 选购的商品  必填*/
	private List<PurchaseProductVO> products;

	/** 填单项列表*/
	private List<FilledModel> filledModelList;

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

	@Deprecated
	public long getResellerAgentId() {
		return resellerAgentId;
	}

	@Deprecated
	public void setResellerAgentId(long resellerAgentId) {
		this.resellerAgentId = resellerAgentId;
	}

	@Deprecated
	public long getSupplierAgentId() {
		return supplierAgentId;
	}

	@Deprecated
	public void setSupplierAgentId(long supplierAgentId) {
		this.supplierAgentId = supplierAgentId;
	}

	public int getSalePort() {
		return salePort;
	}

	public void setSalePort(int salePort) {
		this.salePort = salePort;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<PurchaseProductVO> getProducts() {
		return products;
	}

	public void setProducts(List<PurchaseProductVO> products) {
		this.products = products;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public List<FilledModel> getFilledModelList() {
		return filledModelList;
	}

	public void setFilledModelList(List<FilledModel> filledModelList) {
		this.filledModelList = filledModelList;
	}

}

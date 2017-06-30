package com.pzj.trade.order.model;

import java.util.Date;
import java.util.List;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 订单列表查询参数.
 * @author YRJ
 *
 */
public class ResellerOrdersReqModel extends PageableRequestBean {
	private static final long serialVersionUID = 1L;

	/** 订单Id */
	private String orderId;
	/** 订单状态 */
	private int orderStatus;
	/** 订单状态 列表*/
	private List<Integer> orderStatusList;

	/** 下单开始日期 */
	private Date startDate;

	/** 下单结束日期 */
	private Date endDate;

	/** 联系人 */
	private String contactee;

	/** 联系电话 */
	private String contactMobile;

	/** 产品名称 */
	private String merchName;

	/** 操作人ID */
	private long operatorId;

	/** 团散 */
	private String productVarie;
	/** 订单来源：1、线下窗口 3、PC分销端  5、商户APP 7、商户微店*/
	private int salePort;

	/** 订单来源列表：1、线下窗口 3、PC分销端  5、商户APP 7、商户微店*/
	private List<Integer> salePortList;
	/** '订单商品状态.0:可消费; 1: 已消费; 2: 待退款; 3: 已退款; -1: 不可用;4:待确认；5：已完成*/
	private Integer merchState;

	/** 分销商Id*/
	private long resellerId;

	/** 初始供应商ID */
	private long pSupplierId;

	/** 供应商ID */
	private long supplierId;

	/** 出游/入住开始时间 */
	private Date visitStartTime;
	/** 出游/入住结束时间 */
	private Date visitEndTime;

	/**支付方式. 1：第三方/余额 2：签单 3：现金*/
	private Integer payWay;

	/** 商品类型 */
	private int merchType;

	/** 产品ID */
	private long productId;

	/** 产品ID集合 */
	private List<Long> productIds;

	/** 分销商Id列表，用于接收多个分销商ID*/
	private List<Long> resellerIds;

	/** 供应商Id列表，用于接收多个供应商ID*/
	private List<Long> supplierIds;

	/** 初始供应商Id列表，用于接收多个供应商ID*/
	private List<Long> pSupplierIds;

	/**
	 * 查询类型:
	 * 
	 *resellerQueryPayReslut 需要付款订单（分销）
	 *resellerQueryDifferencePayReslut 需要补差订单（分销）
	 * */
	private String queryType;
	private String transactionId;

	/** 列表结算是否按照创建时间降序排列  */
	private boolean sortDesc = true;

	public boolean getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	public long getpSupplierId() {
		return pSupplierId;
	}

	public void setpSupplierId(long pSupplierId) {
		this.pSupplierId = pSupplierId;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Integer> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<Integer> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public String getProductVarie() {
		return productVarie;
	}

	public void setProductVarie(String productVarie) {
		this.productVarie = productVarie;
	}

	public int getSalePort() {
		return salePort;
	}

	public void setSalePort(int salePort) {
		this.salePort = salePort;
	}

	public List<Integer> getSalePortList() {
		return salePortList;
	}

	public void setSalePortList(List<Integer> salePortList) {
		this.salePortList = salePortList;
	}

	public Integer getMerchState() {
		return merchState;
	}

	public void setMerchState(Integer merchState) {
		this.merchState = merchState;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	public Date getVisitStartTime() {
		return visitStartTime;
	}

	public void setVisitStartTime(Date visitStartTime) {
		this.visitStartTime = visitStartTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public int getMerchType() {
		return merchType;
	}

	public void setMerchType(int merchType) {
		this.merchType = merchType;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public List<Long> getResellerIds() {
		return resellerIds;
	}

	public void setResellerIds(List<Long> resellerIds) {
		this.resellerIds = resellerIds;
	}

	public List<Long> getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(List<Long> supplierIds) {
		this.supplierIds = supplierIds;
	}

	public List<Long> getpSupplierIds() {
		return pSupplierIds;
	}

	public void setpSupplierIds(List<Long> pSupplierIds) {
		this.pSupplierIds = pSupplierIds;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

}

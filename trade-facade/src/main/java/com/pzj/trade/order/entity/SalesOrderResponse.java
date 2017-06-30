package com.pzj.trade.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 销售/采购单. 专为清结算系统提供.
 * @author YRJ
 *
 */
public class SalesOrderResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orderId;
	private String porderId;
	private String transactionId;
	private String orderStatus;
	private long payerId;
	private long operatorId;
	private long travel;
	private long travelDepartId;
	private long guideId;
	private long resellerId;
	private String reseller;
	private long originResellerId;
	private String originReseller;
	private long resellerAgentId;
	private String resellerAgenter;
	private long supplierId;
	private String supplier;
	private long originSupplierId;
	private String originSupplier;
	private long supplierAgentId;
	private String supplierAgenter;
	private int totalNum;
	private int checkedNum;
	private int refundNum;
	private double totalAmount;
	private double refundAmount;
	private boolean confirm;
	@Deprecated
	private int channelType;
	private int currency = 1;
	private int salePort;
	private String salePortContent;
	private int orderType;
	private int deduction = 0;
	private String thirdCode;
	private Date createTime;
	private Date confirmTime;
	private List<MerchResponse> merchs;
	private String contactee;
	private String contactMobile;
	private int thirdPayType;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int is_direct;
	/**订单版本 0为老 订单数据,大于0为新订单*/
	private int version;

	public String getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getIs_direct() {
		return is_direct;
	}

	public void setIs_direct(int is_direct) {
		this.is_direct = is_direct;
	}

	/**
	 * 获取第三方支付类型.
	 * @return
	 */
	public int getThirdPayType() {
		return thirdPayType;
	}

	public void setThirdPayType(int thirdPayType) {
		this.thirdPayType = thirdPayType;
	}

	/**
	 * 设置第三方支付类型
	 * @param orderId
	 */

	/**
	 * 获取联系人.
	 * @return
	 */
	public String getContactee() {
		return contactee;
	}

	/**
	 * 设置联系人
	 * @param orderId
	 */
	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	/**
	 * 获取 联系电话 .
	 * @return
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * 设置联系电话 .
	 * @return
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * 获取订单ID.
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取促销活动抵扣
	 * @return
	 */
	public int getDeduction() {
		return deduction;
	}

	/**
	 * 设置促销活动抵扣
	 * @param deduction
	 */
	public void setDeduction(int deduction) {
		this.deduction = deduction;
	}

	/**
	 * 获取订单类型. 1: 魔方向供应商采购订单; 2: 分销商向魔方购买订单; 3: 直签的单产品
	 * @return
	 */
	public int getOrderType() {
		return orderType;
	}

	/**
	 * 设置订单类型.
	 * @param orderType
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	/**
	 * 获取交易ID. 此次级联订单所用的整个交易ID，多个交易相同
	 * @return
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * 设置交易ID.
	 * @param transactionId
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * 获取原供应商ID. 当购买多个产品属于不同供应商时, 此值为0.
	 * @return
	 */
	public long getOriginSupplierId() {
		return originSupplierId;
	}

	/**
	 * 设置原供应商ID
	 * @param originSupplierId
	 */
	public void setOriginSupplierId(long originSupplierId) {
		this.originSupplierId = originSupplierId;
	}

	/**
	 * 获取原供应商名称, 此值为空.
	 * @return
	 */
	public String getOriginSupplier() {
		return originSupplier;
	}

	/**
	 * 设置原供应商名称
	 * @param originSupplier
	 */
	public void setOriginSupplier(String originSupplier) {
		this.originSupplier = originSupplier;
	}

	/**
	 * 获取供应商ID.
	 * @return
	 */
	public long getSupplierId() {
		return supplierId;
	}

	/**
	 * 设置供应商ID
	 * @param supplierId
	 */
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * 获取供应商名称, 此值为空.
	 * @return
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * 设置供应商名称.
	 * @param supplier
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * 获取原分销商ID
	 * @return
	 */
	public long getOriginResellerId() {
		return originResellerId;
	}

	/**
	 * 设置原分销商ID
	 * @param originResellerId
	 */
	public void setOriginResellerId(long originResellerId) {
		this.originResellerId = originResellerId;
	}

	/**
	 * 获取原分销商名称, 此值为空.
	 * @return
	 */
	public String getOriginReseller() {
		return originReseller;
	}

	/**
	 * 设置原分销商名称
	 * @param originReseller
	 */
	public void setOriginReseller(String originReseller) {
		this.originReseller = originReseller;
	}

	/**
	 * 获取分销商ID
	 * @return
	 */
	public long getResellerId() {
		return resellerId;
	}

	/**
	 * 设置分销商ID
	 * @param resellerId
	 */
	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
	}

	/**
	 * 获取分销商名称, 此值为空.
	 * @return
	 */
	public String getReseller() {
		return reseller;
	}

	/**
	 * 设置分销商名称.
	 * @param reseller
	 */
	public void setReseller(String reseller) {
		this.reseller = reseller;
	}

	/**
	 * 设置币种.
	 * @return
	 */
	public int getCurrency() {
		return currency;
	}

	/**
	 * 币种. 默认为1: 人民币
	 * @param currency
	 */
	public void setCurrency(int currency) {
		this.currency = currency;
	}

	/**
	 * 获取订单销售端口.
	 * @return
	 */
	public int getSalePort() {
		return salePort;
	}

	/**
	 * 设置销售端口.
	 * @param salePort
	 */
	public void setSalePort(int salePort) {
		this.salePort = salePort;
	}

	public String getSalePortContent() {
		return salePortContent;
	}

	public void setSalePortContent(String salePortContent) {
		this.salePortContent = salePortContent;
	}

	/**
	 * 获取订单总金额
	 * @return
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 设置订单总金额
	 * @param totalAmount
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 获取订单创建时间
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 订单创建时间
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取订单退款总金额
	 * @return
	 */
	public double getRefundAmount() {
		return refundAmount;
	}

	/**
	 * 设置订单退款总金额
	 * @param refundAmount
	 */
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * 父订单ID
	 * @return
	 */
	public String getPorderId() {
		return porderId;
	}

	/**
	 * 父订单ID
	 * @param porderId
	 */
	public void setPorderId(String porderId) {
		this.porderId = porderId;
	}

	/**
	 * 支付者ID
	 * @return
	 */
	public long getPayerId() {
		return payerId;
	}

	/**
	 * 支付者ID
	 * @param payerId
	 */
	public void setPayerId(long payerId) {
		this.payerId = payerId;
	}

	/**
	 * 操作者ID
	 * @return
	 */
	public long getOperatorId() {
		return operatorId;
	}

	/**
	 * 操作者ID
	 * @param operatorId
	 */
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 供应端代理商ID
	 * @return
	 */
	public long getSupplierAgentId() {
		return supplierAgentId;
	}

	/**
	 * 供应端代理商ID
	 * @param supplierAgentId
	 */
	public void setSupplierAgentId(long supplierAgentId) {
		this.supplierAgentId = supplierAgentId;
	}

	/**
	 * 分销端代理商
	 * @return
	 */
	public String getResellerAgenter() {
		return resellerAgenter;
	}

	/**
	 * 分销端代理商
	 * @param resellerAgenter
	 */
	public void setResellerAgenter(String resellerAgenter) {
		this.resellerAgenter = resellerAgenter;
	}

	/**
	 * 供应端代理商名称
	 * @return
	 */
	public String getSupplierAgenter() {
		return supplierAgenter;
	}

	/**
	 * 供应端代理商名称
	 * @param supplierAgenter
	 */
	public void setSupplierAgenter(String supplierAgenter) {
		this.supplierAgenter = supplierAgenter;
	}

	/**
	 * 旅行社
	 * @return
	 */
	public long getTravel() {
		return travel;
	}

	/**
	 * 旅行社
	 * @param travel
	 */
	public void setTravel(long travel) {
		this.travel = travel;
	}

	/**
	 * 旅行社部门
	 * @return
	 */
	public long getTravelDepartId() {
		return travelDepartId;
	}

	/**
	 * 旅行社部门
	 * @param travelDepartId
	 */
	public void setTravelDepartId(long travelDepartId) {
		this.travelDepartId = travelDepartId;
	}

	/**
	 * 导游ID
	 * @return
	 */
	public long getGuideId() {
		return guideId;
	}

	/**
	 * 导游ID
	 * @param guideId
	 */
	public void setGuideId(long guideId) {
		this.guideId = guideId;
	}

	/**
	 * 分销端代理商ID
	 * @return
	 */
	public long getResellerAgentId() {
		return resellerAgentId;
	}

	/**
	 * 分销端代理商ID
	 * @param resellerAgentId
	 */
	public void setResellerAgentId(long resellerAgentId) {
		this.resellerAgentId = resellerAgentId;
	}

	/**
	 * 订单状态.1: 待付款; 10: 已支付; 20: 已取消; 30: 已退款; 40: 已完成; 50: 已确认（二次确认情况）
	 * @return
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 订单状态.1: 待付款; 10: 已支付; 20: 已取消; 30: 已退款; 40: 已完成; 50: 已确认（二次确认情况）
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 是否需要确认. 1: 不需要; 2: 需要
	 * @return
	 */
	public boolean isConfirm() {
		return confirm;
	}

	/**
	 * 是否需要确认. 1: 不需要; 2: 需要
	 * @param confirm
	 */
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	/**
	 * 订单包含的商品总数量
	 * @return
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * 订单包含的商品总数量
	 * @param totalNum
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * 已确认的商品数量
	 * @return
	 */
	public int getCheckedNum() {
		return checkedNum;
	}

	/**
	 * 已确认的商品数量
	 * @param checkedNum
	 */
	public void setCheckedNum(int checkedNum) {
		this.checkedNum = checkedNum;
	}

	/**
	 * 已退款的商品数量
	 * @return
	 */
	public int getRefundNum() {
		return refundNum;
	}

	/**
	 * 已退款的商品数量
	 * @param refundNum
	 */
	public void setRefundNum(int refundNum) {
		this.refundNum = refundNum;
	}

	/**
	 * 渠道类型. 1: 直签; 2: 直销; 3: 魔方分销
	 * @return
	 */
	public int getChannelType() {
		return channelType;
	}

	/**
	 * 渠道类型. 1: 直签; 2: 直销; 3: 魔方分销
	 * @param channelType
	 */
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	/**
	 * 订单关闭时间（取消、已退款、已完成）
	 * @return
	 */
	public Date getConfirmTime() {
		return confirmTime;
	}

	/**
	 * 订单关闭时间（取消、已退款、已完成）
	 * @param confirmTime
	 */
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	/**
	 * 订单商品列表
	 * @return
	 */
	public List<MerchResponse> getMerchs() {
		return merchs;
	}

	/**
	 * 订单商品列表
	 * @param merchs
	 */
	public void setMerchs(List<MerchResponse> merchs) {
		this.merchs = merchs;
	}
	//"perOfServiceFee": 0, //交易服务费百分比
	//"dealMoney": 0, //应收交易服务费
	//"commission": 0, //应收佣金
	//"amountReceived": 236, //已收金额
	//"amountPayable": 236, //订单应收
	// "otherDeduction": 0, //使用其它促销活动抵扣
}

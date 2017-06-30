package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.trade.order.entity.RemarkResponse;

/**
 * 分销商响应结果
 * @author GLG 
 * 
 */
public class ResellerOrderDetailRespModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -2306196522971689910L;
	/** 订单id*/
	private String orderId;

	/**订单支付者的资金帐号*/
	private long payer_id;
	/** 是否需要确认. 1: 不需要; 2: 需要;3:已确认' */
	private int confirm;

	/**已确认的商品数量*/
	private int checked_num;

	/**已退款的商品数量*/
	private int refund_num;
	/**销售端口（APP, OTA, 微店）名称*/
	private String sale_port_content = "";
	/** 联系人 */
	private String contactee = "";

	/** 联系电话 */
	private String contact_mobile = "";

	/** 联系人身份证 */
	private String idcard_no = "";

	private String transaction_id = "";
	/**父订单ID*/
	private String p_order_id = "";
	/**第三方支付类型*/
	private int third_pay_type;

	/**结算价,非政策结算价，是购买价-后返之和*/
	private double settlement_price;
	/**支付方式. 1：第三方/余额 2：签单 3：现金*/
	private Integer payWay;

	/** 下单时间 */
	private Date create_time;

	/** 支付时间*/
	private Date pay_time;

	/** 分销商id*/
	private long reseller_id;

	/** 分销商手机*/
	private String reseller_mobile;

	/**渠道id	*/
	private long channel_id;

	/**旅行社*/
	private long travel;

	/**旅行社手机*/
	private String travel_mobile;

	/**旅行社部门*/
	private long travel_depart_id;

	/**旅行社部门手机*/
	private String travel_depart_mobile;

	/**导游*/
	private long guide_id;

	/**导游手机*/
	private String guide_mobile;

	/**已消费金额*/
	private double check_amount;

	/**已退金额*/
	private double refund_amount;

	/**后返*/
	private double rebate_amount;

	/**操作者ID*/
	private long operator_id;

	/**供应商ID*/
	private long supplier_id;

	/**订单状态*/
	private int order_status;

	/**订单总金额*/
	private double total_amount;

	/**总数量*/
	private int total_num;

	/**订单类型*/
	private int order_type;

	/** 订单来源：1、线下窗口 3、PC分销端  5、商户APP 7、商户微店 */
	private int sale_port;

	/**初始供应商*/
	private long pSupplierId;

	/**初始供应商手机*/
	private String pSupplierMobile;

	/**我的供应商手机*/
	private String supplierMobile;

	/**分销支付时间*/
	private Date resellerPayTime;

	/**采购支付方式 1：第三方/余额 2：签单 3：现金*/
	private int supplierPayType;

	/**分销支付方式1：第三方/余额 2：签单 3：现金*/
	private int resellerPayType;

	/**付款锁. 0: 未支付; 1: 已锁定,2:支付成功*/
	private int payState;

	/**采购支付时间*/
	private Date supplierPayTime;

	/**采购订单金额*/
	private double supplierTotalAmount;

	/**分销订单金额*/
	private double resellerTotalAmount;

	/**后返-采购*/
	private double supplierRebateAmount;

	/**后返-分销*/
	private double resellerRebateAmount;

	/**我的分销商id*/
	private long childResellerId;

	public long getChildResellerId() {
		return childResellerId;
	}

	public void setChildResellerId(long childResellerId) {
		this.childResellerId = childResellerId;
	}

	private List<ResellerMerchDetailRespModel> resellerMerchDetailRespModels = new ArrayList<ResellerMerchDetailRespModel>();
	/** 备注 */
	private List<RemarkResponse> remarks = new ArrayList<RemarkResponse>();

	/** 填单项列表*/
	private List<FilledModel> filledModelList;
	/** 配送信息*/
	private DeliveryDetailModel delivery_info;
	/** 客源地信息*/
	private String touristSource;

	/**是否直签，1-直签，2-非直签（即魔方)*/
	private int isDirect;

	public int getPayState() {
		return payState;
	}

	public void setPayState(int payState) {
		this.payState = payState;
	}

	public int getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(int isDirect) {
		this.isDirect = isDirect;
	}

	/**初始订单支付时间*/
	private Date originPayTime;

	public Date getOriginPayTime() {
		return originPayTime;
	}

	public void setOriginPayTime(Date originPayTime) {
		this.originPayTime = originPayTime;
	}

	public String getTouristSource() {
		return touristSource;
	}

	public void setTouristSource(String touristSource) {
		this.touristSource = touristSource;
	}

	public int getSupplierPayType() {
		return supplierPayType;
	}

	public void setSupplierPayType(int supplierPayType) {
		this.supplierPayType = supplierPayType;
	}

	public DeliveryDetailModel getDelivery_info() {
		return delivery_info;
	}

	public void setDelivery_info(DeliveryDetailModel delivery_info) {
		this.delivery_info = delivery_info;
	}

	public List<ResellerMerchDetailRespModel> getResellerMerchDetailRespModels() {
		return resellerMerchDetailRespModels;
	}

	public void setResellerMerchDetailRespModels(List<ResellerMerchDetailRespModel> resellerMerchDetailRespModels) {
		this.resellerMerchDetailRespModels = resellerMerchDetailRespModels;
	}

	public List<RemarkResponse> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<RemarkResponse> remarks) {
		this.remarks = remarks;
	}

	public long getPayer_id() {
		return payer_id;
	}

	public void setPayer_id(long payer_id) {
		this.payer_id = payer_id;
	}

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	public int getChecked_num() {
		return checked_num;
	}

	public void setChecked_num(int checked_num) {
		this.checked_num = checked_num;
	}

	public int getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}

	public String getSale_port_content() {
		return sale_port_content;
	}

	public void setSale_port_content(String sale_port_content) {
		this.sale_port_content = sale_port_content;
	}

	public String getContactee() {
		return contactee;
	}

	public void setContactee(String contactee) {
		this.contactee = contactee;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getP_order_id() {
		return p_order_id;
	}

	public void setP_order_id(String p_order_id) {
		this.p_order_id = p_order_id;
	}

	public int getThird_pay_type() {
		return third_pay_type;
	}

	public void setThird_pay_type(int third_pay_type) {
		this.third_pay_type = third_pay_type;
	}

	public double getSettlement_price() {
		return settlement_price;
	}

	public void setSettlement_price(double settlement_price) {
		this.settlement_price = settlement_price;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public long getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(long reseller_id) {
		this.reseller_id = reseller_id;
	}

	public String getReseller_mobile() {
		return reseller_mobile;
	}

	public void setReseller_mobile(String reseller_mobile) {
		this.reseller_mobile = reseller_mobile;
	}

	public long getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(long travel) {
		this.travel = travel;
	}

	public String getTravel_mobile() {
		return travel_mobile;
	}

	public void setTravel_mobile(String travel_mobile) {
		this.travel_mobile = travel_mobile;
	}

	public long getTravel_depart_id() {
		return travel_depart_id;
	}

	public void setTravel_depart_id(long travel_depart_id) {
		this.travel_depart_id = travel_depart_id;
	}

	public String getTravel_depart_mobile() {
		return travel_depart_mobile;
	}

	public void setTravel_depart_mobile(String travel_depart_mobile) {
		this.travel_depart_mobile = travel_depart_mobile;
	}

	public long getGuide_id() {
		return guide_id;
	}

	public void setGuide_id(long guide_id) {
		this.guide_id = guide_id;
	}

	public String getGuide_mobile() {
		return guide_mobile;
	}

	public void setGuide_mobile(String guide_mobile) {
		this.guide_mobile = guide_mobile;
	}

	public double getCheck_amount() {
		return check_amount;
	}

	public void setCheck_amount(double check_amount) {
		this.check_amount = check_amount;
	}

	public double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public double getRebate_amount() {
		return rebate_amount;
	}

	public void setRebate_amount(double rebate_amount) {
		this.rebate_amount = rebate_amount;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}

	public int getSale_port() {
		return sale_port;
	}

	public void setSale_port(int sale_port) {
		this.sale_port = sale_port;
	}

	public long getpSupplierId() {
		return pSupplierId;
	}

	public void setpSupplierId(long pSupplierId) {
		this.pSupplierId = pSupplierId;
	}

	public String getpSupplierMobile() {
		return pSupplierMobile;
	}

	public void setpSupplierMobile(String pSupplierMobile) {
		this.pSupplierMobile = pSupplierMobile;
	}

	public String getSupplierMobile() {
		return supplierMobile;
	}

	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
	}

	public Date getResellerPayTime() {
		return resellerPayTime;
	}

	public void setResellerPayTime(Date resellerPayTime) {
		this.resellerPayTime = resellerPayTime;
	}

	public int getResellerPayType() {
		return resellerPayType;
	}

	public void setResellerPayType(int resellerPayType) {
		this.resellerPayType = resellerPayType;
	}

	public Date getSupplierPayTime() {
		return supplierPayTime;
	}

	public void setSupplierPayTime(Date supplierPayTime) {
		this.supplierPayTime = supplierPayTime;
	}

	public double getSupplierTotalAmount() {
		return supplierTotalAmount;
	}

	public void setSupplierTotalAmount(double supplierTotalAmount) {
		this.supplierTotalAmount = supplierTotalAmount;
	}

	public double getResellerTotalAmount() {
		return resellerTotalAmount;
	}

	public void setResellerTotalAmount(double resellerTotalAmount) {
		this.resellerTotalAmount = resellerTotalAmount;
	}

	public double getSupplierRebateAmount() {
		return supplierRebateAmount;
	}

	public void setSupplierRebateAmount(double supplierRebateAmount) {
		this.supplierRebateAmount = supplierRebateAmount;
	}

	public double getResellerRebateAmount() {
		return resellerRebateAmount;
	}

	public void setResellerRebateAmount(double resellerRebateAmount) {
		this.resellerRebateAmount = resellerRebateAmount;
	}

	public List<FilledModel> getFilledModelList() {
		return filledModelList;
	}

	public void setFilledModelList(List<FilledModel> filledModelList) {
		this.filledModelList = filledModelList;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
